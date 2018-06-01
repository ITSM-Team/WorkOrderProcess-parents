package com.citsh.keyvalue.entity;

import java.io.Serializable;
import javax.persistence.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;

public class Prop
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  private ObjectId _id;

  @Indexed
  private Long id;
  private String code;
  private int type;
  private String value;
  private long recordId;

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
  public String getCode() {
    return this.code;
  }
  public void setCode(String code) {
    this.code = code;
  }
  public int getType() {
    return this.type;
  }
  public void setType(int type) {
    this.type = type;
  }
  public String getValue() {
    return this.value;
  }
  public void setValue(String value) {
    this.value = value;
  }
  public long getRecordId() {
    return this.recordId;
  }
  public void setRecordId(long recordId) {
    this.recordId = recordId;
  }
}