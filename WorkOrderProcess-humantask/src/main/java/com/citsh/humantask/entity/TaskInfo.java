package com.citsh.humantask.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="TASK_INFO")
public class TaskInfo
  implements Serializable
{
  private static final long serialVersionUID = 0L;
  private Long id;
  private TaskInfo taskInfo;
  private String businessKey;
  private String code;
  private String name;
  private String description;
  private Integer priority;
  private String category;
  private String form;
  private String tenantId;
  private String status;
  private String suspendStatus;
  private String delegateStatus;
  private String completeStatus;
  private String skipStatus;
  private String escalateStatus;
  private String copyStatus;
  private String copyTaskId;
  private String presentationName;
  private String presentationSubject;
  private Date createTime;
  private Date activationTime;
  private Date claimTime;
  private Date completeTime;
  private Date expirationTime;
  private Date lastModifiedTime;
  private String duration;
  private String creator;
  private String initiator;
  private String assignee;
  private String owner;
  private String lastModifier;
  private String swimlane;
  private String taskId;
  private String executionId;
  private String processInstanceId;
  private String processDefinitionId;
  private String attr1;
  private String attr2;
  private String attr3;
  private String attr4;
  private String attr5;
  private String processBusinessKey;
  private String processStarter;
  private String catalog;
  private String action;
  private String comment;
  private String message;
  private Set<TaskDeadline> taskDeadlines = new HashSet(0);

  private Set<TaskInfo> taskInfos = new HashSet(0);

  private Set<TaskParticipant> taskParticipants = new HashSet(0);

  private Set<TaskLog> taskLogs = new HashSet(0);

  public TaskInfo() {
  }

  public TaskInfo(Long id) {
    this.id = id;
  }

  public TaskInfo(Long id, TaskInfo taskInfo, String businessKey, String code, String name, String description, Integer priority, String category, String form, String tenantId, String status, String suspendStatus, String delegateStatus, String completeStatus, String skipStatus, String escalateStatus, String copyStatus, String copyTaskId, String presentationName, String presentationSubject, Date createTime, Date activationTime, Date claimTime, Date completeTime, Date expirationTime, Date lastModifiedTime, String duration, String creator, String initiator, String assignee, String owner, String lastModifier, String swimlane, String taskId, String executionId, String processInstanceId, String processDefinitionId, String attr1, String attr2, String attr3, String attr4, String attr5, String processBusinessKey, String processStarter, String catalog, String action, String comment, String message, Set<TaskDeadline> taskDeadlines, Set<TaskInfo> taskInfos, Set<TaskParticipant> taskParticipants, Set<TaskLog> taskLogs)
  {
    this.id = id;
    this.taskInfo = taskInfo;
    this.businessKey = businessKey;
    this.code = code;
    this.name = name;
    this.description = description;
    this.priority = priority;
    this.category = category;
    this.form = form;
    this.tenantId = tenantId;
    this.status = status;
    this.suspendStatus = suspendStatus;
    this.delegateStatus = delegateStatus;
    this.completeStatus = completeStatus;
    this.skipStatus = skipStatus;
    this.escalateStatus = escalateStatus;
    this.copyStatus = copyStatus;
    this.copyTaskId = copyTaskId;
    this.presentationName = presentationName;
    this.presentationSubject = presentationSubject;
    this.createTime = createTime;
    this.activationTime = activationTime;
    this.claimTime = claimTime;
    this.completeTime = completeTime;
    this.expirationTime = expirationTime;
    this.lastModifiedTime = lastModifiedTime;
    this.duration = duration;
    this.creator = creator;
    this.initiator = initiator;
    this.assignee = assignee;
    this.owner = owner;
    this.lastModifier = lastModifier;
    this.swimlane = swimlane;
    this.taskId = taskId;
    this.executionId = executionId;
    this.processInstanceId = processInstanceId;
    this.processDefinitionId = processDefinitionId;
    this.attr1 = attr1;
    this.attr2 = attr2;
    this.attr3 = attr3;
    this.attr4 = attr4;
    this.attr5 = attr5;
    this.processBusinessKey = processBusinessKey;
    this.processStarter = processStarter;
    this.catalog = catalog;
    this.action = action;
    this.comment = comment;
    this.message = message;
    this.taskDeadlines = taskDeadlines;
    this.taskInfos = taskInfos;
    this.taskParticipants = taskParticipants;
    this.taskLogs = taskLogs;
  }
  @Id
  @Column(name="ID", unique=true, nullable=false)
  public Long getId() {
    return this.id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="PARENT_ID")
  public TaskInfo getTaskInfo() {
    return this.taskInfo;
  }

  public void setTaskInfo(TaskInfo taskInfo)
  {
    this.taskInfo = taskInfo;
  }

  @Column(name="BUSINESS_KEY", length=100)
  public String getBusinessKey() {
    return this.businessKey;
  }

  public void setBusinessKey(String businessKey)
  {
    this.businessKey = businessKey;
  }

  @Column(name="CODE", length=100)
  public String getCode() {
    return this.code;
  }

  public void setCode(String code)
  {
    this.code = code;
  }

  @Column(name="NAME", length=200)
  public String getName() {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  @Column(name="DESCRIPTION", length=200)
  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  @Column(name="PRIORITY")
  public Integer getPriority() {
    return this.priority;
  }

  public void setPriority(Integer priority)
  {
    this.priority = priority;
  }

  @Column(name="CATEGORY", length=100)
  public String getCategory() {
    return this.category;
  }

  public void setCategory(String category)
  {
    this.category = category;
  }

  @Column(name="FORM", length=100)
  public String getForm() {
    return this.form;
  }

  public void setForm(String form)
  {
    this.form = form;
  }

  @Column(name="TENANT_ID", length=100)
  public String getTenantId() {
    return this.tenantId;
  }

  public void setTenantId(String tenantId)
  {
    this.tenantId = tenantId;
  }

  @Column(name="STATUS", length=50)
  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status)
  {
    this.status = status;
  }

  @Column(name="SUSPEND_STATUS", length=50)
  public String getSuspendStatus() {
    return this.suspendStatus;
  }

  public void setSuspendStatus(String suspendStatus)
  {
    this.suspendStatus = suspendStatus;
  }

  @Column(name="DELEGATE_STATUS", length=50)
  public String getDelegateStatus() {
    return this.delegateStatus;
  }

  public void setDelegateStatus(String delegateStatus)
  {
    this.delegateStatus = delegateStatus;
  }

  @Column(name="COMPLETE_STATUS", length=50)
  public String getCompleteStatus() {
    return this.completeStatus;
  }

  public void setCompleteStatus(String completeStatus)
  {
    this.completeStatus = completeStatus;
  }

  @Column(name="SKIP_STATUS", length=50)
  public String getSkipStatus() {
    return this.skipStatus;
  }

  public void setSkipStatus(String skipStatus)
  {
    this.skipStatus = skipStatus;
  }

  @Column(name="ESCALATE_STATUS", length=50)
  public String getEscalateStatus() {
    return this.escalateStatus;
  }

  public void setEscalateStatus(String escalateStatus)
  {
    this.escalateStatus = escalateStatus;
  }

  @Column(name="COPY_STATUS", length=50)
  public String getCopyStatus() {
    return this.copyStatus;
  }

  public void setCopyStatus(String copyStatus)
  {
    this.copyStatus = copyStatus;
  }

  @Column(name="COPY_TASK_ID", length=64)
  public String getCopyTaskId() {
    return this.copyTaskId;
  }

  public void setCopyTaskId(String copyTaskId)
  {
    this.copyTaskId = copyTaskId;
  }

  @Column(name="PRESENTATION_NAME", length=200)
  public String getPresentationName() {
    return this.presentationName;
  }

  public void setPresentationName(String presentationName)
  {
    this.presentationName = presentationName;
  }

  @Column(name="PRESENTATION_SUBJECT", length=200)
  public String getPresentationSubject() {
    return this.presentationSubject;
  }

  public void setPresentationSubject(String presentationSubject)
  {
    this.presentationSubject = presentationSubject;
  }
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="CREATE_TIME", length=26)
  public Date getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="ACTIVATION_TIME", length=26)
  public Date getActivationTime() {
    return this.activationTime;
  }

  public void setActivationTime(Date activationTime)
  {
    this.activationTime = activationTime;
  }
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="CLAIM_TIME", length=26)
  public Date getClaimTime() {
    return this.claimTime;
  }

  public void setClaimTime(Date claimTime)
  {
    this.claimTime = claimTime;
  }
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="COMPLETE_TIME", length=26)
  public Date getCompleteTime() {
    return this.completeTime;
  }

  public void setCompleteTime(Date completeTime)
  {
    this.completeTime = completeTime;
  }
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="EXPIRATION_TIME", length=26)
  public Date getExpirationTime() {
    return this.expirationTime;
  }

  public void setExpirationTime(Date expirationTime)
  {
    this.expirationTime = expirationTime;
  }
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="LAST_MODIFIED_TIME", length=26)
  public Date getLastModifiedTime() {
    return this.lastModifiedTime;
  }

  public void setLastModifiedTime(Date lastModifiedTime)
  {
    this.lastModifiedTime = lastModifiedTime;
  }

  @Column(name="DURATION", length=50)
  public String getDuration() {
    return this.duration;
  }

  public void setDuration(String duration)
  {
    this.duration = duration;
  }

  @Column(name="CREATOR", length=64)
  public String getCreator() {
    return this.creator;
  }

  public void setCreator(String creator)
  {
    this.creator = creator;
  }

  @Column(name="INITIATOR", length=64)
  public String getInitiator() {
    return this.initiator;
  }

  public void setInitiator(String initiator)
  {
    this.initiator = initiator;
  }

  @Column(name="ASSIGNEE", length=64)
  public String getAssignee() {
    return this.assignee;
  }

  public void setAssignee(String assignee)
  {
    this.assignee = assignee;
  }

  @Column(name="OWNER", length=64)
  public String getOwner() {
    return this.owner;
  }

  public void setOwner(String owner)
  {
    this.owner = owner;
  }

  @Column(name="LAST_MODIFIER", length=64)
  public String getLastModifier() {
    return this.lastModifier;
  }

  public void setLastModifier(String lastModifier)
  {
    this.lastModifier = lastModifier;
  }

  @Column(name="SWIMLANE", length=100)
  public String getSwimlane() {
    return this.swimlane;
  }

  public void setSwimlane(String swimlane)
  {
    this.swimlane = swimlane;
  }

  @Column(name="TASK_ID", length=200)
  public String getTaskId() {
    return this.taskId;
  }

  public void setTaskId(String taskId)
  {
    this.taskId = taskId;
  }

  @Column(name="EXECUTION_ID", length=200)
  public String getExecutionId() {
    return this.executionId;
  }

  public void setExecutionId(String executionId)
  {
    this.executionId = executionId;
  }

  @Column(name="PROCESS_INSTANCE_ID", length=200)
  public String getProcessInstanceId() {
    return this.processInstanceId;
  }

  public void setProcessInstanceId(String processInstanceId)
  {
    this.processInstanceId = processInstanceId;
  }

  @Column(name="PROCESS_DEFINITION_ID", length=200)
  public String getProcessDefinitionId() {
    return this.processDefinitionId;
  }

  public void setProcessDefinitionId(String processDefinitionId)
  {
    this.processDefinitionId = processDefinitionId;
  }

  @Column(name="ATTR1", length=100)
  public String getAttr1() {
    return this.attr1;
  }

  public void setAttr1(String attr1)
  {
    this.attr1 = attr1;
  }

  @Column(name="ATTR2", length=100)
  public String getAttr2() {
    return this.attr2;
  }

  public void setAttr2(String attr2)
  {
    this.attr2 = attr2;
  }

  @Column(name="ATTR3", length=100)
  public String getAttr3() {
    return this.attr3;
  }

  public void setAttr3(String attr3)
  {
    this.attr3 = attr3;
  }

  @Column(name="ATTR4", length=100)
  public String getAttr4() {
    return this.attr4;
  }

  public void setAttr4(String attr4)
  {
    this.attr4 = attr4;
  }

  @Column(name="ATTR5", length=100)
  public String getAttr5() {
    return this.attr5;
  }

  public void setAttr5(String attr5)
  {
    this.attr5 = attr5;
  }

  @Column(name="PROCESS_BUSINESS_KEY", length=200)
  public String getProcessBusinessKey() {
    return this.processBusinessKey;
  }

  public void setProcessBusinessKey(String processBusinessKey)
  {
    this.processBusinessKey = processBusinessKey;
  }

  @Column(name="PROCESS_STARTER", length=64)
  public String getProcessStarter() {
    return this.processStarter;
  }

  public void setProcessStarter(String processStarter)
  {
    this.processStarter = processStarter;
  }

  @Column(name="CATALOG", length=100)
  public String getCatalog() {
    return this.catalog;
  }

  public void setCatalog(String catalog)
  {
    this.catalog = catalog;
  }

  @Column(name="ACTION", length=200)
  public String getAction() {
    return this.action;
  }

  public void setAction(String action)
  {
    this.action = action;
  }

  @Column(name="COMMENT", length=200)
  public String getComment() {
    return this.comment;
  }

  public void setComment(String comment)
  {
    this.comment = comment;
  }

  @Column(name="MESSAGE", length=200)
  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message)
  {
    this.message = message;
  }

  @OneToMany(fetch=FetchType.LAZY, mappedBy="taskInfo")
  public Set<TaskDeadline> getTaskDeadlines() {
    return this.taskDeadlines;
  }

  public void setTaskDeadlines(Set<TaskDeadline> taskDeadlines)
  {
    this.taskDeadlines = taskDeadlines;
  }

  @OneToMany(fetch=FetchType.LAZY, mappedBy="taskInfo")
  public Set<TaskInfo> getTaskInfos() {
    return this.taskInfos;
  }

  public void setTaskInfos(Set<TaskInfo> taskInfos)
  {
    this.taskInfos = taskInfos;
  }

  @OneToMany(fetch=FetchType.LAZY, mappedBy="taskInfo")
  public Set<TaskParticipant> getTaskParticipants() {
    return this.taskParticipants;
  }

  public void setTaskParticipants(Set<TaskParticipant> taskParticipants)
  {
    this.taskParticipants = taskParticipants;
  }

  @OneToMany(fetch=FetchType.LAZY, mappedBy="taskInfo")
  public Set<TaskLog> getTaskLogs() {
    return this.taskLogs;
  }

  public void setTaskLogs(Set<TaskLog> taskLogs)
  {
    this.taskLogs = taskLogs;
  }
}