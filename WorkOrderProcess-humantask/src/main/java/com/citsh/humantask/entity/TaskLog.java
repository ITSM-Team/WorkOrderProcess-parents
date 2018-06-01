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
@Table(name="TASK_LOG")
public class TaskLog
  implements Serializable
{
  private static final long serialVersionUID = 0L;
  private Long id;
  private TaskInfo taskInfo;
  private String eventType;
  private Date eventTime;
  private String creator;
  private String startOwner;
  private String endOwner;
  private String taskStatus;
  private String content;
  private Integer priority;

  public TaskLog()
  {
  }

  public TaskLog(Long id)
  {
    this.id = id;
  }

  public TaskLog(Long id, TaskInfo taskInfo, String eventType, Date eventTime, String creator, String startOwner, String endOwner, String taskStatus, String content, Integer priority)
  {
    this.id = id;
    this.taskInfo = taskInfo;
    this.eventType = eventType;
    this.eventTime = eventTime;
    this.creator = creator;
    this.startOwner = startOwner;
    this.endOwner = endOwner;
    this.taskStatus = taskStatus;
    this.content = content;
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
  @JoinColumn(name="TASK_ID")
  public TaskInfo getTaskInfo() {
    return this.taskInfo;
  }

  public void setTaskInfo(TaskInfo taskInfo)
  {
    this.taskInfo = taskInfo;
  }

  @Column(name="EVENT_TYPE", length=100)
  public String getEventType() {
    return this.eventType;
  }

  public void setEventType(String eventType)
  {
    this.eventType = eventType;
  }
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="EVENT_TIME", length=26)
  public Date getEventTime() {
    return this.eventTime;
  }

  public void setEventTime(Date eventTime)
  {
    this.eventTime = eventTime;
  }

  @Column(name="CREATOR", length=64)
  public String getCreator() {
    return this.creator;
  }

  public void setCreator(String creator)
  {
    this.creator = creator;
  }

  @Column(name="START_OWNER", length=64)
  public String getStartOwner() {
    return this.startOwner;
  }

  public void setStartOwner(String startOwner)
  {
    this.startOwner = startOwner;
  }

  @Column(name="END_OWNER", length=64)
  public String getEndOwner() {
    return this.endOwner;
  }

  public void setEndOwner(String endOwner)
  {
    this.endOwner = endOwner;
  }

  @Column(name="TASK_STATUS", length=50)
  public String getTaskStatus() {
    return this.taskStatus;
  }

  public void setTaskStatus(String taskStatus)
  {
    this.taskStatus = taskStatus;
  }

  @Column(name="CONTENT", length=65535)
  public String getContent() {
    return this.content;
  }

  public void setContent(String content)
  {
    this.content = content;
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