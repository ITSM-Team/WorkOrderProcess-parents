package com.citsh.form.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;

public class FormTemplate implements Serializable {
	private static final long serialVersionUID = 0L;

	@Id
	private Long id;
	private Integer type;
	private String name;
	private String content;
	private String code;
	private String tenantId;
	private String userId;
	private Date createTime;
	private Set<FormSchema> formSchemas = new HashSet<FormSchema>(0);

	public FormTemplate() {
	}

	public FormTemplate(Long id, Integer type, String name, String content, String code, String tenantId, String userId,
			Date createTime, Set<FormSchema> formSchemas) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
		this.content = content;
		this.code = code;
		this.tenantId = tenantId;
		this.userId = userId;
		this.createTime = createTime;
		this.formSchemas = formSchemas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Set<FormSchema> getFormSchemas() {
		return formSchemas;
	}

	public void setFormSchemas(Set<FormSchema> formSchemas) {
		this.formSchemas = formSchemas;
	}

}