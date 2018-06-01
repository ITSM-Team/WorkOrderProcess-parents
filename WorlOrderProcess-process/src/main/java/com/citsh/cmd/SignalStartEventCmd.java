package com.citsh.cmd;

import java.lang.reflect.Method;
import org.activiti.engine.impl.bpmn.behavior.FlowNodeActivityBehavior;
import org.activiti.engine.impl.cmd.NeedsActiveExecutionCmd;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignalStartEventCmd extends NeedsActiveExecutionCmd<Object>
{
  private static final long serialVersionUID = 1L;
  private static Logger logger = LoggerFactory.getLogger(SignalStartEventCmd.class)
    ;

  public SignalStartEventCmd(String executionId) {
    super(executionId);
  }

  protected Object execute(CommandContext commandContext, ExecutionEntity execution)
  {
    try
    {
      FlowNodeActivityBehavior activityBehavior = (FlowNodeActivityBehavior)execution
        .getActivity().getActivityBehavior();
      Method method = FlowNodeActivityBehavior.class.getDeclaredMethod("leave", new Class[] { ActivityExecution.class });

      method.setAccessible(true);
      method.invoke(activityBehavior, new Object[] { execution });
      method.setAccessible(false);
    } catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
    }

    return null;
  }

  protected String getSuspendedExceptionMessage()
  {
    return "Cannot signal an execution that is suspended";
  }
}