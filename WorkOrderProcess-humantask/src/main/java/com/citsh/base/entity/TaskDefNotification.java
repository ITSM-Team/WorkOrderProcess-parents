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
@Table(name="TASK_DEF_NOTIFICATION")
public class TaskDefNotification
  implements Serializable
{
  private static final long serialVersionUID = 0L;
  private Long id;
  private TaskDefBase taskDefBase;
  private String eventName;
  private String receiver;
  private String type;
  private String templateCode;

  public TaskDefNotification()
  {
  }

  public TaskDefNotification(Long id)
  {
    this.id = id;
  }

  public TaskDefNotification(Long id, TaskDefBase taskDefBase, String eventName, String receiver, String type, String templateCode)
  {
    this.id = id;
    this.taskDefBase = taskDefBase;
    this.eventName = eventName;
    this.receiver = receiver;
    this.type = type;
    this.templateCode = templateCode;
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

  @Column(name="EVENT_NAME", length=100)
  public String getEventName() {
    return this.eventName;
  }

  public void setEventName(String eventName)
  {
    this.eventName = eventName;
  }

  @Column(name="RECEIVER", length=200)
  public String getReceiver() {
    return this.receiver;
  }

  public void setReceiver(String receiver)
  {
    this.receiver = receiver;
  }

  @Column(name="TYPE", length=50)
  public String getType() {
    return this.type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  @Column(name="TEMPLATE_CODE", length=200)
  public String getTemplateCode() {
    return this.templateCode;
  }

  public void setTemplateCode(String templateCode)
  {
    this.templateCode = templateCode;
  }
}