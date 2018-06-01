package com.citsh.keyvalue.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.persistence.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;

public class Record
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  private ObjectId _id;

  @Indexed
  private Long id;
  private String businessKey;
  private String name;
  private String formTemplateCode;
  private String code;
  private String category;
  private int status;
  private String ref;
  private Date createTime;
  private String userId;
  private String tenantId;
  private Map<String, Prop> props = new LinkedHashMap();

  public ObjectId get_id() { return this._id; }

  public void set_id(ObjectId _id) {
    this._id = _id;
  }
  public Long getId() {
    return this.id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getBusinessKey() {
    return this.businessKey;
  }
  public void setBusinessKey(String businessKey) {
    this.businessKey = businessKey;
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getFormTemplateCode() {
    return this.formTemplateCode;
  }
  public void setFormTemplateCode(String formTemplateCode) {
    this.formTemplateCode = formTemplateCode;
  }
  public String getCode() {
    return this.code;
  }
  public void setCode(String code) {
    this.code = code;
  }
  public String getCategory() {
    return this.category;
  }
  public void setCategory(String category) {
    this.category = category;
  }
  public int getStatus() {
    return this.status;
  }
  public void setStatus(int status) {
    this.status = status;
  }
  public String getRef() {
    return this.ref;
  }
  public void setRef(String ref) {
    this.ref = ref;
  }
  public Date getCreateTime() {
    return this.createTime;
  }
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
  public String getUserId() {
    return this.userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  public String getTenantId() {
    return this.tenantId;
  }
  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }
  public Map<String, Prop> getProps() {
    return this.props;
  }
  public void setProps(Map<String, Prop> props) {
    this.props = props;
  }
}