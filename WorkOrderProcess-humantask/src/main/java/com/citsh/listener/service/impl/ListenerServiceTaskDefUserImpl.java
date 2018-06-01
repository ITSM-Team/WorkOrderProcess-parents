package com.citsh.listener.service.impl;

import com.citsh.activiti.service.InternalProcessService;
import com.citsh.base.service.TaskDefinitionService;
import com.citsh.enumitem.HumanTaskEnum;
import com.citsh.humantask.TaskUserDTO;
import com.citsh.humantask.entity.TaskInfo;
import com.citsh.humantask.entity.TaskParticipant;
import com.citsh.humantask.service.TaskParticipantService;
import com.citsh.id.IdGenerator;
import com.citsh.listener.service.ListenerService;
import com.citsh.rule.service.AssigneeRule;
import com.citsh.rule.service.impl.AssigneeRuleInitiatorImpl;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ListenerServiceTaskDefUserImpl
  implements ListenerService
{
  private Logger logger = LoggerFactory.getLogger(ListenerServiceTaskDefUserImpl.class);

  @Autowired
  private TaskDefinitionService taskDefinitionService;

  @Autowired
  private TaskParticipantService taskParticipantService;

  @Autowired
  private IdGenerator idGenerator;

  @Autowired
  private InternalProcessService internalProcessService;

  public void onCreate(TaskInfo taskInfo) { if ("copy".equals(taskInfo.getCatalog())) {
      return;
    }
    String processInstanceId = taskInfo.getProcessInstanceId();
    String startUserId = this.internalProcessService.findInitiator(processInstanceId);
    AssigneeRule assigneeRule = new AssigneeRuleInitiatorImpl();
    String userId = assigneeRule.process(startUserId);
    this.logger.debug("userId : {}", userId);
    String taskDefinitionKey = taskInfo.getCode();
    String processDefinitionId = taskInfo.getProcessDefinitionId();
    List<TaskUserDTO> taskUsers = this.taskDefinitionService.findTaskUsers(taskDefinitionKey, processDefinitionId);
    for (TaskUserDTO taskUser : taskUsers) {
      String catalog = taskUser.getCatalog();
      String type = taskUser.getType();
      String value = taskUser.getValue();
      if (HumanTaskEnum.ASSIGNEE.getCode().equals(catalog)) {
        taskInfo.setAssignee(value);
      } else if (HumanTaskEnum.CANDIDATE.getCode().equals(catalog)) {
        TaskParticipant taskParticipant = new TaskParticipant();
        taskParticipant.setCategory(catalog);
        taskParticipant.setRef(value);
        taskParticipant.setType(type);
        taskParticipant.setTaskInfo(taskInfo);
        taskParticipant.setId(this.idGenerator.generateId());
        this.taskParticipantService.save(taskParticipant);
      }
    }
  }

  public void onComplete(TaskInfo taskInfo)
  {
  }
}