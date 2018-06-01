package com.citsh.base.dao;

import com.citsh.base.entity.TaskDefDeadline;
import com.citsh.dao.jpa.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface TaskDefDeadlineDao extends BaseDao<TaskDefDeadline, Long>
{
}