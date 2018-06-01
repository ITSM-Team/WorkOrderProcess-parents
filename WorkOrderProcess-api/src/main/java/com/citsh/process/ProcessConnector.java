package com.citsh.process;

import com.citsh.from.FormDTO;
import com.citsh.page.Page;
import java.util.Map;

public abstract interface ProcessConnector
{
  public abstract FormDTO findStartForm(String paramString);

  public abstract ProcessDTO findProcess(String paramString);

  public abstract String startProcess(String paramString1, String paramString2, String paramString3, Map<String, Object> paramMap);

  public abstract Page findRunningProcessInstances(String paramString1, String paramString2, Page paramPage);

  public abstract Page findCompletedProcessInstances(String paramString1, String paramString2, Page paramPage);

  public abstract Page findInvolvedProcessInstances(String paramString1, String paramString2, Page paramPage);

  public abstract Page findPersonalTasks(String paramString1, String paramString2, Page paramPage);

  public abstract Page findGroupTasks(String paramString1, String paramString2, Page paramPage);

  public abstract Page findHistoryTasks(String paramString1, String paramString2, Page paramPage);

  public abstract Page findDelegatedTasks(String paramString1, String paramString2, Page paramPage);

  public abstract Page findCandidateOrAssignedTasks(String paramString1, String paramString2, Page paramPage);

  public abstract Page findProcessDefinitions(String paramString, Page paramPage);

  public abstract Page findProcessInstances(String paramString, Page paramPage);

  public abstract Page findTasks(String paramString, Page paramPage);

  public abstract Page findDeployments(String paramString, Page paramPage);

  public abstract Page findHistoricProcessInstances(String paramString, Page paramPage);

  public abstract Page findHistoricActivityInstances(String paramString, Page paramPage);

  public abstract Page findHistoricTaskInstances(String paramString, Page paramPage);

  public abstract Page findJobs(String paramString, Page paramPage);
}