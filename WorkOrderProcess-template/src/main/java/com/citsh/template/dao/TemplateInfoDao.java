package com.citsh.template.dao;

import com.citsh.dao.jpa.BaseDao;
import com.citsh.template.entity.TemplateInfo;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface TemplateInfoDao extends BaseDao<TemplateInfo, Long>
{
}