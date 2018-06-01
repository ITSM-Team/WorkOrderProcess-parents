package com.citsh.delegate.dao;

import com.citsh.dao.jpa.BaseDao;
import com.citsh.delegate.entity.DelegateHistory;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface DelegateHistoryDao extends BaseDao<DelegateHistory, Long>
{
}