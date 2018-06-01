package com.citsh.form.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;

public class FormTemplate
  implements Serializable
{
  private static final long serialVersionUID = 0L;

  @Id
  private ObjectId _id;

  @Indexed
  private Long id;
  private Integer type;
  private String name;
  private String content;
  private String code;
  private String tenantId;
  private String userId;
  private Date createTime;
  private Set<FormSchema> formSchemas = new HashSet(0);

  public ObjectId get_id()
  {
    return this._id;
  }

  public void set_id(ObjectId _id) {
    this._id = _id;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getType() {
    return this.type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getTenantId() {
    return this.tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public String getUserId() {
    return this.userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Date getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Set<FormSchema> getFormSchemas() {
    return this.formSchemas;
  }

  public void setFormSchemas(Set<FormSchema> formSchemas) {
    this.formSchemas = formSchemas;
  }
}