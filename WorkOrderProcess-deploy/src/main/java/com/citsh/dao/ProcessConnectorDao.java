package com.citsh.dao;

import com.citsh.page.Page;
import java.util.Map;

public abstract interface ProcessConnectorDao
{
  public abstract String startProcess(String paramString1, String paramString2, String paramString3, Map<String, Object> paramMap);

  public abstract Page findDeployments(String paramString, Page paramPage);

  public abstract Page findProcessDefinitions(String paramString, Page paramPage);

  public abstract Page findProcessInstances(String paramString, Page paramPage);

  public abstract Page findTasks(String paramString, Page paramPage);

  public abstract Page findHistoricProcessInstances(String paramString, Page paramPage);

  public abstract Page findHistoricActivityInstances(String paramString, Page paramPage);

  public abstract Page findHistoricTaskInstances(String paramString, Page paramPage);
}