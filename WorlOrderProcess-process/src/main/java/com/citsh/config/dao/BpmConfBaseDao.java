package com.citsh.config.dao;

import com.citsh.config.entity.BpmConfBase;
import com.citsh.dao.jpa.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface BpmConfBaseDao extends BaseDao<BpmConfBase, Long>
{
}