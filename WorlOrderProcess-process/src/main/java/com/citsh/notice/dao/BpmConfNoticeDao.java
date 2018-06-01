package com.citsh.notice.dao;

import com.citsh.dao.jpa.BaseDao;
import com.citsh.notice.entity.BpmConfNotice;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface BpmConfNoticeDao extends BaseDao<BpmConfNotice, Long>
{
}