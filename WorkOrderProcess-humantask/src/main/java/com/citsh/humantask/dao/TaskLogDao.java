package com.citsh.humantask.dao;

import com.citsh.dao.jpa.BaseDao;
import com.citsh.humantask.entity.TaskLog;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface TaskLogDao extends BaseDao<TaskLog, Long>
{
}