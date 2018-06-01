package com.citsh.process.service.impl;

import com.citsh.humantask.HumanTaskConnector;
import com.citsh.humantask.HumanTaskDTO;
import com.citsh.keyvalue.FormParameter;
import com.citsh.keyvalue.config.RecordBuilder;
import com.citsh.keyvalue.entity.Record;
import com.citsh.keyvalue.service.KeyValueService;
import com.citsh.process.ProcessConnector;
import com.citsh.process.ProcessDTO;
import com.citsh.process.service.ProcessOperactionService;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ProcessOperactionServiceImpl
  implements ProcessOperactionService
{
  private static Logger logger = LoggerFactory.getLogger(ProcessOperactionServiceImpl.class);
  public static final String OPERATION_BUSINESS_KEY = "businessKey";
  public static final String OPERATION_TASK_ID = "taskId";
  public static final String OPERATION_BPM_PROCESS_ID = "bpmProcessId";
  public static final int STATUS_DRAFT_PROCESS = 0;
  public static final int STATUS_DRAFT_TASK = 1;
  public static final int STATUS_RUNNING = 2;

  @Autowired
  private KeyValueService keyValueService;

  @Autowired
  private HumanTaskConnector humanTaskConnector;

  @Autowired
  private ProcessConnector processConnector;

  public String saveDraft(String userId, String tenantId, FormParameter formParameter)
  {
    String humanTaskId = formParameter.getHumanTaskId();
    String businessKey = formParameter.getBusinessKey();
    String bpmProcessId = formParameter.getBpmProcessId();
    if (StringUtils.isNotBlank(humanTaskId))
    {
      HumanTaskDTO humanTaskDTO = this.humanTaskConnector.findHumanTask(humanTaskId);
      if (humanTaskDTO == null) {
        throw new IllegalStateException("任务不存在");
      }
      String processInstanceId = humanTaskDTO.getProcessInstanceId();
      Record record = this.keyValueService.findByRef(processInstanceId);
      if (record != null) {
        record = new RecordBuilder().build(record, 1, formParameter);
        businessKey = record.getCode();
        if (record.getBusinessKey() == null) {
          record.setBusinessKey(businessKey);
        }
        this.keyValueService.save(record);
      }
    } else if (StringUtils.isNotBlank(businessKey))
    {
      Record record = this.keyValueService.findByCode(businessKey);
      record = new RecordBuilder().build(record, 0, formParameter);
      this.keyValueService.save(record);
    } else if (StringUtils.isNotBlank(bpmProcessId))
    {
      Record record = new RecordBuilder().build(bpmProcessId, 0, formParameter, userId, tenantId);

      ProcessDTO processDTO = this.processConnector.findProcess(bpmProcessId);
      record.setName(processDTO.getProcessDefinitionName());

      businessKey = record.getCode();
      if (record.getBusinessKey() == null) {
        record.setBusinessKey(businessKey);
      }
      this.keyValueService.save(record);
    } else {
      logger.error("humanTaskId, businessKey, bpmProcessId all null : {}", formParameter.getMultiValueMap());
      throw new IllegalArgumentException("humanTaskId, businessKey, bpmProcessId all null");
    }
    return businessKey;
  }

  public void startProcessInstance(String userId, String businessKey, String processDefinitionId, Map<String, Object> processParameters, Record record)
  {
    String processInstanceId = this.processConnector.startProcess(userId, businessKey, processDefinitionId, processParameters);

    record = new RecordBuilder().build(record, 2, processInstanceId);
    this.keyValueService.save(record);
  }

  public void completeTask(String humanTaskId, String userId, FormParameter formParameter, Map<String, Object> taskParameters, Record record, String processInstanceId)
  {
    this.humanTaskConnector.completeTask(humanTaskId, userId, formParameter.getAction(), formParameter.getComment(), taskParameters);

    if (record == null) {
      record = new Record();
    }
    record = new RecordBuilder().build(record, 2, processInstanceId);
    this.keyValueService.save(record);
  }
}