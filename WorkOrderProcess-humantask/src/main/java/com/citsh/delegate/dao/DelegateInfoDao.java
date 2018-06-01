package com.citsh.delegate.dao;

import com.citsh.dao.jpa.BaseDao;
import com.citsh.delegate.entity.DelegateInfo;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface DelegateInfoDao extends BaseDao<DelegateInfo, Long>
{
}