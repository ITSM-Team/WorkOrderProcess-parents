package com.citsh.process;

public class ProcessDTO
{
  private String processDefinitionId;
  private String processDefinitionName;
  private boolean configTask;

  public String getProcessDefinitionId()
  {
    return this.processDefinitionId;
  }

  public void setProcessDefinitionId(String processDefinitionId) {
    this.processDefinitionId = processDefinitionId;
  }

  public String getProcessDefinitionName() {
    return this.processDefinitionName;
  }

  public void setProcessDefinitionName(String processDefinitionName) {
    this.processDefinitionName = processDefinitionName;
  }

  public boolean isConfigTask() {
    return this.configTask;
  }

  public void setConfigTask(boolean configTask) {
    this.configTask = configTask;
  }
}