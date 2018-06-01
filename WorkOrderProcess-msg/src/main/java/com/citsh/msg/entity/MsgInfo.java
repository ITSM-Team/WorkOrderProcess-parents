package com.citsh.msg.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="MSG_INFO")
public class MsgInfo
  implements Serializable
{
  private static final long serialVersionUID = 0L;
  private Long id;
  private String name;
  private String content;
  private Integer type;
  private Date createTime;
  private String senderId;
  private String receiverId;
  private Integer status;
  private String tenantId;
  private String data;

  public MsgInfo()
  {
  }

  public MsgInfo(Long id)
  {
    this.id = id;
  }

  public MsgInfo(Long id, String name, String content, Integer type, Date createTime, String senderId, String receiverId, Integer status, String tenantId, String data)
  {
    this.id = id;
    this.name = name;
    this.content = content;
    this.type = type;
    this.createTime = createTime;
    this.senderId = senderId;
    this.receiverId = receiverId;
    this.status = status;
    this.tenantId = tenantId;
    this.data = data;
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

  @Column(name="CONTENT", length=65535)
  public String getContent() {
    return this.content;
  }

  public void setContent(String content)
  {
    this.content = content;
  }

  @Column(name="TYPE")
  public Integer getType() {
    return this.type;
  }

  public void setType(Integer type)
  {
    this.type = type;
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

  @Column(name="SENDER_ID", length=64)
  public String getSenderId() {
    return this.senderId;
  }

  public void setSenderId(String senderId)
  {
    this.senderId = senderId;
  }

  @Column(name="RECEIVER_ID", length=64)
  public String getReceiverId() {
    return this.receiverId;
  }

  public void setReceiverId(String receiverId)
  {
    this.receiverId = receiverId;
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

  @Column(name="DATA")
  public String getData() {
    return this.data;
  }

  public void setData(String data)
  {
    this.data = data;
  }
}