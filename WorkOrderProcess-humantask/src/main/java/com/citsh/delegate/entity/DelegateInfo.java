package com.citsh.delegate.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="DELEGATE_INFO")
public class DelegateInfo
  implements Serializable
{
  private static final long serialVersionUID = 0L;
  private Long id;
  private String assignee;
  private String attorney;
  private Date startTime;
  private Date endTime;
  private String processDefinitionId;
  private String taskDefinitionKey;
  private Integer status;
  private String tenantId;

  public DelegateInfo()
  {
  }

  public DelegateInfo(Long id)
  {
    this.id = id;
  }

  public DelegateInfo(Long id, String assignee, String attorney, Date startTime, Date endTime, String processDefinitionId, String taskDefinitionKey, Integer status, String tenantId)
  {
    this.id = id;
    this.assignee = assignee;
    this.attorney = attorney;
    this.startTime = startTime;
    this.endTime = endTime;
    this.processDefinitionId = processDefinitionId;
    this.taskDefinitionKey = taskDefinitionKey;
    this.status = status;
    this.tenantId = tenantId;
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

  @Column(name="ASSIGNEE", length=200)
  public String getAssignee() {
    return this.assignee;
  }

  public void setAssignee(String assignee)
  {
    this.assignee = assignee;
  }

  @Column(name="ATTORNEY", length=200)
  public String getAttorney() {
    return this.attorney;
  }

  public void setAttorney(String attorney)
  {
    this.attorney = attorney;
  }
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="START_TIME", length=26)
  public Date getStartTime() {
    return this.startTime;
  }

  public void setStartTime(Date startTime)
  {
    this.startTime = startTime;
  }
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="END_TIME", length=26)
  public Date getEndTime() {
    return this.endTime;
  }

  public void setEndTime(Date endTime)
  {
    this.endTime = endTime;
  }

  @Column(name="PROCESS_DEFINITION_ID", length=100)
  public String getProcessDefinitionId() {
    return this.processDefinitionId;
  }

  public void setProcessDefinitionId(String processDefinitionId)
  {
    this.processDefinitionId = processDefinitionId;
  }

  @Column(name="TASK_DEFINITION_KEY", length=100)
  public String getTaskDefinitionKey() {
    return this.taskDefinitionKey;
  }

  public void setTaskDefinitionKey(String taskDefinitionKey)
  {
    this.taskDefinitionKey = taskDefinitionKey;
  }

  @Column(name="STATUS")
  public Integer getStatus() {
    return this.status;
  }

  public void setStatus(Integer status)
  {
    this.status = status;
  }

  @Column(name="TENANT_ID", length=64)
  public String getTenantId() {
    return this.tenantId;
  }

  public void setTenantId(String tenantId)
  {
    this.tenantId = tenantId;
  }
}