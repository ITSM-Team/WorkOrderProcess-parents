package com.citsh.user.dao;


import org.springframework.stereotype.Repository;

import com.citsh.dao.jpa.BaseDao;
import com.citsh.user.entity.BpmConfUser;
@Repository
public interface BpmConfUserDao extends BaseDao<BpmConfUser,Long> {

}

