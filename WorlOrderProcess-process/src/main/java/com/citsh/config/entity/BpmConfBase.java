package com.citsh.config.entity;

import com.citsh.process.entity.BpmProcess;
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
@Table(name="BPM_CONF_BASE")
public class BpmConfBase
  implements Serializable
{
  private static final long serialVersionUID = 0L;
  private Long id;
  private String processDefinitionId;
  private String processDefinitionKey;
  private Integer processDefinitionVersion;
  private Set<BpmConfNode> bpmConfNodes = new HashSet(0);

  private Set<BpmProcess> bpmProcesses = new HashSet(0);

  public BpmConfBase() {
  }

  public BpmConfBase(Long id) {
    this.id = id;
  }

  public BpmConfBase(Long id, String processDefinitionId, String processDefinitionKey, Integer processDefinitionVersion, Set<BpmConfNode> bpmConfNodes, Set<BpmProcess> bpmProcesses)
  {
    this.id = id;
    this.processDefinitionId = processDefinitionId;
    this.processDefinitionKey = processDefinitionKey;
    this.processDefinitionVersion = processDefinitionVersion;
    this.bpmConfNodes = bpmConfNodes;
    this.bpmProcesses = bpmProcesses;
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

  @Column(name="PROCESS_DEFINITION_ID", length=200)
  public String getProcessDefinitionId() {
    return this.processDefinitionId;
  }

  public void setProcessDefinitionId(String processDefinitionId)
  {
    this.processDefinitionId = processDefinitionId;
  }

  @Column(name="PROCESS_DEFINITION_KEY", length=200)
  public String getProcessDefinitionKey() {
    return this.processDefinitionKey;
  }

  public void setProcessDefinitionKey(String processDefinitionKey)
  {
    this.processDefinitionKey = processDefinitionKey;
  }

  @Column(name="PROCESS_DEFINITION_VERSION")
  public Integer getProcessDefinitionVersion() {
    return this.processDefinitionVersion;
  }

  public void setProcessDefinitionVersion(Integer processDefinitionVersion)
  {
    this.processDefinitionVersion = processDefinitionVersion;
  }

  @OneToMany(fetch=FetchType.LAZY, mappedBy="bpmConfBase")
  public Set<BpmConfNode> getBpmConfNodes() {
    return this.bpmConfNodes;
  }

  public void setBpmConfNodes(Set<BpmConfNode> bpmConfNodes)
  {
    this.bpmConfNodes = bpmConfNodes;
  }

  @OneToMany(fetch=FetchType.LAZY, mappedBy="bpmConfBase")
  public Set<BpmProcess> getBpmProcesses() {
    return this.bpmProcesses;
  }

  public void setBpmProcesses(Set<BpmProcess> bpmProcesses)
  {
    this.bpmProcesses = bpmProcesses;
  }
}