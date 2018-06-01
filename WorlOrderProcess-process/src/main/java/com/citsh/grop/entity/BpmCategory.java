package com.citsh.grop.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BPM_CATEGORY")
public class BpmCategory
  implements Serializable
{
  private static final long serialVersionUID = 0L;
  private Long id;
  private String name;
  private Integer priority;
  private String tenantId;

  public BpmCategory()
  {
  }

  public BpmCategory(Long id)
  {
    this.id = id;
  }

  public BpmCategory(Long id, String name, Integer priority, String tenantId)
  {
    this.id = id;
    this.name = name;
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

  @Column(name="NAME", length=200)
  public String getName() {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
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