package com.citsh.humantask;

import java.util.ArrayList;
import java.util.List;

public class TaskDefinitionDTO
{
  public static final String CATALOG_ASSIGNEE = "assignee";
  public static final String CATALOG_CANDIDATE = "candidate";
  public static final String CATALOG_NOTIFICATION = "notification";
  public static final String TYPE_USER = "user";
  public static final String TYPE_GROUP = "group";
  private String id;
  private String code;
  private String name;
  private String assignStrategy;
  private FormDTO form;
  private CounterSignDTO counterSign;
  private List<String> operations = new ArrayList();
  private List<TaskUserDTO> taskUsers = new ArrayList();
  private List<DeadlineDTO> deadlines = new ArrayList();
  private String processDefinitionId;

  public String getId()
  {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAssignStrategy() {
    return this.assignStrategy;
  }

  public void setAssignStrategy(String assignStrategy) {
    this.assignStrategy = assignStrategy;
  }

  public FormDTO getForm() {
    return this.form;
  }

  public void setForm(FormDTO form) {
    this.form = form;
  }

  public CounterSignDTO getCounterSign() {
    return this.counterSign;
  }

  public void setCounterSign(CounterSignDTO counterSign) {
    this.counterSign = counterSign;
  }

  public List<String> getOperations() {
    return this.operations;
  }

  public void setOperations(List<String> operations) {
    this.operations = operations;
  }

  public List<TaskUserDTO> getTaskUsers() {
    return this.taskUsers;
  }

  public void setTaskUsers(List<TaskUserDTO> taskUsers) {
    this.taskUsers = taskUsers;
  }

  public List<DeadlineDTO> getDeadlines() {
    return this.deadlines;
  }

  public void setDeadlines(List<DeadlineDTO> deadlines) {
    this.deadlines = deadlines;
  }

  public String getProcessDefinitionId() {
    return this.processDefinitionId;
  }

  public void setProcessDefinitionId(String processDefinitionId) {
    this.processDefinitionId = processDefinitionId;
  }

  public void setAssignee(String assignee) {
    for (TaskUserDTO taskUser : this.taskUsers) {
      if ("assignee".equals(taskUser.getCatalog())) {
        taskUser.setValue(assignee);

        return;
      }
    }

    TaskUserDTO taskUser = new TaskUserDTO();
    taskUser.setCatalog("assignee");
    taskUser.setType("user");
    taskUser.setValue(assignee);
    this.taskUsers.add(taskUser);
  }

  public void addCandidateUser(String candidateUser) {
    TaskUserDTO taskUser = new TaskUserDTO();
    taskUser.setCatalog("candidate");
    taskUser.setType("user");
    taskUser.setValue(candidateUser);
    this.taskUsers.add(taskUser);
  }

  public void addCandidateGroup(String candidateGroup) {
    TaskUserDTO taskUser = new TaskUserDTO();
    taskUser.setCatalog("candidate");
    taskUser.setType("group");
    taskUser.setValue(candidateGroup);
    this.taskUsers.add(taskUser);
  }
}

