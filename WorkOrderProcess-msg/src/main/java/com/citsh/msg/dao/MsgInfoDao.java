package com.citsh.msg.dao;

import com.citsh.dao.jpa.BaseDao;
import com.citsh.msg.entity.MsgInfo;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface MsgInfoDao extends BaseDao<MsgInfo, Long>
{
}