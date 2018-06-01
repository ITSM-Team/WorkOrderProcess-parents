package com.citsh.template.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity
@Table(name = "sms_template")
public class SmsTemplate implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 模版ID
	 */
	private Long id;

	/**
	 * 短信名称
	 */
	private String smsName;

	/**
	 * 短信服务器
	 */
	private String smsServer;

	/**
	 * 短信内容
	 */
	private String smsContent;
	
	/**
	 * 创建时间
	 */
	private Date creationTime;

	/**
	 * 租户ID
	 */
	private String tenantId;

	public SmsTemplate() {
		super();
	}

	public SmsTemplate(Long id) {
		super();
		this.id = id;
	}

	public SmsTemplate(String smsName, String smsServer, String smsContent) {
		super();
		this.smsName = smsName;
		this.smsServer = smsServer;
		this.smsContent = smsContent;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "SMS_ID", unique = true, nullable = false,updatable=false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "SMS_NAME", length = 50)
	public String getSmsName() {
		return smsName;
	}

	public void setSmsName(String smsName) {
		this.smsName = smsName;
	}

	@Column(name = "SMS_SERVER", length = 50)
	public String getSmsServer() {
		return smsServer;
	}

	public void setSmsServer(String smsServer) {
		this.smsServer = smsServer;
	}

	@Column(name = "SMS_CONTENT", length = 300)
	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name = "TENANT_ID", length = 50,updatable=false)
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(name = "CREATION_TIME", nullable = false, length = 25,updatable=false)
	@org.hibernate.annotations.CreationTimestamp
	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

}
