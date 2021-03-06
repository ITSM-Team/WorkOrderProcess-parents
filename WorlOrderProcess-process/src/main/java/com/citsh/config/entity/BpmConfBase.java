package com.citsh.config.entity;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.citsh.process.entity.BpmProcess;

/**
 * BpmConfBase 流程配置.
 * 
 */
@Entity
@Table(name = "BPM_CONF_BASE")
public class BpmConfBase implements java.io.Serializable {
    private static final long serialVersionUID = 0L;

    /** 主键. */
    private Long id;

    /** 流程定义ID. */
    private String processDefinitionId;

    /** 流程定义KEY. */
    private String processDefinitionKey;

    /** 流程定义版本. */
    private Integer processDefinitionVersion;

    /** . */
    private Set<BpmConfNode> bpmConfNodes = new HashSet<BpmConfNode>(0);

    /** . */
    private Set<BpmProcess> bpmProcesses = new HashSet<BpmProcess>(0);

    public BpmConfBase() {
    }

    public BpmConfBase(Long id) {
        this.id = id;
    }

    public BpmConfBase(Long id, String processDefinitionId,
            String processDefinitionKey, Integer processDefinitionVersion,
            Set<BpmConfNode> bpmConfNodes, Set<BpmProcess> bpmProcesses) {
        this.id = id;
        this.processDefinitionId = processDefinitionId;
        this.processDefinitionKey = processDefinitionKey;
        this.processDefinitionVersion = processDefinitionVersion;
        this.bpmConfNodes = bpmConfNodes;
        this.bpmProcesses = bpmProcesses;
    }

    /** @return 主键. */
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    /**
     * @param id
     *            主键.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return 流程定义ID. */
    @Column(name = "PROCESS_DEFINITION_ID", length = 200)
    public String getProcessDefinitionId() {
        return this.processDefinitionId;
    }

    /**
     * @param processDefinitionId
     *            流程定义ID.
     */
    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    /** @return 流程定义KEY. */
    @Column(name = "PROCESS_DEFINITION_KEY", length = 200)
    public String getProcessDefinitionKey() {
        return this.processDefinitionKey;
    }

    /**
     * @param processDefinitionKey
     *            流程定义KEY.
     */
    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    /** @return 流程定义版本. */
    @Column(name = "PROCESS_DEFINITION_VERSION")
    public Integer getProcessDefinitionVersion() {
        return this.processDefinitionVersion;
    }

    /**
     * @param processDefinitionVersion
     *            流程定义版本.
     */
    public void setProcessDefinitionVersion(Integer processDefinitionVersion) {
        this.processDefinitionVersion = processDefinitionVersion;
    }

    /** @return . */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bpmConfBase")
    public Set<BpmConfNode> getBpmConfNodes() {
        return this.bpmConfNodes;
    }

    /**
     * @param bpmConfNodes
     *            .
     */
    public void setBpmConfNodes(Set<BpmConfNode> bpmConfNodes) {
        this.bpmConfNodes = bpmConfNodes;
    }

    /** @return . */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bpmConfBase")
    public Set<BpmProcess> getBpmProcesses() {
        return this.bpmProcesses;
    }

    /**
     * @param bpmProcesses
     *            .
     */
    public void setBpmProcesses(Set<BpmProcess> bpmProcesses) {
        this.bpmProcesses = bpmProcesses;
    }
}