package com.citsh.button.dao;

import com.citsh.button.entity.ButtonInfo;
import com.citsh.dao.jpa.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface ButtoInfoDao extends BaseDao<ButtonInfo, Long>
{
}