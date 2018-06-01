package com.citsh.config.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.citsh.process.entity.BpmProcess;

/**
 * BpmTaskDef .
 */
@Entity
@Table(name = "BPM_TASK_DEF")
public class BpmTaskDef implements java.io.Serializable {
    private static final long serialVersionUID = 0L;

    private Long id;


    private BpmProcess bpmProcess;


    private String taskDefinitionKey;


    private String assignee;


    private String candidate;

    public BpmTaskDef() {
    }

    public BpmTaskDef(Long id) {
        this.id = id;
    }

    public BpmTaskDef(Long id, BpmProcess bpmProcess, String taskDefinitionKey,
            String assignee, String candidate) {
        this.id = id;
        this.bpmProcess = bpmProcess;
        this.taskDefinitionKey = taskDefinitionKey;
        this.assignee = assignee;
        this.candidate = candidate;
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
    @JoinColumn(name = "PROCESS_ID")
    public BpmProcess getBpmProcess() {
        return this.bpmProcess;
    }

    /**
     * @param bpmProcess
     *            null.
     */
    public void setBpmProcess(BpmProcess bpmProcess) {
        this.bpmProcess = bpmProcess;
    }

    /** @return null. */
    @Column(name = "TASK_DEFINITION_KEY", length = 200)
    public String getTaskDefinitionKey() {
        return this.taskDefinitionKey;
    }

    /**
     * @param taskDefinitionKey
     *            null.
     */
    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    /** @return null. */
    @Column(name = "ASSIGNEE", length = 200)
    public String getAssignee() {
        return this.assignee;
    }

    /**
     * @param assignee
     *            null.
     */
    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    /** @return null. */
    @Column(name = "CANDIDATE", length = 200)
    public String getCandidate() {
        return this.candidate;
    }

    /**
     * @param candidate
     *            null.
     */
    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }
}

