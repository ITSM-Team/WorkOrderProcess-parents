package com.citsh.template.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TEMPLATE_FIELD")
public class TemplateField
  implements Serializable
{
  private static final long serialVersionUID = 0L;
  private Long id;
  private TemplateInfo templateInfo;
  private String name;
  private String type;
  private String content;
  private String tenantId;

  public TemplateField()
  {
  }

  public TemplateField(Long id)
  {
    this.id = id;
  }

  public TemplateField(Long id, TemplateInfo templateInfo, String name, String type, String content, String tenantId)
  {
    this.id = id;
    this.templateInfo = templateInfo;
    this.name = name;
    this.type = type;
    this.content = content;
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
  @JoinColumn(name="INFO_ID")
  public TemplateInfo getTemplateInfo() {
    return this.templateInfo;
  }

  public void setTemplateInfo(TemplateInfo templateInfo)
  {
    this.templateInfo = templateInfo;
  }

  @Column(name="NAME", length=50)
  public String getName() {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  @Column(name="TYPE", length=50)
  public String getType() {
    return this.type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  @Column(name="CONTENT", length=65535)
  public String getContent() {
    return this.content;
  }

  public void setContent(String content)
  {
    this.content = content;
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