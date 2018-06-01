package com.citsh.config.entity;

import com.citsh.assign.entity.BpmConfAssign;
import com.citsh.form.entity.BpmConfForm;
import com.citsh.listener.entity.BpmConfListener;
import com.citsh.notice.entity.BpmConfNotice;
import com.citsh.operation.entity.BpmConfOperation;
import com.citsh.rule.entity.BpmConfRule;
import com.citsh.user.entity.BpmConfUser;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="BPM_CONF_NODE")
public class BpmConfNode
  implements Serializable
{
  private static final long serialVersionUID = 0L;
  private Long id;
  private BpmConfBase bpmConfBase;
  private String code;
  private String name;
  private String type;
  private Integer confUser;
  private Integer confListener;
  private Integer confRule;
  private Integer confForm;
  private Integer confOperation;
  private Integer confNotice;
  private Integer priority;
  private Set<BpmConfListener> bpmConfListeners = new HashSet(0);

  private Set<BpmConfNotice> bpmConfNotices = new HashSet(0);

  private Set<BpmConfUser> bpmConfUsers = new HashSet(0);

  private Set<BpmConfAssign> bpmConfAssigns = new HashSet(0);

  private Set<BpmConfCountersign> bpmConfCountersigns = new HashSet(0);

  private Set<BpmConfForm> bpmConfForms = new HashSet(0);

  private Set<BpmConfRule> bpmConfRules = new HashSet(0);

  private Set<BpmConfOperation> bpmConfOperations = new HashSet(0);

  public BpmConfNode()
  {
  }

  public BpmConfNode(Long id) {
    this.id = id;
  }

  public BpmConfNode(Long id, BpmConfBase bpmConfBase, String code, String name, String type, Integer confUser, Integer confListener, Integer confRule, Integer confForm, Integer confOperation, Integer confNotice, Integer priority, Set<BpmConfListener> bpmConfListeners, Set<BpmConfNotice> bpmConfNotices, Set<BpmConfUser> bpmConfUsers, Set<BpmConfAssign> bpmConfAssigns, Set<BpmConfCountersign> bpmConfCountersigns, Set<BpmConfForm> bpmConfForms, Set<BpmConfRule> bpmConfRules, Set<BpmConfOperation> bpmConfOperations)
  {
    this.id = id;
    this.bpmConfBase = bpmConfBase;
    this.code = code;
    this.name = name;
    this.type = type;
    this.confUser = confUser;
    this.confListener = confListener;
    this.confRule = confRule;
    this.confForm = confForm;
    this.confOperation = confOperation;
    this.confNotice = confNotice;
    this.priority = priority;
    this.bpmConfListeners = bpmConfListeners;
    this.bpmConfNotices = bpmConfNotices;
    this.bpmConfUsers = bpmConfUsers;
    this.bpmConfAssigns = bpmConfAssigns;
    this.bpmConfCountersigns = bpmConfCountersigns;
    this.bpmConfForms = bpmConfForms;
    this.bpmConfRules = bpmConfRules;
    this.bpmConfOperations = bpmConfOperations;
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
  @JoinColumn(name="CONF_BASE_ID")
  public BpmConfBase getBpmConfBase() {
    return this.bpmConfBase;
  }

  public void setBpmConfBase(BpmConfBase bpmConfBase)
  {
    this.bpmConfBase = bpmConfBase;
  }

  @Column(name="CODE", length=200)
  public String getCode() {
    return this.code;
  }

  public void setCode(String code)
  {
    this.code = code;
  }

  @Column(name="NAME", length=200)
  public String getName() {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  @Column(name="TYPE", length=200)
  public String getType() {
    return this.type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  @Column(name="CONF_USER")
  public Integer getConfUser() {
    return this.confUser;
  }

  public void setConfUser(Integer confUser)
  {
    this.confUser = confUser;
  }

  @Column(name="CONF_LISTENER")
  public Integer getConfListener() {
    return this.confListener;
  }

  public void setConfListener(Integer confListener)
  {
    this.confListener = confListener;
  }

  @Column(name="CONF_RULE")
  public Integer getConfRule() {
    return this.confRule;
  }

  public void setConfRule(Integer confRule)
  {
    this.confRule = confRule;
  }

  @Column(name="CONF_FORM")
  public Integer getConfForm() {
    return this.confForm;
  }

  public void setConfForm(Integer confForm)
  {
    this.confForm = confForm;
  }

  @Column(name="CONF_OPERATION")
  public Integer getConfOperation() {
    return this.confOperation;
  }

  public void setConfOperation(Integer confOperation)
  {
    this.confOperation = confOperation;
  }

  @Column(name="CONF_NOTICE")
  public Integer getConfNotice() {
    return this.confNotice;
  }

  public void setConfNotice(Integer confNotice)
  {
    this.confNotice = confNotice;
  }

  @Column(name="PRIORITY")
  public Integer getPriority() {
    return this.priority;
  }

  public void setPriority(Integer priority)
  {
    this.priority = priority;
  }

  @OneToMany(fetch=FetchType.LAZY, mappedBy="bpmConfNode")
  public Set<BpmConfListener> getBpmConfListeners() {
    return this.bpmConfListeners;
  }

  public void setBpmConfListeners(Set<BpmConfListener> bpmConfListeners)
  {
    this.bpmConfListeners = bpmConfListeners;
  }

  @OneToMany(fetch=FetchType.LAZY, mappedBy="bpmConfNode")
  public Set<BpmConfNotice> getBpmConfNotices() {
    return this.bpmConfNotices;
  }

  public void setBpmConfNotices(Set<BpmConfNotice> bpmConfNotices)
  {
    this.bpmConfNotices = bpmConfNotices;
  }

  @OneToMany(fetch=FetchType.LAZY, mappedBy="bpmConfNode")
  public Set<BpmConfUser> getBpmConfUsers() {
    return this.bpmConfUsers;
  }

  public void setBpmConfUsers(Set<BpmConfUser> bpmConfUsers)
  {
    this.bpmConfUsers = bpmConfUsers;
  }

  @OneToMany(fetch=FetchType.LAZY, mappedBy="bpmConfNode")
  public Set<BpmConfAssign> getBpmConfAssigns() {
    return this.bpmConfAssigns;
  }

  public void setBpmConfAssigns(Set<BpmConfAssign> bpmConfAssigns)
  {
    this.bpmConfAssigns = bpmConfAssigns;
  }

  @OneToMany(fetch=FetchType.LAZY, mappedBy="bpmConfNode")
  public Set<BpmConfCountersign> getBpmConfCountersigns() {
    return this.bpmConfCountersigns;
  }

  public void setBpmConfCountersigns(Set<BpmConfCountersign> bpmConfCountersigns)
  {
    this.bpmConfCountersigns = bpmConfCountersigns;
  }

  @OneToMany(fetch=FetchType.LAZY, mappedBy="bpmConfNode")
  public Set<BpmConfForm> getBpmConfForms() {
    return this.bpmConfForms;
  }

  public void setBpmConfForms(Set<BpmConfForm> bpmConfForms)
  {
    this.bpmConfForms = bpmConfForms;
  }

  @OneToMany(fetch=FetchType.LAZY, mappedBy="bpmConfNode")
  public Set<BpmConfRule> getBpmConfRules() {
    return this.bpmConfRules;
  }

  public void setBpmConfRules(Set<BpmConfRule> bpmConfRules)
  {
    this.bpmConfRules = bpmConfRules;
  }

  @OneToMany(fetch=FetchType.LAZY, mappedBy="bpmConfNode")
  public Set<BpmConfOperation> getBpmConfOperations() {
    return this.bpmConfOperations;
  }

  public void setBpmConfOperations(Set<BpmConfOperation> bpmConfOperations)
  {
    this.bpmConfOperations = bpmConfOperations;
  }
}