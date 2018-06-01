package com.citsh.base.dao;
import com.citsh.base.entity.TaskDefOperation;
import com.citsh.dao.jpa.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface TaskDefOperationDao extends BaseDao<TaskDefOperation, Long>
{
}
