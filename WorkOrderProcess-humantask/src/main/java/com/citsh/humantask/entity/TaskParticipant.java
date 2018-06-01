package com.citsh.humantask.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TASK_PARTICIPANT")
public class TaskParticipant
  implements Serializable
{
  private static final long serialVersionUID = 0L;
  private Long id;
  private TaskInfo taskInfo;
  private String category;
  private String type;
  private String ref;

  public TaskParticipant()
  {
  }

  public TaskParticipant(Long id)
  {
    this.id = id;
  }

  public TaskParticipant(Long id, TaskInfo taskInfo, String category, String type, String ref)
  {
    this.id = id;
    this.taskInfo = taskInfo;
    this.category = category;
    this.type = type;
    this.ref = ref;
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

  @Column(name="CATEGORY", length=200)
  public String getCategory() {
    return this.category;
  }

  public void setCategory(String category)
  {
    this.category = category;
  }

  @Column(name="TYPE", length=200)
  public String getType() {
    return this.type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  @Column(name="REF", length=200)
  public String getRef() {
    return this.ref;
  }

  public void setRef(String ref)
  {
    this.ref = ref;
  }
}