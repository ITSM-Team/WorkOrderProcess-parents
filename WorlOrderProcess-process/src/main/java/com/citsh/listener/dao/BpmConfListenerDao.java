package com.citsh.listener.dao;

import com.citsh.dao.jpa.BaseDao;
import com.citsh.listener.entity.BpmConfListener;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface BpmConfListenerDao extends BaseDao<BpmConfListener, Long>
{
}