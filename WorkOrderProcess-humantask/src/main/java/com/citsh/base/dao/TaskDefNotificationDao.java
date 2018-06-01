package com.citsh.base.dao;

import com.citsh.base.entity.TaskDefNotification;
import com.citsh.dao.jpa.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface TaskDefNotificationDao extends BaseDao<TaskDefNotification, Long> {
}
