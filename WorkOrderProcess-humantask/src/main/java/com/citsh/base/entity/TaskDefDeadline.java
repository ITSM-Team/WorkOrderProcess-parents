package com.citsh.base.entity;

import java.io.Serializable;
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

@Entity
@Table(name="TASK_DEF_DEADLINE")
public class TaskDefDeadline
  implements Serializable
{
  private static final long serialVersionUID = 0L;
  private Long id;
  private TaskDefBase taskDefBase;
  private String type;
  private String duration;
  private String status;
  private String escalationType;
  private String notificationCondition;
  private String notificationType;
  private String notificationReceiver;
  private String notificationTemplateCode;
  private String reassginment;
  private String operation;
  private Set<TaskDefEscalation> taskDefEscalations = new HashSet(0);

  public TaskDefDeadline()
  {
  }

  public TaskDefDeadline(Long id) {
    this.id = id;
  }

  public TaskDefDeadline(Long id, TaskDefBase taskDefBase, String type, String duration, String status, String escalationType, String notificationCondition, String notificationType, String notificationReceiver, String notificationTemplateCode, String reassginment, String operation, Set<TaskDefEscalation> taskDefEscalations)
  {
    this.id = id;
    this.taskDefBase = taskDefBase;
    this.type = type;
    this.duration = duration;
    this.status = status;
    this.escalationType = escalationType;
    this.notificationCondition = notificationCondition;
    this.notificationType = notificationType;
    this.notificationReceiver = notificationReceiver;
    this.notificationTemplateCode = notificationTemplateCode;
    this.reassginment = reassginment;
    this.operation = operation;
    this.taskDefEscalations = taskDefEscalations;
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
  @JoinColumn(name="BASE_ID")
  public TaskDefBase getTaskDefBase() {
    return this.taskDefBase;
  }

  public void setTaskDefBase(TaskDefBase taskDefBase)
  {
    this.taskDefBase = taskDefBase;
  }

  @Column(name="TYPE", length=50)
  public String getType() {
    return this.type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  @Column(name="DURATION", length=100)
  public String getDuration() {
    return this.duration;
  }

  public void setDuration(String duration)
  {
    this.duration = duration;
  }

  @Column(name="STATUS", length=50)
  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status)
  {
    this.status = status;
  }

  @Column(name="ESCALATION_TYPE", length=50)
  public String getEscalationType() {
    return this.escalationType;
  }

  public void setEscalationType(String escalationType)
  {
    this.escalationType = escalationType;
  }

  @Column(name="NOTIFICATION_CONDITION", length=200)
  public String getNotificationCondition() {
    return this.notificationCondition;
  }

  public void setNotificationCondition(String notificationCondition)
  {
    this.notificationCondition = notificationCondition;
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

  @Column(name="REASSGINMENT", length=200)
  public String getReassginment() {
    return this.reassginment;
  }

  public void setReassginment(String reassginment)
  {
    this.reassginment = reassginment;
  }

  @Column(name="OPERATION", length=200)
  public String getOperation() {
    return this.operation;
  }

  public void setOperation(String operation)
  {
    this.operation = operation;
  }

  @OneToMany(fetch=FetchType.LAZY, mappedBy="taskDefDeadline")
  public Set<TaskDefEscalation> getTaskDefEscalations() {
    return this.taskDefEscalations;
  }

  public void setTaskDefEscalations(Set<TaskDefEscalation> taskDefEscalations)
  {
    this.taskDefEscalations = taskDefEscalations;
  }
}