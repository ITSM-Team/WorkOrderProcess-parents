package com.citsh.config.entity;

import com.citsh.process.entity.BpmProcess;
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
@Table(name="BPM_INSTANCE")
public class BpmInstance
  implements Serializable
{
  private static final long serialVersionUID = 0L;
  private Long id;
  private BpmProcess bpmProcess;
  private String name;
  private String businessKey;
  private String ref;
  private Date createTime;
  private String initiator;
  private Integer priority;
  private String tenantId;

  public BpmInstance()
  {
  }

  public BpmInstance(Long id)
  {
    this.id = id;
  }

  public BpmInstance(Long id, BpmProcess bpmProcess, String name, String businessKey, String ref, Date createTime, String initiator, Integer priority, String tenantId)
  {
    this.id = id;
    this.bpmProcess = bpmProcess;
    this.name = name;
    this.businessKey = businessKey;
    this.ref = ref;
    this.createTime = createTime;
    this.initiator = initiator;
    this.priority = priority;
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
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="PROCESS_ID")
  public BpmProcess getBpmProcess() {
    return this.bpmProcess;
  }

  public void setBpmProcess(BpmProcess bpmProcess)
  {
    this.bpmProcess = bpmProcess;
  }

  @Column(name="NAME", length=200)
  public String getName() {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  @Column(name="BUSINESS_KEY", length=64)
  public String getBusinessKey() {
    return this.businessKey;
  }

  public void setBusinessKey(String businessKey)
  {
    this.businessKey = businessKey;
  }

  @Column(name="REF", length=64)
  public String getRef() {
    return this.ref;
  }

  public void setRef(String ref)
  {
    this.ref = ref;
  }
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="CREATE_TIME", length=26)
  public Date getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }

  @Column(name="INITIATOR", length=64)
  public String getInitiator() {
    return this.initiator;
  }

  public void setInitiator(String initiator)
  {
    this.initiator = initiator;
  }

  @Column(name="PRIORITY")
  public Integer getPriority() {
    return this.priority;
  }

  public void setPriority(Integer priority)
  {
    this.priority = priority;
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