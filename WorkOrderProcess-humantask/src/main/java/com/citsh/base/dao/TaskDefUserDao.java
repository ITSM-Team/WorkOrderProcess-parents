package com.citsh.base.dao;
import com.citsh.base.entity.TaskDefUser;
import com.citsh.dao.jpa.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface TaskDefUserDao extends BaseDao<TaskDefUser, Long>
{
}
