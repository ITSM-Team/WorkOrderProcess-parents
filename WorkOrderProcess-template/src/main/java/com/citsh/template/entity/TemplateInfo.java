package com.citsh.template.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TEMPLATE_INFO")
public class TemplateInfo
  implements Serializable
{
  private static final long serialVersionUID = 0L;
  private Long id;
  private String name;
  private String code;
  private String tenantId;
  private Set<TemplateField> templateFields = new HashSet(0);

  public TemplateInfo() {
  }

  public TemplateInfo(Long id) {
    this.id = id;
  }

  public TemplateInfo(Long id, String name, String code, String tenantId, Set<TemplateField> templateFields)
  {
    this.id = id;
    this.name = name;
    this.code = code;
    this.tenantId = tenantId;
    this.templateFields = templateFields;
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

  @Column(name="NAME", length=50)
  public String getName() {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  @Column(name="CODE", length=50)
  public String getCode() {
    return this.code;
  }

  public void setCode(String code)
  {
    this.code = code;
  }

  @Column(name="TENANT_ID", length=64)
  public String getTenantId() {
    return this.tenantId;
  }

  public void setTenantId(String tenantId)
  {
    this.tenantId = tenantId;
  }

  @OneToMany(fetch=FetchType.LAZY, mappedBy="templateInfo")
  public Set<TemplateField> getTemplateFields() {
    return this.templateFields;
  }

  public void setTemplateFields(Set<TemplateField> templateFields)
  {
    this.templateFields = templateFields;
  }
}