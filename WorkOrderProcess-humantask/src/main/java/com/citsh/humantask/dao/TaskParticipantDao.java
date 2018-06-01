package com.citsh.humantask.dao;

import com.citsh.dao.jpa.BaseDao;
import com.citsh.humantask.entity.TaskParticipant;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface TaskParticipantDao extends BaseDao<TaskParticipant, Long>
{
}