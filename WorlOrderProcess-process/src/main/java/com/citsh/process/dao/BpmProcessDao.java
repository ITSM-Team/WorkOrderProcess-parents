package com.citsh.process.dao;

import org.springframework.stereotype.Repository;

import com.citsh.dao.jpa.BaseDao;
import com.citsh.process.entity.BpmProcess;
@Repository
public interface BpmProcessDao extends BaseDao<BpmProcess, Long>{

}
