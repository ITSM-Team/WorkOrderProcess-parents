package com.citsh.user.dao;

import com.citsh.dao.jpa.BaseDao;
import com.citsh.user.entity.BpmConfUser;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface BpmConfUserDao extends BaseDao<BpmConfUser, Long>
{
}