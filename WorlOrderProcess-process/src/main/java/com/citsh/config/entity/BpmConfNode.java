package com.citsh.config.entity;
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

import com.citsh.assign.entity.BpmConfAssign;
import com.citsh.form.entity.BpmConfForm;
import com.citsh.listener.entity.BpmConfListener;
import com.citsh.notice.entity.BpmConfNotice;
import com.citsh.operation.entity.BpmConfOperation;
import com.citsh.rule.entity.BpmConfRule;
import com.citsh.user.entity.BpmConfUser;

/**
 * BpmConfNode 节点配置.
 */
@Entity
@Table(name = "BPM_CONF_NODE")
public class BpmConfNode implements java.io.Serializable {
    private static final long serialVersionUID = 0L;

    /** 主键. */
    private Long id;

    /** 外键，流程配置. */
    private BpmConfBase bpmConfBase;

    /** 节点ID. */
    private String code;

    /** 节点名称. */
    private String name;

    /** 节点类型. */
    private String type;

    /** 配置用户. */
    private Integer confUser;

    /** 配置回调. */
    private Integer confListener;

    /** 配置规则. */
    private Integer confRule;

    /** 配置表单. */
    private Integer confForm;

    /** 配置操作. */
    private Integer confOperation;

    /** 配置提醒. */
    private Integer confNotice;

    /** 排序. */
    private Integer priority;

    /** . */
    private Set<BpmConfListener> bpmConfListeners = new HashSet<BpmConfListener>(0);

    /** . */
    private Set<BpmConfNotice> bpmConfNotices = new HashSet<BpmConfNotice>(0);

    /** . */
    private Set<BpmConfUser> bpmConfUsers = new HashSet<BpmConfUser>(0);

    /** . */
    private Set<BpmConfAssign> bpmConfAssigns = new HashSet<BpmConfAssign>(0);

    /** . */
    private Set<BpmConfCountersign> bpmConfCountersigns = new HashSet<BpmConfCountersign>(
            0);

    /** . */
    private Set<BpmConfForm> bpmConfForms = new HashSet<BpmConfForm>(0);

    /** . */
    private Set<BpmConfRule> bpmConfRules = new HashSet<BpmConfRule>(0);

    /** . */
    private Set<BpmConfOperation> bpmConfOperations = new HashSet<BpmConfOperation>(
            0);

    public BpmConfNode() {
    }

    public BpmConfNode(Long id) {
        this.id = id;
    }

