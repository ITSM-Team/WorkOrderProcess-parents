package com.citsh.config.entity;

import com.citsh.notice.entity.BpmMailTemplate;
import com.citsh.process.entity.BpmProcess;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="BPM_TASK_DEF_NOTICE")
public class BpmTaskDefNotice
  implements Serializable
{
  private static final long serialVersionUID = 0L;
  private Long id;
  private BpmMailTemplate bpmMailTemplate;
  private BpmProcess bpmProcess;
  private String taskDefinitionKey;
  private Integer type;
  private String receiver;
  private String dueDate;

  public BpmTaskDefNotice()
  {
  }

  public BpmTaskDefNotice(Long id)
  {
    this.id = id;
  }

  public BpmTaskDefNotice(Long id, BpmMailTemplate bpmMailTemplate, BpmProcess bpmProcess, String taskDefinitionKey, Integer type, String receiver, String dueDate)
  {
    this.id = id;
    this.bpmMailTemplate = bpmMailTemplate;
    this.bpmProcess = bpmProcess;
    this.taskDefinitionKey = taskDefinitionKey;
    this.type = type;
    this.receiver = receiver;
    this.dueDate = dueDate;
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
  @JoinColumn(name="TEMPLATE_ID")
  public BpmMailTemplate getBpmMailTemplate() {
    return this.bpmMailTemplate;
  }

  public void setBpmMailTemplate(BpmMailTemplate bpmMailTemplate)
  {
    this.bpmMailTemplate = bpmMailTemplate;
  }
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="PROCESS_ID")
  public BpmProcess getBpmProcess() {
    return this.bpmProcess;
  }

  public void setBpmProcess(BpmProcess bpmProcess)
  {
    this.bpmProcess = bpmProcess;
  }

  @Column(name="TASK_DEFINITION_KEY", length=200)
  public String getTaskDefinitionKey() {
    return this.taskDefinitionKey;
  }

  public void setTaskDefinitionKey(String taskDefinitionKey)
  {
    this.taskDefinitionKey = taskDefinitionKey;
  }

  @Column(name="TYPE")
  public Integer getType() {
    return this.type;
  }

  public void setType(Integer type)
  {
    this.type = type;
  }

  @Column(name="RECEIVER", length=200)
  public String getReceiver() {
    return this.receiver;
  }

  public void setReceiver(String receiver)
  {
    this.receiver = receiver;
  }

  @Column(name="DUE_DATE", length=50)
  public String getDueDate() {
    return this.dueDate;
  }

  public void setDueDate(String dueDate)
  {
    this.dueDate = dueDate;
  }
}