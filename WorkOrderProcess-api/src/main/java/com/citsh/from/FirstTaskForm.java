package com.citsh.from;

public class FirstTaskForm
{
  private boolean exists;
  private boolean taskForm;
  private String processDefinitionId;
  private String activityId;
  private String assignee;
  private String formKey;
  private String initiatorName;

  public boolean isExists()
  {
    return this.exists;
  }

  public void setExists(boolean exists) {
    this.exists = exists;
  }

  public boolean isTaskForm() {
    return this.taskForm;
  }

  public void setTaskForm(boolean taskForm) {
    this.taskForm = taskForm;
  }

  public String getProcessDefinitionId() {
    return this.processDefinitionId;
  }

  public void setProcessDefinitionId(String processDefinitionId) {
    this.processDefinitionId = processDefinitionId;
  }

  public String getActivityId() {
    return this.activityId;
  }

  public void setActivityId(String activityId) {
    this.activityId = activityId;
  }

  public String getAssignee() {
    return this.assignee;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }

  public String getFormKey() {
    return this.formKey;
  }

  public void setFormKey(String formKey) {
    this.formKey = formKey;
  }

  public String getInitiatorName() {
    return this.initiatorName;
  }

  public void setInitiatorName(String initiatorName) {
    this.initiatorName = initiatorName;
  }
}