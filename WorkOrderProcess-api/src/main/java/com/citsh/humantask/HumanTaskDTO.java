package com.citsh.humantask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HumanTaskDTO
{
  private String id;
  private String businessKey;
  private String name;
  private String presentationSubject;
  private String description;
  private String assignee;
  private String owner;
  private String delegateStatus;
  private Date createTime;
  private String suspendStatus;
  private String code;
  private String taskId;
  private String executionId;
  private String processInstanceId;
  private String processDefinitionId;
  private String processStarter;
  private String taskDefinitionKey;
  private int priority;
  private String duration;
  private String tenantId;
  private String category;
  private String form;
  private String status;
  private Date completeTime;
  private String parentId;
  private String catalog;
  private String action;
  private String comment;
  private String message;
  private List<HumanTaskDTO> children = new ArrayList();

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getBusinessKey() {
    return this.businessKey;
  }

  public void setBusinessKey(String businessKey) {
    this.businessKey = businessKey;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPresentationSubject() {
    return this.presentationSubject;
  }

  public void setPresentationSubject(String presentationSubject) {
    this.presentationSubject = presentationSubject;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAssignee() {
    return this.assignee;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }

  public String getOwner() {
    return this.owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getDelegateStatus() {
    return this.delegateStatus;
  }

  public void setDelegateStatus(String delegateStatus) {
    this.delegateStatus = delegateStatus;
  }

  public Date getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getSuspendStatus() {
    return this.suspendStatus;
  }

  public void setSuspendStatus(String suspendStatus) {
    this.suspendStatus = suspendStatus;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getTaskId() {
    return this.taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public String getExecutionId() {
    return this.executionId;
  }

  public void setExecutionId(String executionId) {
    this.executionId = executionId;
  }

  public String getProcessInstanceId() {
    return this.processInstanceId;
  }

  public void setProcessInstanceId(String processInstanceId) {
    this.processInstanceId = processInstanceId;
  }

  public String getProcessDefinitionId() {
    return this.processDefinitionId;
  }

  public void setProcessDefinitionId(String processDefinitionId) {
    this.processDefinitionId = processDefinitionId;
  }

  public String getProcessStarter() {
    return this.processStarter;
  }

  public void setProcessStarter(String processStarter) {
    this.processStarter = processStarter;
  }

  public String getTaskDefinitionKey() {
    return this.taskDefinitionKey;
  }

  public void setTaskDefinitionKey(String taskDefinitionKey) {
    this.taskDefinitionKey = taskDefinitionKey;
  }

  public int getPriority() {
    return this.priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  public String getDuration() {
    return this.duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public String getTenantId() {
    return this.tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public String getCategory() {
    return this.category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getForm() {
    return this.form;
  }

  public void setForm(String form) {
    this.form = form;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Date getCompleteTime() {
    return this.completeTime;
  }

  public void setCompleteTime(Date completeTime) {
    this.completeTime = completeTime;
  }

  public String getParentId() {
    return this.parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getCatalog() {
    return this.catalog;
  }

  public void setCatalog(String catalog) {
    this.catalog = catalog;
  }

  public String getAction() {
    return this.action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getComment() {
    return this.comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<HumanTaskDTO> getChildren() {
    return this.children;
  }

  public void setChildren(List<HumanTaskDTO> children) {
    this.children = children;
  }
}