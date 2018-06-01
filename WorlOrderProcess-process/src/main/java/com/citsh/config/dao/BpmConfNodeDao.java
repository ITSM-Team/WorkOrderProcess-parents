package com.citsh.config.dao;

import com.citsh.config.entity.BpmConfNode;
import com.citsh.dao.jpa.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface BpmConfNodeDao extends BaseDao<BpmConfNode, Long>
{
}
