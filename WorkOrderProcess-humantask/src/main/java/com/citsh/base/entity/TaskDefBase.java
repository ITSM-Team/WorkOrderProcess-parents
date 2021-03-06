package com.citsh.base.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
* TaskDefBase 任务定义.
*/
@Entity
@Table(name = "TASK_DEF_BASE")
public class TaskDefBase implements java.io.Serializable {
 private static final long serialVersionUID = 0L;

 /** 主键. */
 private Long id;

 /** 主键. */
 private String code;

 /** 名称. */
 private String name;

 /** 流程定义ID. */
 private String processDefinitionId;

 /** 表单编号. */
 private String formKey;

 /** 表单类型. */
 private String formType;

 /** 会签类型. */
 private String countersignType;

 /** 会签用户. */
 private String countersignUser;

 /** 会签策略. */
 private String countersignStrategy;

 /** 会签通过率. */
 private Integer countersignRate;

 /** 分配策略. */
 private String assignStrategy;

 /** 流程标识. */
 private String processDefinitionKey;

 /** 流程版本. */
 private Integer processDefinitionVersion;

 /** . */
 private Set<TaskDefDeadline> taskDefDeadlines = new HashSet<TaskDefDeadline>(
         0);

 /** . */
 private Set<TaskDefNotification> taskDefNotifications = new HashSet<TaskDefNotification>(
         0);

 /** . */
 private Set<TaskDefUser> taskDefUsers = new HashSet<TaskDefUser>(0);

 /** . */
 private Set<TaskDefOperation> taskDefOperations = new HashSet<TaskDefOperation>(
         0);

 public TaskDefBase() {
 }

 public TaskDefBase(Long id) {
     this.id = id;
 }

 public TaskDefBase(Long id, String code, String name,
         String processDefinitionId, String formKey, String formType,
         String countersignType, String countersignUser,
         String countersignStrategy, Integer countersignRate,
         String assignStrategy, String processDefinitionKey,
         Integer processDefinitionVersion,
         Set<TaskDefDeadline> taskDefDeadlines,
         Set<TaskDefNotification> taskDefNotifications,
         Set<TaskDefUser> taskDefUsers,
         Set<TaskDefOperation> taskDefOperations) {
     this.id = id;
     this.code = code;
     this.name = name;
     this.processDefinitionId = processDefinitionId;
     this.formKey = formKey;
     this.formType = formType;
     this.countersignType = countersignType;
     this.countersignUser = countersignUser;
     this.countersignStrategy = countersignStrategy;
     this.countersignRate = countersignRate;
     this.assignStrategy = assignStrategy;
     this.processDefinitionKey = processDefinitionKey;
     this.processDefinitionVersion = processDefinitionVersion;
     this.taskDefDeadlines = taskDefDeadlines;
     this.taskDefNotifications = taskDefNotifications;
     this.taskDefUsers = taskDefUsers;
     this.taskDefOperations = taskDefOperations;
 }

 /** @return 主键. */
 @Id
 @Column(name = "ID", unique = true, nullable = false)
 public Long getId() {
     return this.id;
 }

 /**
  * @param id
  *            主键.
  */
 public void setId(Long id) {
     this.id = id;
 }

 /** @return 主键. */
 @Column(name = "CODE", length = 100)
 public String getCode() {
     return this.code;
 }

 /**
  * @param code
  *            主键.
  */
 public void setCode(String code) {
     this.code = code;
 }

 /** @return 名称. */
 @Column(name = "NAME", length = 200)
 public String getName() {
     return this.name;
 }

 /**
  * @param name
  *            名称.
  */
 public void setName(String name) {
     this.name = name;
 }

 /** @return 流程定义ID. */
 @Column(name = "PROCESS_DEFINITION_ID", length = 200)
 public String getProcessDefinitionId() {
     return this.processDefinitionId;
 }

 /**
  * @param processDefinitionId
  *            流程定义ID.
  */
 public void setProcessDefinitionId(String processDefinitionId) {
     this.processDefinitionId = processDefinitionId;
 }

 /** @return 表单编号. */
 @Column(name = "FORM_KEY", length = 200)
 public String getFormKey() {
     return this.formKey;
 }

 /**
  * @param formKey
  *            表单编号.
  */
 public void setFormKey(String formKey) {
     this.formKey = formKey;
 }

 /** @return 表单类型. */
 @Column(name = "FORM_TYPE", length = 50)
 public String getFormType() {
     return this.formType;
 }

