package com.citsh.template.dao;

import org.springframework.stereotype.Repository;
import com.citsh.dao.jpa.BaseDao;
import com.citsh.template.entity.SmsTemplate;

@Repository
public interface SmsTemplateDao extends BaseDao<SmsTemplate, Long> {

}
