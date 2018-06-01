package com.citsh.base.dao;
import org.springframework.stereotype.Repository;

import com.citsh.base.entity.TaskDefBase;
import com.citsh.dao.jpa.BaseDao;
@Repository
public interface TaskDefBaseDao extends BaseDao<TaskDefBase, Long> {

}
