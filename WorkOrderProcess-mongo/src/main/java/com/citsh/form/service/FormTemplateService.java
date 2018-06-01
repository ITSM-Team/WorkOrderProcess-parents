package com.citsh.form.service;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import com.citsh.form.entity.FormTemplate;
import com.citsh.from.FormConnector;
import com.citsh.page.Page;
import com.citsh.query.PropertyFilter;

public abstract interface FormTemplateService extends FormConnector {
	public Page pageQuery(Page page, List<PropertyFilter> propertyFilters, Query query);

	public FormTemplate findById(Long id);

	public FormTemplate save(FormTemplate formTemplate);

	public FormTemplate remove(Query query);
}