package com.citsh.base.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TASK_DEF_ESCALATION")
public class TaskDefEscalation
  implements Serializable
{
  private static final long serialVersionUID = 0L;
  private Long id;
  private TaskDefDeadline taskDefDeadline;
  private String type;
  private String status;
  private String escalationCondition;
  private String value;

  public TaskDefEscalation()
  {
  }

  public TaskDefEscalation(Long id)
  {
    this.id = id;
  }

  public TaskDefEscalation(Long id, TaskDefDeadline taskDefDeadline, String type, String status, String escalationCondition, String value)
  {
    this.id = id;
    this.taskDefDeadline = taskDefDeadline;
    this.type = type;
    this.status = status;
    this.escalationCondition = escalationCondition;
    this.value = value;
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
  @JoinColumn(name="DEADLINE_ID")
  public TaskDefDeadline getTaskDefDeadline() {
    return this.taskDefDeadline;
  }

  public void setTaskDefDeadline(TaskDefDeadline taskDefDeadline)
  {
    this.taskDefDeadline = taskDefDeadline;
  }

  @Column(name="TYPE", length=50)
  public String getType() {
    return this.type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  @Column(name="STATUS", length=50)
  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status)
  {
    this.status = status;
  }

  @Column(name="ESCALATION_CONDITION", length=200)
  public String getEscalationCondition() {
    return this.escalationCondition;
  }

  public void setEscalationCondition(String escalationCondition)
  {
    this.escalationCondition = escalationCondition;
  }

  @Column(name="VALUE", length=200)
  public String getValue() {
    return this.value;
  }

  public void setValue(String value)
  {
    this.value = value;
  }
}