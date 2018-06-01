package com.citsh.operation.dao;

import com.citsh.dao.jpa.BaseDao;
import com.citsh.operation.entity.BpmConfOperation;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface BpmConfOperationDao extends BaseDao<BpmConfOperation, Long>
{
}