 /**
  * @param formType
  *            表单类型.
  */
 public void setFormType(String formType) {
     this.formType = formType;
 }

 /** @return 会签类型. */
 @Column(name = "COUNTERSIGN_TYPE", length = 50)
 public String getCountersignType() {
     return this.countersignType;
 }

 /**
  * @param countersignType
  *            会签类型.
  */
 public void setCountersignType(String countersignType) {
     this.countersignType = countersignType;
 }

 /** @return 会签用户. */
 @Column(name = "COUNTERSIGN_USER", length = 200)
 public String getCountersignUser() {
     return this.countersignUser;
 }

 /**
  * @param countersignUser
  *            会签用户.
  */
 public void setCountersignUser(String countersignUser) {
     this.countersignUser = countersignUser;
 }

 /** @return 会签策略. */
 @Column(name = "COUNTERSIGN_STRATEGY", length = 50)
 public String getCountersignStrategy() {
     return this.countersignStrategy;
 }

 /**
  * @param countersignStrategy
  *            会签策略.
  */
 public void setCountersignStrategy(String countersignStrategy) {
     this.countersignStrategy = countersignStrategy;
 }

 /** @return 会签通过率. */
 @Column(name = "COUNTERSIGN_RATE")
 public Integer getCountersignRate() {
     return this.countersignRate;
 }

 /**
  * @param countersignRate
  *            会签通过率.
  */
 public void setCountersignRate(Integer countersignRate) {
     this.countersignRate = countersignRate;
 }

 /** @return 分配策略. */
 @Column(name = "ASSIGN_STRATEGY", length = 100)
 public String getAssignStrategy() {
     return this.assignStrategy;
 }

 /**
  * @param assignStrategy
  *            分配策略.
  */
 public void setAssignStrategy(String assignStrategy) {
     this.assignStrategy = assignStrategy;
 }

 /** @return 流程标识. */
 @Column(name = "PROCESS_DEFINITION_KEY", length = 100)
 public String getProcessDefinitionKey() {
     return this.processDefinitionKey;
 }

 /**
  * @param processDefinitionKey
  *            流程标识.
  */
 public void setProcessDefinitionKey(String processDefinitionKey) {
     this.processDefinitionKey = processDefinitionKey;
 }

 /** @return 流程版本. */
 @Column(name = "PROCESS_DEFINITION_VERSION")
 public Integer getProcessDefinitionVersion() {
     return this.processDefinitionVersion;
 }

 /**
  * @param processDefinitionVersion
  *            流程版本.
  */
 public void setProcessDefinitionVersion(Integer processDefinitionVersion) {
     this.processDefinitionVersion = processDefinitionVersion;
 }

 /** @return . */
 @OneToMany(fetch = FetchType.LAZY, mappedBy = "taskDefBase")
 public Set<TaskDefDeadline> getTaskDefDeadlines() {
     return this.taskDefDeadlines;
 }

 /**
  * @param taskDefDeadlines
  *            .
  */
 public void setTaskDefDeadlines(Set<TaskDefDeadline> taskDefDeadlines) {
     this.taskDefDeadlines = taskDefDeadlines;
 }

 /** @return . */
 @OneToMany(fetch = FetchType.LAZY, mappedBy = "taskDefBase")
 public Set<TaskDefNotification> getTaskDefNotifications() {
     return this.taskDefNotifications;
 }

 /**
  * @param taskDefNotifications
  *            .
  */
 public void setTaskDefNotifications(
         Set<TaskDefNotification> taskDefNotifications) {
     this.taskDefNotifications = taskDefNotifications;
 }

 /** @return . */
 @OneToMany(fetch = FetchType.LAZY, mappedBy = "taskDefBase")
 public Set<TaskDefUser> getTaskDefUsers() {
     return this.taskDefUsers;
 }

 /**
  * @param taskDefUsers
  *            .
  */
 public void setTaskDefUsers(Set<TaskDefUser> taskDefUsers) {
     this.taskDefUsers = taskDefUsers;
 }

 /** @return . */
 @OneToMany(fetch = FetchType.LAZY, mappedBy = "taskDefBase")
 public Set<TaskDefOperation> getTaskDefOperations() {
     return this.taskDefOperations;
 }

 /**
  * @param taskDefOperations
  *            .
  */
 public void setTaskDefOperations(Set<TaskDefOperation> taskDefOperations) {
     this.taskDefOperations = taskDefOperations;
 }
}

