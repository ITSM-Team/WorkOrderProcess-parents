package com.citsh.button.entity;

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
@Table(name="BUTTON_CATEGORY")
public class ButtonCategory
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String name;
  private Set<ButtonInfo> buttonInfo = new HashSet(0);

  public ButtonCategory() {
  }

  public ButtonCategory(Long id, String name, Set<ButtonInfo> buttonInfo) {
    this.id = id;
    this.name = name;
    this.buttonInfo = buttonInfo;
  }
  @Id
  @Column(name="ID", unique=true, nullable=false)
  public Long getId() { return this.id; }

  public void setId(Long id)
  {
    this.id = id;
  }
  @Column(name="NAME", length=64)
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }
  @OneToMany(fetch=FetchType.LAZY, mappedBy="buttonCategory")
  public Set<ButtonInfo> getButtonInfo() {
    return this.buttonInfo;
  }

  public void setButtonInfo(Set<ButtonInfo> buttonInfo) {
    this.buttonInfo = buttonInfo;
  }
}