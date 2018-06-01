package com.citsh.process.dao;

import com.citsh.dao.jpa.BaseDao;
import com.citsh.process.entity.BpmProcess;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface BpmProcessDao extends BaseDao<BpmProcess, Long>
{
}