    public BpmConfNode(Long id, BpmConfBase bpmConfBase, String code,
            String name, String type, Integer confUser, Integer confListener,
            Integer confRule, Integer confForm, Integer confOperation,
            Integer confNotice, Integer priority,
            Set<BpmConfListener> bpmConfListeners,
            Set<BpmConfNotice> bpmConfNotices, Set<BpmConfUser> bpmConfUsers,
            Set<BpmConfAssign> bpmConfAssigns,
            Set<BpmConfCountersign> bpmConfCountersigns,
            Set<BpmConfForm> bpmConfForms, Set<BpmConfRule> bpmConfRules,
            Set<BpmConfOperation> bpmConfOperations) {
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

    /** @return 节点ID. */
    @Column(name = "CODE", length = 200)
    public String getCode() {
        return this.code;
    }

    /**
     * @param code
     *            节点ID.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /** @return 节点名称. */
    @Column(name = "NAME", length = 200)
    public String getName() {
        return this.name;
    }

    /**
     * @param name
     *            节点名称.
     */
    public void setName(String name) {
        this.name = name;
    }

    /** @return 节点类型. */
    @Column(name = "TYPE", length = 200)
    public String getType() {
        return this.type;
    }

    /**
     * @param type
     *            节点类型.
     */
    public void setType(String type) {
        this.type = type;
    }

    /** @return 配置用户. */
    @Column(name = "CONF_USER")
    public Integer getConfUser() {
        return this.confUser;
    }

    /**
     * @param confUser
     *            配置用户.
     */
    public void setConfUser(Integer confUser) {
        this.confUser = confUser;
    }

    /** @return 配置回调. */
    @Column(name = "CONF_LISTENER")
    public Integer getConfListener() {
        return this.confListener;
    }

    /**
     * @param confListener
     *            配置回调.
     */
    public void setConfListener(Integer confListener) {
        this.confListener = confListener;
    }

    /** @return 配置规则. */
    @Column(name = "CONF_RULE")
    public Integer getConfRule() {
        return this.confRule;
    }

    /**
     * @param confRule
     *            配置规则.
     */
    public void setConfRule(Integer confRule) {
        this.confRule = confRule;
    }

    /** @return 配置表单. */
    @Column(name = "CONF_FORM")
    public Integer getConfForm() {
        return this.confForm;
    }

    /**
     * @param confForm
     *            配置表单.
     */
    public void setConfForm(Integer confForm) {
        this.confForm = confForm;
    }

    /** @return 配置操作. */
    @Column(name = "CONF_OPERATION")
    public Integer getConfOperation() {
        return this.confOperation;
    }

    /**
     * @param confOperation
     *            配置操作.
     */
    public void setConfOperation(Integer confOperation) {
        this.confOperation = confOperation;
    }

    /** @return 配置提醒. */
    @Column(name = "CONF_NOTICE")
    public Integer getConfNotice() {
        return this.confNotice;
    }

    /**
     * @param confNotice
     *            配置提醒.
     */
    public void setConfNotice(Integer confNotice) {
        this.confNotice = confNotice;
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

    /** @return . */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bpmConfNode")
    public Set<BpmConfListener> getBpmConfListeners() {
        return this.bpmConfListeners;
    }

    /**
     * @param bpmConfListeners
     *            .
     */
    public void setBpmConfListeners(Set<BpmConfListener> bpmConfListeners) {
        this.bpmConfListeners = bpmConfListeners;
    }

    /** @return . */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bpmConfNode")
    public Set<BpmConfNotice> getBpmConfNotices() {
        return this.bpmConfNotices;
    }

    /**
     * @param bpmConfNotices
     *            .
     */
    public void setBpmConfNotices(Set<BpmConfNotice> bpmConfNotices) {
        this.bpmConfNotices = bpmConfNotices;
    }

    /** @return . */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bpmConfNode")
    public Set<BpmConfUser> getBpmConfUsers() {
        return this.bpmConfUsers;
    }

    /**
     * @param bpmConfUsers
     *            .
     */
    public void setBpmConfUsers(Set<BpmConfUser> bpmConfUsers) {
        this.bpmConfUsers = bpmConfUsers;
    }

    /** @return . */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bpmConfNode")
    public Set<BpmConfAssign> getBpmConfAssigns() {
        return this.bpmConfAssigns;
    }

    /**
     * @param bpmConfAssigns
     *            .
     */
    public void setBpmConfAssigns(Set<BpmConfAssign> bpmConfAssigns) {
        this.bpmConfAssigns = bpmConfAssigns;
    }

    /** @return . */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bpmConfNode")
    public Set<BpmConfCountersign> getBpmConfCountersigns() {
        return this.bpmConfCountersigns;
    }

    /**
     * @param bpmConfCountersigns
     *            .
     */
    public void setBpmConfCountersigns(
            Set<BpmConfCountersign> bpmConfCountersigns) {
        this.bpmConfCountersigns = bpmConfCountersigns;
    }

    /** @return . */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bpmConfNode")
    public Set<BpmConfForm> getBpmConfForms() {
        return this.bpmConfForms;
    }

    /**
     * @param bpmConfForms
     *            .
     */
    public void setBpmConfForms(Set<BpmConfForm> bpmConfForms) {
        this.bpmConfForms = bpmConfForms;
    }

    /** @return . */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bpmConfNode")
    public Set<BpmConfRule> getBpmConfRules() {
        return this.bpmConfRules;
    }

    /**
     * @param bpmConfRules
     *            .
     */
    public void setBpmConfRules(Set<BpmConfRule> bpmConfRules) {
        this.bpmConfRules = bpmConfRules;
    }

    /** @return . */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bpmConfNode")
    public Set<BpmConfOperation> getBpmConfOperations() {
        return this.bpmConfOperations;
    }

    /**
     * @param bpmConfOperations
     *            .
     */
    public void setBpmConfOperations(Set<BpmConfOperation> bpmConfOperations) {
        this.bpmConfOperations = bpmConfOperations;
    }
}