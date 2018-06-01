package com.citsh.notice.dao;

import com.citsh.dao.jpa.BaseDao;
import com.citsh.notice.entity.BpmMailTemplate;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface BpmMailTemplateDao extends BaseDao<BpmMailTemplate, Long>
{
}