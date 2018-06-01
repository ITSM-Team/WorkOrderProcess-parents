package com.citsh.humantask.dao;

import com.citsh.dao.jpa.BaseDao;
import com.citsh.humantask.entity.TaskInfo;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface TaskInfoDao extends BaseDao<TaskInfo, Long>
{
}