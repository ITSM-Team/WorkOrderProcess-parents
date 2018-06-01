package com.citsh.form.entity;

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
@Table(name="BPM_CONF_FORM")
public class BpmConfForm
  implements Serializable
{
  private static final long serialVersionUID = 0L;
  private Long id;
  private BpmConfNode bpmConfNode;
  private String value;
  private Integer type;
  private String originValue;
  private Integer originType;
  private Integer status;

  public BpmConfForm()
  {
  }

  public BpmConfForm(Long id)
  {
    this.id = id;
  }

  public BpmConfForm(Long id, BpmConfNode bpmConfNode, String value, Integer type, String originValue, Integer originType, Integer status)
  {
    this.id = id;
    this.bpmConfNode = bpmConfNode;
    this.value = value;
    this.type = type;
    this.originValue = originValue;
    this.originType = originType;
    this.status = status;
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
  @JoinColumn(name="NODE_ID")
  public BpmConfNode getBpmConfNode() {
    return this.bpmConfNode;
  }

  public void setBpmConfNode(BpmConfNode bpmConfNode)
  {
    this.bpmConfNode = bpmConfNode;
  }

  @Column(name="VALUE", length=200)
  public String getValue() {
    return this.value;
  }

  public void setValue(String value)
  {
    this.value = value;
  }

  @Column(name="TYPE")
  public Integer getType() {
    return this.type;
  }

  public void setType(Integer type)
  {
    this.type = type;
  }

  @Column(name="ORIGIN_VALUE", length=200)
  public String getOriginValue() {
    return this.originValue;
  }

  public void setOriginValue(String originValue)
  {
    this.originValue = originValue;
  }

  @Column(name="ORIGIN_TYPE")
  public Integer getOriginType() {
    return this.originType;
  }

  public void setOriginType(Integer originType)
  {
    this.originType = originType;
  }

  @Column(name="STATUS")
  public Integer getStatus() {
    return this.status;
  }

  public void setStatus(Integer status)
  {
    this.status = status;
  }
}