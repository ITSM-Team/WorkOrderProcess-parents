package com.citsh.form.mdao;

import com.citsh.form.entity.FormTemplate;
import com.citsh.mdao.mongo.BaseMongoDao;
import org.springframework.stereotype.Repository;

@Repository
public class FormTemplateDao extends BaseMongoDao<FormTemplate> {

	@Override
	protected Class<FormTemplate> getEntityClass() {
		// TODO Auto-generated method stub
		return FormTemplate.class;
	}
}