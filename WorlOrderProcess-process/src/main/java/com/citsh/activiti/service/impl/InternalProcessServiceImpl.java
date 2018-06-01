package com.citsh.activiti.service.impl;

import com.citsh.activiti.service.InternalProcessService;
import com.citsh.activiti.service.QueryRuTaskService;
import com.citsh.cmd.CompleteTaskWithCommentCmd;
import com.citsh.cmd.DeleteTaskWithCommentCmd;
import com.citsh.cmd.FindTaskDefinitionsCmd;
import com.citsh.cmd.RollbackTaskCmd;
import com.citsh.cmd.SignalStartEventCmd;
import com.citsh.cmd.WithdrawTaskCmd;
import com.citsh.config.entity.BpmTaskConf;
import com.citsh.config.service.BpmTaskConfService;
import com.citsh.form.entity.BpmConfForm;
import com.citsh.form.service.BpmConfFormService;
import com.citsh.from.FormDTO;
import com.citsh.operation.entity.BpmConfOperation;
import com.citsh.operation.service.BpmConfOperationService;
import com.citsh.process.ProcessTaskDefinition;
import com.citsh.user.entity.BpmConfUser;
import com.citsh.user.service.BpmConfUserService;
import com.citsh.util.DOM4JUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cmd.GetDeploymentProcessDefinitionCmd;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntityManager;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class InternalProcessServiceImpl
  implements InternalProcessService
{
  private static Logger logger = LoggerFactory.getLogger(InternalProcessServiceImpl.class);

  @Autowired
  private ProcessEngine processEngine;

  @Autowired
  private BpmConfOperationService bpmConfOperationService;

  @Autowired
  private BpmConfFormService bpmConfFormService;

  @Autowired
  private QueryRuTaskService queryRuTaskService;

  @Autowired
  private BpmConfUserService bpmConfUserService;

  @Autowired
  private BpmTaskConfService bpmTaskConfService;

  public FormDTO findTaskForm(String taskId) { if (taskId == null) {
      logger.error("taskId cannot be null");
      return null;
    }
    Task task = (Task)((TaskQuery)this.processEngine.getTaskService().createTaskQuery().taskId(taskId)).singleResult();
    if (task == null) {
      logger.error("cannot find task for {}", taskId);
      return null;
    }
    String processDefinitionId = task.getProcessDefinitionId();
    String activityId = task.getTaskDefinitionKey();
    FormDTO formDto = new FormDTO();
    formDto.setTaskId(taskId);

    List<BpmConfOperation> bpmConfOperations = this.bpmConfOperationService.listBySQL("bpmConfNode.bpmConfBase.processDefinitionId=? and bpmConfNode.code=?", new Object[] { processDefinitionId, activityId });

    for (BpmConfOperation bpmConfOperation : bpmConfOperations) {
      formDto.getButtons().add(bpmConfOperation.getValue());
    }
    formDto.setProcessDefinitionId(processDefinitionId);
    formDto.setActivityId(activityId);

    List<BpmConfForm> bpmConfForms = this.bpmConfFormService.listBySQL(" bpmConfNode.bpmConfBase.processDefinitionId=? and bpmConfNode.code=?", new Object[] { processDefinitionId, activityId });

    if (!bpmConfForms.isEmpty()) {
      BpmConfForm bpmConfForm = (BpmConfForm)bpmConfForms.get(0);
      if (!Integer.valueOf(2).equals(bpmConfForm.getStatus()))
      {
        if (Integer.valueOf(1).equals(bpmConfForm.getType())) {
          formDto.setRedirect(true);
          formDto.setUrl(bpmConfForm.getValue());
        } else {
          formDto.setCode(bpmConfForm.getValue());
        }
      }
    }
    return formDto;
  }

  public List<ProcessTaskDefinition> findTaskDefinitions(String processDefinitionId)
  {
    List<ProcessTaskDefinition> processTaskDefinitions = new ArrayList<ProcessTaskDefinition>();
    FindTaskDefinitionsCmd cmd = new FindTaskDefinitionsCmd(processDefinitionId);
    List<TaskDefinition> taskDefinitions =this.processEngine.getManagementService().executeCommand(cmd);
    for (TaskDefinition taskDefinition : taskDefinitions) {
      ProcessTaskDefinition processTaskDefinition = new ProcessTaskDefinition();
      processTaskDefinition.setKey(taskDefinition.getKey());

      if (taskDefinition.getNameExpression() != null) {
        processTaskDefinition.setName(taskDefinition.getNameExpression().getExpressionText());
      }

      if (taskDefinition.getAssigneeExpression() != null) {
        processTaskDefinition.setAssignee(taskDefinition.getAssigneeExpression().getExpressionText());
      }

      processTaskDefinitions.add(processTaskDefinition);
    }

    return processTaskDefinitions;
  }

  public void completeTask(String taskId, String userId, Map<String, Object> variables)
  {
    TaskService taskService = this.processEngine.getTaskService();
    Task task = (Task)((TaskQuery)taskService.createTaskQuery().taskId(taskId)).singleResult();

    if ((variables == null) || (variables.size() <= 0)) {
      variables = DOM4JUtil.completemission(taskId);
    }
    if (task == null) {
      throw new IllegalStateException("任务不存在");
    }
    IdentityService identityService = this.processEngine.getIdentityService();
    identityService.setAuthenticatedUserId(userId);

    if ("subtask".equals(task.getCategory())) {
      this.processEngine.getManagementService().executeCommand(new DeleteTaskWithCommentCmd(taskId, "完成"));
      int count = this.queryRuTaskService.count("select count(*) from ACT_RU_TASK where PARENT_TASK_ID_=?", new Object[] { task
        .getParentTaskId() }).intValue();

      if (count > 1) {
        return;
      }

      taskId = task.getParentTaskId();
    }
    this.processEngine.getManagementService().executeCommand(new CompleteTaskWithCommentCmd(taskId, variables, "完成"));
  }

  public void transfer(String taskId, String assignee, String owner)
  {
    this.processEngine.getTaskService().setAssignee(taskId, assignee);
    this.processEngine.getTaskService().setOwner(taskId, owner);
  }

  public void withdrawTask(String taskId)
  {
    Command cmd = new WithdrawTaskCmd(taskId);
    this.processEngine.getManagementService().executeCommand(cmd);
  }

  public void rollback(String taskId, String activityId, String userId)
  {
    Command cmd = new RollbackTaskCmd(taskId, activityId, userId);
    this.processEngine.getManagementService().executeCommand(cmd);
  }

  public void rollbackAuto(String taskId, String activityId)
  {
    Command cmd = new RollbackTaskCmd(taskId, activityId);
    this.processEngine.getManagementService().executeCommand(cmd);
  }

  public void delegateTask(String taskId, String userId)
  {
    this.processEngine.getTaskService().delegateTask(taskId, userId);
  }

  public void resolveTask(String taskId)
  {
    this.processEngine.getTaskService().resolveTask(taskId);
  }

  public ProcessTaskDefinition findTaskDefinition(String processDefinitionId, String taskDefintionKey, String businessKey)
  {
    List<BpmConfUser> bpmConfUsers = this.bpmConfUserService.listBySQL(" bpmConfNode.bpmConfBase.processDefinitionId=? and bpmConfNode.code=?", new Object[] { processDefinitionId, taskDefintionKey });

    logger.debug("{}", bpmConfUsers);
    ProcessTaskDefinition processTaskDefinition = new ProcessTaskDefinition();
    try {
      for (BpmConfUser bpmConfUser : bpmConfUsers) {
        logger.debug("status : {}, type: {}", bpmConfUser.getStatus(), bpmConfUser.getType());
        logger.debug("value : {}", bpmConfUser.getValue());

        String value = bpmConfUser.getValue();

        if (bpmConfUser.getStatus().intValue() == 1) {
          if (bpmConfUser.getType().intValue() == 0) {
            logger.debug("add assignee : {}", value);
            processTaskDefinition.setAssignee(value);
          } else if (bpmConfUser.getType().intValue() == 1) {
            logger.debug("add candidate user : {}", value);
            processTaskDefinition.addParticipantDefinition("user", value, "add");
          } else if (bpmConfUser.getType().intValue() == 2) {
            logger.debug("add candidate group : {}", value);
            processTaskDefinition.addParticipantDefinition("group", value, "add");
          }
        } else if (bpmConfUser.getStatus().intValue() == 2)
          if (bpmConfUser.getType().intValue() == 0) {
            logger.debug("delete assignee : {}", value);
            processTaskDefinition.setAssignee(null);
          } else if (bpmConfUser.getType().intValue() == 1) {
            logger.debug("delete candidate user : {}", value);
            processTaskDefinition.addParticipantDefinition("user", value, "delete");
          } else if (bpmConfUser.getType().intValue() == 2) {
            logger.debug("delete candidate group : {}", value);
            processTaskDefinition.addParticipantDefinition("group", value, "delete");
          }
      }
    }
    catch (Exception ex) {
      logger.info(ex.getMessage(), ex);
    }
    try
    {
      BpmTaskConf bpmTaskConf = this.bpmTaskConfService.listBySQLOne("businessKey=? and taskDefinitionKey=?", new Object[] { businessKey, taskDefintionKey });

      if (bpmTaskConf != null) {
        String assignee = bpmTaskConf.getAssignee();

        if ((assignee != null) && (!"".equals(assignee))) {
          logger.debug("add assignee : {}", assignee);
          processTaskDefinition.setAssignee(assignee);
        }
      } else {
        logger.info("cannot find BpmTaskConf {} {}", businessKey, taskDefintionKey);
      }
    } catch (Exception ex) {
      logger.info(ex.getMessage(), ex);
    }
    return processTaskDefinition;
  }

  public String findInitiator(String processInstanceId)
  {
    String initiator = null;
    if (Context.getCommandContext() == null)
    {
      initiator = ((HistoricProcessInstance)this.processEngine.getHistoryService().createHistoricProcessInstanceQuery()
        .processInstanceId(processInstanceId)
        .singleResult()).getStartUserId();
    }
    else {
      initiator = Context.getCommandContext().getHistoricProcessInstanceEntityManager()
        .findHistoricProcessInstance(processInstanceId)
        .getStartUserId();
    }
    return initiator;
  }

  public String findAssigneeByActivityId(String processInstanceId, String activityId)
  {
    return null;
  }

  public Object executeExpression(String taskId, String expressionText)
  {
    TaskEntity taskEntity = Context.getCommandContext().getTaskEntityManager().findTaskById(taskId);
    ExpressionManager expressionManager = Context.getProcessEngineConfiguration().getExpressionManager();
    return expressionManager.createExpression(expressionText).getValue(taskEntity);
  }

  public String findInitialActivityId(String processDefinitionId)
  {
    GetDeploymentProcessDefinitionCmd cmd = new GetDeploymentProcessDefinitionCmd(processDefinitionId);
    ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)this.processEngine.getManagementService().executeCommand(cmd);
    return processDefinitionEntity.getInitial().getId();
  }

  public String findFirstUserTaskActivityId(String processDefinitionId, String initiator)
  {
    GetDeploymentProcessDefinitionCmd cmd = new GetDeploymentProcessDefinitionCmd(processDefinitionId);
    ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)this.processEngine.getManagementService().executeCommand(cmd);
    ActivityImpl activityImpl = processDefinitionEntity.getInitial();
    if (activityImpl.getOutgoingTransitions().size() != 1)
    {
      throw new IllegalStateException("start activity outgoing transitions cannot more than 1, now is : " + activityImpl
        .getOutgoingTransitions().size());
    }
    PvmTransition pvmTransition = (PvmTransition)activityImpl.getOutgoingTransitions().get(0);
    PvmActivity pvmActivity = pvmTransition.getDestination();
    if (!"userTask".equals(pvmActivity.getProperty("type"))) {
      logger.info("first activity is not userTask, just skip");
      return null;
    }
    return pvmActivity.getId();
  }

  public void signalExecution(String executionId)
  {
    this.processEngine.getManagementService().executeCommand(new SignalStartEventCmd(executionId));
  }
}