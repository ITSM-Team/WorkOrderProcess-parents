package com.citsh.button.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="BUTTON_INFO")
public class ButtonInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String code;
  private String name;
  private ButtonCategory buttonCategory;

  public ButtonInfo()
  {
  }

  public ButtonInfo(Long id, String code, String name, ButtonCategory buttonCategory)
  {
    this.id = id;
    this.code = code;
    this.name = name;
    this.buttonCategory = buttonCategory;
  }
  @Id
  @Column(name="ID", unique=true, nullable=false)
  public Long getId() { return this.id; }

  public void setId(Long id)
  {
    this.id = id;
  }
  @Column(name="CODE", length=64)
  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }
  @Column(name="NAME", length=64)
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="CATEGORY_ID")
  public ButtonCategory getButtonCategory() { return this.buttonCategory; }

  public void setButtonCategory(ButtonCategory buttonCategory)
  {
    this.buttonCategory = buttonCategory;
  }
}