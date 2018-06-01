package com.citsh.process;

import com.citsh.from.FormDTO;
import java.util.List;
import java.util.Map;

public abstract interface InternalProcessConnector
{
  public abstract FormDTO findTaskForm(String paramString);

  public abstract List<ProcessTaskDefinition> findTaskDefinitions(String paramString);

  public abstract void completeTask(String paramString1, String paramString2, Map<String, Object> paramMap);

  public abstract void transfer(String paramString1, String paramString2, String paramString3);

  public abstract void withdrawTask(String paramString);

  public abstract void rollback(String paramString1, String paramString2, String paramString3);

  public abstract void rollbackAuto(String paramString1, String paramString2);

  public abstract void delegateTask(String paramString1, String paramString2);

  public abstract void resolveTask(String paramString);

  public abstract ProcessTaskDefinition findTaskDefinition(String paramString1, String paramString2, String paramString3);

  public abstract String findInitiator(String paramString);

  public abstract String findAssigneeByActivityId(String paramString1, String paramString2);

  public abstract Object executeExpression(String paramString1, String paramString2);

  public abstract String findInitialActivityId(String paramString);

  public abstract String findFirstUserTaskActivityId(String paramString1, String paramString2);

  public abstract void signalExecution(String paramString);
}

/* Location:           E:\work\process\WorkOrderProcess-spi-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.citsh.process.InternalProcessConnector
 * JD-Core Version:    0.6.0
 */