package com.citsh.cmd;

import java.util.Map;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEventBuilder;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntityManager;
import org.activiti.engine.task.DelegationState;

public class CompleteTaskWithCommentCmd
  implements Command<Object>
{
  private String taskId;
  private String comment;
  private Map<String, Object> variables;

  public CompleteTaskWithCommentCmd(String taskId, Map<String, Object> variables, String comment)
  {
    this.taskId = taskId;
    this.variables = variables;
    this.comment = comment;
  }

  public Object execute(CommandContext commandContext) {
    TaskEntity taskEntity = commandContext.getTaskEntityManager().findTaskById(this.taskId);

    if (this.variables != null) {
      taskEntity.setExecutionVariables(this.variables);
    }

    boolean localScope = false;

    if ((taskEntity.getDelegationState() != null) && 
      (taskEntity
      .getDelegationState().equals(DelegationState.PENDING))) {
      throw new ActivitiException("A delegated task cannot be completed, but should be resolved instead.");
    }

    taskEntity.fireEvent("complete");

    if ((Authentication.getAuthenticatedUserId() != null) && (taskEntity.getProcessInstanceId() != null)) {
      taskEntity.getProcessInstance().involveUser(Authentication.getAuthenticatedUserId(), "participant");
    }

    if (Context.getProcessEngineConfiguration().getEventDispatcher().isEnabled()) {
      Context.getProcessEngineConfiguration().getEventDispatcher()
        .dispatchEvent(ActivitiEventBuilder.createEntityWithVariablesEvent(ActivitiEventType.TASK_COMPLETED, taskEntity, this.variables, localScope));
    }

    Context.getCommandContext().getTaskEntityManager().deleteTask(taskEntity, this.comment, false);

    if (taskEntity.getExecutionId() != null) {
      ExecutionEntity execution = taskEntity.getExecution();
      execution.removeTask(taskEntity);
      try
      {
        Context.setExecutionContext(execution);
        execution.signal(null, null);
      } finally {
        Context.removeExecutionContext();
      }
    }

    return null;
  }
}