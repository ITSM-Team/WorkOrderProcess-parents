package com.citsh.notice.entity;

import com.citsh.config.entity.BpmTaskDefNotice;
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
@Table(name="BPM_MAIL_TEMPLATE")
public class BpmMailTemplate
  implements Serializable
{
  private static final long serialVersionUID = 0L;
  private Long id;
  private String name;
  private String subject;
  private String content;
  private Set<BpmTaskDefNotice> bpmTaskDefNotices = new HashSet(0);

  private Set<BpmConfNotice> bpmConfNotices = new HashSet(0);

  public BpmMailTemplate() {
  }

  public BpmMailTemplate(Long id) {
    this.id = id;
  }

  public BpmMailTemplate(Long id, String name, String subject, String content, Set<BpmTaskDefNotice> bpmTaskDefNotices, Set<BpmConfNotice> bpmConfNotices)
  {
    this.id = id;
    this.name = name;
    this.subject = subject;
    this.content = content;
    this.bpmTaskDefNotices = bpmTaskDefNotices;
    this.bpmConfNotices = bpmConfNotices;
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

  @Column(name="SUBJECT", length=100)
  public String getSubject() {
    return this.subject;
  }

  public void setSubject(String subject)
  {
    this.subject = subject;
  }

  @Column(name="CONTENT", length=200)
  public String getContent() {
    return this.content;
  }

  public void setContent(String content)
  {
    this.content = content;
  }

  @OneToMany(fetch=FetchType.LAZY, mappedBy="bpmMailTemplate")
  public Set<BpmTaskDefNotice> getBpmTaskDefNotices() {
    return this.bpmTaskDefNotices;
  }

  public void setBpmTaskDefNotices(Set<BpmTaskDefNotice> bpmTaskDefNotices)
  {
    this.bpmTaskDefNotices = bpmTaskDefNotices;
  }

  @OneToMany(fetch=FetchType.LAZY, mappedBy="bpmMailTemplate")
  public Set<BpmConfNotice> getBpmConfNotices() {
    return this.bpmConfNotices;
  }

  public void setBpmConfNotices(Set<BpmConfNotice> bpmConfNotices)
  {
    this.bpmConfNotices = bpmConfNotices;
  }
}