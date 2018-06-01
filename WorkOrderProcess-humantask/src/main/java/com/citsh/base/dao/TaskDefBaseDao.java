package com.citsh.base.dao;

import com.citsh.base.entity.TaskDefBase;
import com.citsh.dao.jpa.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface TaskDefBaseDao extends BaseDao<TaskDefBase, Long>
{
}