package com.citsh.humantask.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="TASK_DEADLINE")
public class TaskDeadline
  implements Serializable
{
  private static final long serialVersionUID = 0L;
  private Long id;
  private TaskInfo taskInfo;
  private String type;
  private Date deadlineTime;
  private Date triggerTime;
  private Integer repeatTimes;
  private String notificationType;
  private String notificationReceiver;
  private String notificationTemplateCode;
  private String reassignment;
  private String operation;

  public TaskDeadline()
  {
  }

  public TaskDeadline(Long id)
  {
    this.id = id;
  }

  public TaskDeadline(Long id, TaskInfo taskInfo, String type, Date deadlineTime, Date triggerTime, Integer repeatTimes, String notificationType, String notificationReceiver, String notificationTemplateCode, String reassignment, String operation)
  {
    this.id = id;
    this.taskInfo = taskInfo;
    this.type = type;
    this.deadlineTime = deadlineTime;
    this.triggerTime = triggerTime;
    this.repeatTimes = repeatTimes;
    this.notificationType = notificationType;
    this.notificationReceiver = notificationReceiver;
    this.notificationTemplateCode = notificationTemplateCode;
    this.reassignment = reassignment;
    this.operation = operation;
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
  @JoinColumn(name="TASK_ID")
  public TaskInfo getTaskInfo() {
    return this.taskInfo;
  }

  public void setTaskInfo(TaskInfo taskInfo)
  {
    this.taskInfo = taskInfo;
  }

  @Column(name="TYPE", length=100)
  public String getType() {
    return this.type;
  }

  public void setType(String type)
  {
    this.type = type;
  }
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="DEADLINE_TIME", length=26)
  public Date getDeadlineTime() {
    return this.deadlineTime;
  }

  public void setDeadlineTime(Date deadlineTime)
  {
    this.deadlineTime = deadlineTime;
  }
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="TRIGGER_TIME", length=26)
  public Date getTriggerTime() {
    return this.triggerTime;
  }

  public void setTriggerTime(Date triggerTime)
  {
    this.triggerTime = triggerTime;
  }

  @Column(name="REPEAT_TIMES")
  public Integer getRepeatTimes() {
    return this.repeatTimes;
  }

  public void setRepeatTimes(Integer repeatTimes)
  {
    this.repeatTimes = repeatTimes;
  }

  @Column(name="NOTIFICATION_TYPE", length=200)
  public String getNotificationType() {
    return this.notificationType;
  }

  public void setNotificationType(String notificationType)
  {
    this.notificationType = notificationType;
  }

  @Column(name="NOTIFICATION_RECEIVER", length=200)
  public String getNotificationReceiver() {
    return this.notificationReceiver;
  }

  public void setNotificationReceiver(String notificationReceiver)
  {
    this.notificationReceiver = notificationReceiver;
  }

  @Column(name="NOTIFICATION_TEMPLATE_CODE", length=200)
  public String getNotificationTemplateCode() {
    return this.notificationTemplateCode;
  }

  public void setNotificationTemplateCode(String notificationTemplateCode)
  {
    this.notificationTemplateCode = notificationTemplateCode;
  }

  @Column(name="REASSIGNMENT", length=200)
  public String getReassignment() {
    return this.reassignment;
  }

  public void setReassignment(String reassignment)
  {
    this.reassignment = reassignment;
  }

  @Column(name="OPERATION", length=200)
  public String getOperation() {
    return this.operation;
  }

  public void setOperation(String operation)
  {
    this.operation = operation;
  }
}