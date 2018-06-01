package com.citsh.config.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.citsh.process.entity.BpmProcess;

/**
 * BpmInstance 流程实例.
 */
@Entity
@Table(name = "BPM_INSTANCE")
public class BpmInstance implements java.io.Serializable {
    private static final long serialVersionUID = 0L;

    /** 主键. */
    private Long id;

    /** 外键，流程定义. */
    private BpmProcess bpmProcess;

    /** 名称. */
    private String name;

    /** 业务标识. */
    private String businessKey;

    /** 外部引用. */
    private String ref;

    /** 创建时间. */
    private Date createTime;

    /** 发起人. */
    private String initiator;

    /** 优先级. */
    private Integer priority;

    /** 租户. */
    private String tenantId;

    public BpmInstance() {
    }

    public BpmInstance(Long id) {
        this.id = id;
    }

    public BpmInstance(Long id, BpmProcess bpmProcess, String name,
            String businessKey, String ref, Date createTime, String initiator,
            Integer priority, String tenantId) {
        this.id = id;
        this.bpmProcess = bpmProcess;
        this.name = name;
        this.businessKey = businessKey;
        this.ref = ref;
        this.createTime = createTime;
        this.initiator = initiator;
        this.priority = priority;
        this.tenantId = tenantId;
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

    /** @return 外键，流程定义. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROCESS_ID")
    public BpmProcess getBpmProcess() {
        return this.bpmProcess;
    }

    /**
     * @param bpmProcess
     *            外键，流程定义.
     */
    public void setBpmProcess(BpmProcess bpmProcess) {
        this.bpmProcess = bpmProcess;
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

    /** @return 业务标识. */
    @Column(name = "BUSINESS_KEY", length = 64)
    public String getBusinessKey() {
        return this.businessKey;
    }

    /**
     * @param businessKey
     *            业务标识.
     */
    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    /** @return 外部引用. */
    @Column(name = "REF", length = 64)
    public String getRef() {
        return this.ref;
    }

    /**
     * @param ref
     *            外部引用.
     */
    public void setRef(String ref) {
        this.ref = ref;
    }

    /** @return 创建时间. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_TIME", length = 26)
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * @param createTime
     *            创建时间.
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** @return 发起人. */
    @Column(name = "INITIATOR", length = 64)
    public String getInitiator() {
        return this.initiator;
    }

    /**
     * @param initiator
     *            发起人.
     */
    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    /** @return 优先级. */
    @Column(name = "PRIORITY")
    public Integer getPriority() {
        return this.priority;
    }

    /**
     * @param priority
     *            优先级.
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
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
}
