package com.citsh.form.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.citsh.config.entity.BpmConfNode;

/**
 * BpmConfForm 配置表单.
 */
@Entity
@Table(name = "BPM_CONF_FORM")
public class BpmConfForm implements java.io.Serializable {
    private static final long serialVersionUID = 0L;

    /** 主键. */
    private Long id;

    /** 外键，配置节点. */
    private BpmConfNode bpmConfNode;

    /** 值. */
    private String value;

    /** 分类. */
    private Integer type;

    /** 原始值. */
    private String originValue;

    /** 原始类型. */
    private Integer originType;

    /** 状态. */
    private Integer status;

    public BpmConfForm() {
    }

    public BpmConfForm(Long id) {
        this.id = id;
    }

    public BpmConfForm(Long id, BpmConfNode bpmConfNode, String value,
            Integer type, String originValue, Integer originType, Integer status) {
        this.id = id;
        this.bpmConfNode = bpmConfNode;
        this.value = value;
        this.type = type;
        this.originValue = originValue;
        this.originType = originType;
        this.status = status;
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

    /** @return 分类. */
    @Column(name = "TYPE")
    public Integer getType() {
        return this.type;
    }

    /**
     * @param type
     *            分类.
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /** @return 原始值. */
    @Column(name = "ORIGIN_VALUE", length = 200)
    public String getOriginValue() {
        return this.originValue;
    }

    /**
     * @param originValue
     *            原始值.
     */
    public void setOriginValue(String originValue) {
        this.originValue = originValue;
    }

    /** @return 原始类型. */
    @Column(name = "ORIGIN_TYPE")
    public Integer getOriginType() {
        return this.originType;
    }

    /**
     * @param originType
     *            原始类型.
     */
    public void setOriginType(Integer originType) {
        this.originType = originType;
    }

    /** @return 状态. */
    @Column(name = "STATUS")
    public Integer getStatus() {
        return this.status;
    }

    /**
     * @param status
     *            状态.
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}

