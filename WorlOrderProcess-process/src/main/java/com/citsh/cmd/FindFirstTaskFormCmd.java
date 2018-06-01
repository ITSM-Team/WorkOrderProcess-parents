package com.citsh.cmd;

import com.citsh.from.FirstTaskForm;
import java.util.List;
import java.util.Map;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.form.DefaultFormHandler;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.deploy.DeploymentManager;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FindFirstTaskFormCmd
  implements Command<FirstTaskForm>
{
  private Logger logger = LoggerFactory.getLogger(FindFirstTaskFormCmd.class);
  private String processDefinitionId;

  public FindFirstTaskFormCmd(String processDefinitionId)
  {
    this.processDefinitionId = processDefinitionId;
  }

  public FirstTaskForm execute(CommandContext commandContext)
  {
    ProcessDefinitionEntity processDefinitionEntity = Context.getProcessEngineConfiguration().getDeploymentManager()
      .findDeployedProcessDefinitionById(this.processDefinitionId);

    if (processDefinitionEntity == null) {
      throw new IllegalArgumentException("cannot find processDefinition : " + this.processDefinitionId);
    }

    if (processDefinitionEntity.getHasStartFormKey()) {
      return findStartEventForm(processDefinitionEntity);
    }

    ActivityImpl activityImpl = processDefinitionEntity.getInitial();
    if (activityImpl.getOutgoingTransitions().size() != 1)
    {
      throw new IllegalStateException("start activity outgoing transitions cannot more than 1, now is : " + activityImpl
        .getOutgoingTransitions().size());
    }
    PvmTransition pvmTransition = (PvmTransition)activityImpl.getOutgoingTransitions().get(0);
    PvmActivity pvmActivity = pvmTransition.getDestination();
    if (!"userTask".equals(pvmActivity.getProperty("type"))) {
      this.logger.info("first activity is not userTask, just skip");
      return new FirstTaskForm();
    }

    FirstTaskForm firstTaskForm = new FirstTaskForm();
    firstTaskForm.setProcessDefinitionId(this.processDefinitionId);
    firstTaskForm.setExists(true);
    firstTaskForm.setTaskForm(true);
    String taskDefinitionKey = pvmActivity.getId();
    this.logger.debug("activityId : {}", pvmActivity.getId());
    firstTaskForm.setActivityId(taskDefinitionKey);

    TaskDefinition taskDefinition = (TaskDefinition)processDefinitionEntity.getTaskDefinitions().get(taskDefinitionKey);

    Expression expression = taskDefinition.getAssigneeExpression();
    if (expression != null) {
      String expressionText = expression.getExpressionText();
      this.logger.debug("{}", expressionText);
      this.logger.debug("{}", activityImpl.getProperties());
      this.logger.debug("{}", processDefinitionEntity.getProperties());
      firstTaskForm.setAssignee(expressionText);
    } else {
      this.logger.info("cannot find expression : {}, {}", this.processDefinitionId, taskDefinitionKey);
    }

    String initiatorVariableName = (String)processDefinitionEntity
      .getProperty("initiatorVariableName");

    firstTaskForm.setInitiatorName(initiatorVariableName);

    DefaultFormHandler formHandler = (DefaultFormHandler)taskDefinition.getTaskFormHandler();
    if (formHandler.getFormKey() != null) {
      String formKey = formHandler.getFormKey().getExpressionText();
      firstTaskForm.setFormKey(formKey);
    } else {
      this.logger.info("cannot find formKey from xml : {}, {}", this.processDefinitionId, taskDefinitionKey);
    }
    return firstTaskForm;
  }

  public FirstTaskForm findStartEventForm(ProcessDefinitionEntity processDefinitionEntity)
  {
    FirstTaskForm firstTaskForm = new FirstTaskForm();
    firstTaskForm.setExists(true);
    firstTaskForm.setProcessDefinitionId(this.processDefinitionId);
    firstTaskForm.setTaskForm(false);

    DefaultFormHandler defaultFormHandler = (DefaultFormHandler)processDefinitionEntity.getStartFormHandler();
    if (defaultFormHandler.getFormKey() != null)
    {
      String formkey = defaultFormHandler.getFormKey().getExpressionText();
      firstTaskForm.setFormKey(formkey);

      firstTaskForm.setActivityId(processDefinitionEntity.getInitial().getId());
    }
    return firstTaskForm;
  }
}