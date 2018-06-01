package com.citsh.humantask.dao;

import com.citsh.dao.jpa.BaseDao;
import com.citsh.humantask.entity.TaskDeadline;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface TaskDeadlineDao extends BaseDao<TaskDeadline, Long>
{
}