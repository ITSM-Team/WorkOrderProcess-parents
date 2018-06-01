package com.citsh.config.dao;

import com.citsh.config.entity.BpmTaskConf;
import com.citsh.dao.jpa.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface BpmTaskConfDao extends BaseDao<BpmTaskConf, Long>
{
}