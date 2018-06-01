package com.citsh.base.dao;

import com.citsh.base.entity.TaskConfUser;
import com.citsh.dao.jpa.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface TaskConfUserDao extends BaseDao<TaskConfUser, Long>
{
}