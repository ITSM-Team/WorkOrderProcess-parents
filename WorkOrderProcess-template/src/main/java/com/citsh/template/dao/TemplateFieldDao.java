package com.citsh.template.dao;

import com.citsh.dao.jpa.BaseDao;
import com.citsh.template.entity.TemplateField;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface TemplateFieldDao extends BaseDao<TemplateField, Long>
{
}