package com.citsh.grop.dao;

import com.citsh.dao.jpa.BaseDao;
import com.citsh.grop.entity.BpmCategory;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface BpmCategoryDao extends BaseDao<BpmCategory, Long> {
}
