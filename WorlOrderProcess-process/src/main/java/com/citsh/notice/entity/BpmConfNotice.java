package com.citsh.notice.entity;

import com.citsh.config.entity.BpmConfNode;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="BPM_CONF_NOTICE")
public class BpmConfNotice
  implements Serializable
{
  private static final long serialVersionUID = 0L;
  private Long id;
  private BpmMailTemplate bpmMailTemplate;
  private BpmConfNode bpmConfNode;
  private Integer type;
  private String receiver;
  private String dueDate;
  private String templateCode;
  private String notificationType;

  public BpmConfNotice()
  {
  }

  public BpmConfNotice(Long id)
  {
    this.id = id;
  }

  public BpmConfNotice(Long id, BpmMailTemplate bpmMailTemplate, BpmConfNode bpmConfNode, Integer type, String receiver, String dueDate, String templateCode, String notificationType)
  {
    this.id = id;
    this.bpmMailTemplate = bpmMailTemplate;
    this.bpmConfNode = bpmConfNode;
    this.type = type;
    this.receiver = receiver;
    this.dueDate = dueDate;
    this.templateCode = templateCode;
    this.notificationType = notificationType;
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
  @JoinColumn(name="NODE_ID")
  public BpmConfNode getBpmConfNode() {
    return this.bpmConfNode;
  }

  public void setBpmConfNode(BpmConfNode bpmConfNode)
  {
    this.bpmConfNode = bpmConfNode;
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

  @Column(name="TEMPLATE_CODE", length=200)
  public String getTemplateCode() {
    return this.templateCode;
  }

  public void setTemplateCode(String templateCode)
  {
    this.templateCode = templateCode;
  }

  @Column(name="NOTIFICATION_TYPE", length=200)
  public String getNotificationType() {
    return this.notificationType;
  }

  public void setNotificationType(String notificationType)
  {
    this.notificationType = notificationType;
  }
}