package com.citsh.rule.dao;

import com.citsh.dao.jpa.BaseDao;
import com.citsh.rule.entity.BpmConfRule;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface BpmConfRuleDao extends BaseDao<BpmConfRule, Long>
{
}