package com.citsh.process.entity;

import com.citsh.config.entity.BpmConfBase;
import com.citsh.config.entity.BpmInstance;
import com.citsh.config.entity.BpmTaskDef;
import com.citsh.config.entity.BpmTaskDefNotice;
import com.citsh.grop.entity.BpmCategory;
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
@Table(name="BPM_PROCESS")
public class BpmProcess
  implements Serializable
{
  private static final long serialVersionUID = 0L;
  private Long id;
  private BpmConfBase bpmConfBase;
  private BpmCategory bpmCategory;
  private String name;
  private Integer priority;
  private String descn;
  private Integer useTaskConf;
  private String code;
  private String tenantId;
  private Integer isEnd;
  private Set<BpmTaskDef> bpmTaskDefs = new HashSet(0);

  private Set<BpmTaskDefNotice> bpmTaskDefNotices = new HashSet(0);

  private Set<BpmInstance> bpmInstances = new HashSet(0);

  public BpmProcess() {
  }

  public BpmProcess(Long id) {
    this.id = id;
  }

  public BpmProcess(Long id, BpmConfBase bpmConfBase, BpmCategory bpmCategory, String name, Integer priority, String descn, Integer useTaskConf, String code, String tenantId, Set<BpmTaskDef> bpmTaskDefs, Set<BpmTaskDefNotice> bpmTaskDefNotices, Set<BpmInstance> bpmInstances)
  {
    this.id = id;
    this.bpmConfBase = bpmConfBase;
    this.bpmCategory = bpmCategory;
    this.name = name;
    this.priority = priority;
    this.descn = descn;
    this.useTaskConf = useTaskConf;
    this.code = code;
    this.tenantId = tenantId;
    this.bpmTaskDefs = bpmTaskDefs;
    this.bpmTaskDefNotices = bpmTaskDefNotices;
    this.bpmInstances = bpmInstances;
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
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="CATEGORY_ID")
  public BpmCategory getBpmCategory() {
    return this.bpmCategory;
  }

  public void setBpmCategory(BpmCategory bpmCategory)
  {
    this.bpmCategory = bpmCategory;
  }

  @Column(name="NAME", length=200)
  public String getName() {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  @Column(name="PRIORITY")
  public Integer getPriority() {
    return this.priority;
  }

  public void setPriority(Integer priority)
  {
    this.priority = priority;
  }

  @Column(name="DESCN", length=200)
  public String getDescn() {
    return this.descn;
  }

  public void setDescn(String descn)
  {
    this.descn = descn;
  }

  @Column(name="USE_TASK_CONF")
  public Integer getUseTaskConf() {
    return this.useTaskConf;
  }

  public void setUseTaskConf(Integer useTaskConf)
  {
    this.useTaskConf = useTaskConf;
  }

  @Column(name="CODE", length=64)
  public String getCode() {
    return this.code;
  }

  public void setCode(String code)
  {
    this.code = code;
  }

  @Column(name="TENANT_ID", length=64)
  public String getTenantId() {
    return this.tenantId;
  }

  public void setTenantId(String tenantId)
  {
    this.tenantId = tenantId;
  }

  @OneToMany(fetch=FetchType.LAZY, mappedBy="bpmProcess")
  public Set<BpmTaskDef> getBpmTaskDefs() {
    return this.bpmTaskDefs;
  }

  public void setBpmTaskDefs(Set<BpmTaskDef> bpmTaskDefs)
  {
    this.bpmTaskDefs = bpmTaskDefs;
  }

  @OneToMany(fetch=FetchType.LAZY, mappedBy="bpmProcess")
  public Set<BpmTaskDefNotice> getBpmTaskDefNotices() {
    return this.bpmTaskDefNotices;
  }

  public void setBpmTaskDefNotices(Set<BpmTaskDefNotice> bpmTaskDefNotices)
  {
    this.bpmTaskDefNotices = bpmTaskDefNotices;
  }

  @OneToMany(fetch=FetchType.LAZY, mappedBy="bpmProcess")
  public Set<BpmInstance> getBpmInstances() {
    return this.bpmInstances;
  }

  public void setBpmInstances(Set<BpmInstance> bpmInstances)
  {
    this.bpmInstances = bpmInstances;
  }
  @Column(name="ISEND")
  public Integer getIsEnd() {
    return this.isEnd;
  }

  public void setIsEnd(Integer isEnd) {
    this.isEnd = isEnd;
  }
}