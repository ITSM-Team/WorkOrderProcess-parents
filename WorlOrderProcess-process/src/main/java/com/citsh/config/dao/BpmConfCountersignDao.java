package com.citsh.config.dao;

import com.citsh.config.entity.BpmConfCountersign;
import com.citsh.dao.jpa.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface BpmConfCountersignDao extends BaseDao<BpmConfCountersign, Long>
{
}