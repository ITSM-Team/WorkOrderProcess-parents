package com.citsh.operation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.citsh.config.entity.BpmConfNode;

/**
 * BpmConfOperation 配置操作.
 */
@Entity
@Table(name = "BPM_CONF_OPERATION")
public class BpmConfOperation implements java.io.Serializable {
    private static final long serialVersionUID = 0L;

    /** 主键. */
    private Long id;

    /** 外键，配置节点. */
    private BpmConfNode bpmConfNode;

    /** 值. */
    private String value;

    /** 排序. */
    private Integer priority;

    public BpmConfOperation() {
    }

    public BpmConfOperation(Long id) {
        this.id = id;
    }

    public BpmConfOperation(Long id, BpmConfNode bpmConfNode, String value,
            Integer priority) {
        this.id = id;
        this.bpmConfNode = bpmConfNode;
        this.value = value;
        this.priority = priority;
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

    /** @return 外键，配置节点. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NODE_ID")
    public BpmConfNode getBpmConfNode() {
        return this.bpmConfNode;
    }

    /**
     * @param bpmConfNode
     *            外键，配置节点.
     */
    public void setBpmConfNode(BpmConfNode bpmConfNode) {
        this.bpmConfNode = bpmConfNode;
    }

    /** @return 值. */
    @Column(name = "VALUE", length = 200)
    public String getValue() {
        return this.value;
    }

    /**
     * @param value
     *            值.
     */
    public void setValue(String value) {
        this.value = value;
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
}