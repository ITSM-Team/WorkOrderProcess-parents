package com.citsh.form.entity;

import java.io.Serializable;
import javax.persistence.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;

public class FormSchema implements Serializable {
	private static final long serialVersionUID = 0L;
	@Id
	private Long id;
	private FormTemplate formTemplate;
	private String code;
	private String name;
	private String type;
	private Integer readOnly;
	private Integer notNull;
	private Integer uniqueConstraint;
	private String validator;
	private String conversionPattern;
	private Integer multiple;
	private String enumerationKeys;
	private String enumerationValues;
	private String tenantId;

	public FormSchema() {
	}

	public FormSchema(Long id) {
		this.id = id;
	}

	public FormSchema(Long id, FormTemplate formTemplate, String code, String name, String type, Integer readOnly,
			Integer notNull, Integer uniqueConstraint, String validator, String conversionPattern, Integer multiple,
			String enumerationKeys, String enumerationValues, String tenantId) {
		super();
		this.id = id;
		this.formTemplate = formTemplate;
		this.code = code;
		this.name = name;
		this.type = type;
		this.readOnly = readOnly;
		this.notNull = notNull;
		this.uniqueConstraint = uniqueConstraint;
		this.validator = validator;
		this.conversionPattern = conversionPattern;
		this.multiple = multiple;
		this.enumerationKeys = enumerationKeys;
		this.enumerationValues = enumerationValues;
		this.tenantId = tenantId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FormTemplate getFormTemplate() {
		return formTemplate;
	}

	public void setFormTemplate(FormTemplate formTemplate) {
		this.formTemplate = formTemplate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Integer readOnly) {
		this.readOnly = readOnly;
	}

	public Integer getNotNull() {
		return notNull;
	}

	public void setNotNull(Integer notNull) {
		this.notNull = notNull;
	}

	public Integer getUniqueConstraint() {
		return uniqueConstraint;
	}

	public void setUniqueConstraint(Integer uniqueConstraint) {
		this.uniqueConstraint = uniqueConstraint;
	}

	public String getValidator() {
		return validator;
	}

	public void setValidator(String validator) {
		this.validator = validator;
	}

	public String getConversionPattern() {
		return conversionPattern;
	}

	public void setConversionPattern(String conversionPattern) {
		this.conversionPattern = conversionPattern;
	}

	public Integer getMultiple() {
		return multiple;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	public String getEnumerationKeys() {
		return enumerationKeys;
	}

	public void setEnumerationKeys(String enumerationKeys) {
		this.enumerationKeys = enumerationKeys;
	}

	public String getEnumerationValues() {
		return enumerationValues;
	}

	public void setEnumerationValues(String enumerationValues) {
		this.enumerationValues = enumerationValues;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

}