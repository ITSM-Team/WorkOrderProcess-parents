package com.citsh.process.entity;


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

import com.citsh.config.entity.BpmConfBase;
import com.citsh.config.entity.BpmInstance;
import com.citsh.config.entity.BpmTaskDef;
import com.citsh.config.entity.BpmTaskDefNotice;
import com.citsh.grop.entity.BpmCategory;

/**
 * BpmProcess 流程定义.
 * 
 */
@Entity
@Table(name = "BPM_PROCESS")
public class BpmProcess implements java.io.Serializable {
    private static final long serialVersionUID = 0L;

    /** 主键. */
    private Long id;

    /** 外键，流程配置. */
    private BpmConfBase bpmConfBase;

    /** 外键，流程分类. */
    private BpmCategory bpmCategory;

    /** 名称. */
    private String name;

    /** 排序. */
    private Integer priority;

    /** 备注. */
    private String descn;

    /** 是否配置任务. */
    private Integer useTaskConf;

    /** 编码. */
    private String code;

    /** 租户. */
    private String tenantId;

    /** . */
    private Set<BpmTaskDef> bpmTaskDefs = new HashSet<BpmTaskDef>(0);

    /** . */
    private Set<BpmTaskDefNotice> bpmTaskDefNotices = new HashSet<BpmTaskDefNotice>(
            0);

    /** . */
    private Set<BpmInstance> bpmInstances = new HashSet<BpmInstance>(0);

    public BpmProcess() {
    }

    public BpmProcess(Long id) {
        this.id = id;
    }

    public BpmProcess(Long id, BpmConfBase bpmConfBase,
            BpmCategory bpmCategory, String name, Integer priority,
            String descn, Integer useTaskConf, String code, String tenantId,
            Set<BpmTaskDef> bpmTaskDefs,
            Set<BpmTaskDefNotice> bpmTaskDefNotices,
            Set<BpmInstance> bpmInstances) {
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

    /** @return 外键，流程配置. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONF_BASE_ID")
    public BpmConfBase getBpmConfBase() {
        return this.bpmConfBase;
    }

    /**
     * @param bpmConfBase
     *            外键，流程配置.
     */
    public void setBpmConfBase(BpmConfBase bpmConfBase) {
        this.bpmConfBase = bpmConfBase;
    }

    /** @return 外键，流程分类. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    public BpmCategory getBpmCategory() {
        return this.bpmCategory;
    }

    /**
     * @param bpmCategory
     *            外键，流程分类.
     */
    public void setBpmCategory(BpmCategory bpmCategory) {
        this.bpmCategory = bpmCategory;
    }

    /** @return 名称. */
    @Column(name = "NAME", length = 200)
    public String getName() {
        return this.name;
    }

    /**
     * @param name
     *            名称.
     */
    public void setName(String name) {
        this.name = name;
    }

    /** @return 排序. */
    @Column(name = "PRIORITY")
    public Integer getPriority() {
        return this.priority;
    }

    /**
     * @param priority
     *            排序.
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /** @return 备注. */
    @Column(name = "DESCN", length = 200)
    public String getDescn() {
        return this.descn;
    }

    /**
     * @param descn
     *            备注.
     */
    public void setDescn(String descn) {
        this.descn = descn;
    }

    /** @return 是否配置任务. */
    @Column(name = "USE_TASK_CONF")
    public Integer getUseTaskConf() {
        return this.useTaskConf;
    }

    /**
     * @param useTaskConf
     *            是否配置任务.
     */
    public void setUseTaskConf(Integer useTaskConf) {
        this.useTaskConf = useTaskConf;
    }

    /** @return 编码. */
    @Column(name = "CODE", length = 64)
    public String getCode() {
        return this.code;
    }

    /**
     * @param code
     *            编码.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /** @return 租户. */
    @Column(name = "TENANT_ID", length = 64)
    public String getTenantId() {
        return this.tenantId;
    }

    /**
     * @param tenantId
     *            租户.
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /** @return . */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bpmProcess")
    public Set<BpmTaskDef> getBpmTaskDefs() {
        return this.bpmTaskDefs;
    }

    /**
     * @param bpmTaskDefs
     *            .
     */
    public void setBpmTaskDefs(Set<BpmTaskDef> bpmTaskDefs) {
        this.bpmTaskDefs = bpmTaskDefs;
    }

    /** @return . */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bpmProcess")
    public Set<BpmTaskDefNotice> getBpmTaskDefNotices() {
        return this.bpmTaskDefNotices;
    }

    /**
     * @param bpmTaskDefNotices
     *            .
     */
    public void setBpmTaskDefNotices(Set<BpmTaskDefNotice> bpmTaskDefNotices) {
        this.bpmTaskDefNotices = bpmTaskDefNotices;
    }

    /** @return . */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bpmProcess")
    public Set<BpmInstance> getBpmInstances() {
        return this.bpmInstances;
    }

    /**
     * @param bpmInstances
     *            .
     */
    public void setBpmInstances(Set<BpmInstance> bpmInstances) {
        this.bpmInstances = bpmInstances;
    }
}