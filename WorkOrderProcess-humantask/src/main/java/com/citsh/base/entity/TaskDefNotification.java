package com.citsh.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
* TaskDefNotification 任务定义提醒.
*/
@Entity
@Table(name = "TASK_DEF_NOTIFICATION")
public class TaskDefNotification implements java.io.Serializable {
 private static final long serialVersionUID = 0L;

 /** 主键. */
 private Long id;

 /** 外键，任务定义. */
 private TaskDefBase taskDefBase;

 /** 事件名称. */
 private String eventName;

 /** 接收人. */
 private String receiver;

 /** 类型. */
 private String type;

 /** 模板编号. */
 private String templateCode;

 public TaskDefNotification() {
 }

 public TaskDefNotification(Long id) {
     this.id = id;
 }

 public TaskDefNotification(Long id, TaskDefBase taskDefBase,
         String eventName, String receiver, String type, String templateCode) {
     this.id = id;
     this.taskDefBase = taskDefBase;
     this.eventName = eventName;
     this.receiver = receiver;
     this.type = type;
     this.templateCode = templateCode;
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

 /** @return 外键，任务定义. */
 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "BASE_ID")
 public TaskDefBase getTaskDefBase() {
     return this.taskDefBase;
 }

 /**
  * @param taskDefBase
  *            外键，任务定义.
  */
 public void setTaskDefBase(TaskDefBase taskDefBase) {
     this.taskDefBase = taskDefBase;
 }

 /** @return 事件名称. */
 @Column(name = "EVENT_NAME", length = 100)
 public String getEventName() {
     return this.eventName;
 }

 /**
  * @param eventName
  *            事件名称.
  */
 public void setEventName(String eventName) {
     this.eventName = eventName;
 }

 /** @return 接收人. */
 @Column(name = "RECEIVER", length = 200)
 public String getReceiver() {
     return this.receiver;
 }

 /**
  * @param receiver
  *            接收人.
  */
 public void setReceiver(String receiver) {
     this.receiver = receiver;
 }

 /** @return 类型. */
 @Column(name = "TYPE", length = 50)
 public String getType() {
     return this.type;
 }

 /**
  * @param type
  *            类型.
  */
 public void setType(String type) {
     this.type = type;
 }

 /** @return 模板编号. */
 @Column(name = "TEMPLATE_CODE", length = 200)
 public String getTemplateCode() {
     return this.templateCode;
 }

 /**
  * @param templateCode
  *            模板编号.
  */
 public void setTemplateCode(String templateCode) {
     this.templateCode = templateCode;
 }
}

