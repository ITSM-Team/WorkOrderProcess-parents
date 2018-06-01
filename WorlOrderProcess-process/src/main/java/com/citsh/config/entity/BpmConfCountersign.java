package com.citsh.config.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * BpmConfCountersign .
 */
@Entity
@Table(name = "BPM_CONF_COUNTERSIGN")
public class BpmConfCountersign implements java.io.Serializable {
    private static final long serialVersionUID = 0L;

    /** null. */
    private Long id;

    /** null. */
    private BpmConfNode bpmConfNode;

    /** null. */
    private Integer sequential;

    /** null. */
    private String participant;

    /** null. */
    private Integer type;

    /** null. */
    private Integer rate;

    public BpmConfCountersign() {
    }

    public BpmConfCountersign(Long id) {
        this.id = id;
    }

    public BpmConfCountersign(Long id, BpmConfNode bpmConfNode,
            Integer sequential, String participant, Integer type, Integer rate) {
        this.id = id;
        this.bpmConfNode = bpmConfNode;
        this.sequential = sequential;
        this.participant = participant;
        this.type = type;
        this.rate = rate;
    }

    /** @return null. */
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    /**
     * @param id
     *            null.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return null. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NODE_ID")
    public BpmConfNode getBpmConfNode() {
        return this.bpmConfNode;
    }

    /**
     * @param bpmConfNode
     *            null.
     */
    public void setBpmConfNode(BpmConfNode bpmConfNode) {
        this.bpmConfNode = bpmConfNode;
    }

    /** @return null. */
    @Column(name = "SEQUENTIAL")
    public Integer getSequential() {
        return this.sequential;
    }

    /**
     * @param sequential
     *            null.
     */
    public void setSequential(Integer sequential) {
        this.sequential = sequential;
    }

    /** @return null. */
    @Column(name = "PARTICIPANT", length = 200)
    public String getParticipant() {
        return this.participant;
    }

    /**
     * @param participant
     *            null.
     */
    public void setParticipant(String participant) {
        this.participant = participant;
    }

    /** @return null. */
    @Column(name = "TYPE")
    public Integer getType() {
        return this.type;
    }

    /**
     * @param type
     *            null.
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /** @return null. */
    @Column(name = "RATE")
    public Integer getRate() {
        return this.rate;
    }

    /**
     * @param rate
     *            null.
     */
    public void setRate(Integer rate) {
        this.rate = rate;
    }
}