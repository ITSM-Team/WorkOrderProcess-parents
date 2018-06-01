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
@Table(name="TASK_DEF_OPERATION")
public class TaskDefOperation
  implements Serializable
{
  private static final long serialVersionUID = 0L;
  private Long id;
  private TaskDefBase taskDefBase;
  private String value;
  private String type;
  private String status;
  private Integer priority;

  public TaskDefOperation()
  {
  }

  public TaskDefOperation(Long id)
  {
    this.id = id;
  }

  public TaskDefOperation(Long id, TaskDefBase taskDefBase, String value, String type, String status, Integer priority)
  {
    this.id = id;
    this.taskDefBase = taskDefBase;
    this.value = value;
    this.type = type;
    this.status = status;
    this.priority = priority;
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

  @Column(name="VALUE", length=200)
  public String getValue() {
    return this.value;
  }

  public void setValue(String value)
  {
    this.value = value;
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

  @Column(name="PRIORITY")
  public Integer getPriority() {
    return this.priority;
  }

  public void setPriority(Integer priority)
  {
    this.priority = priority;
  }
}