package com.citsh.base.service.impl;

import com.citsh.base.dao.TaskConfUserDao;
import com.citsh.base.entity.TaskConfUser;
import com.citsh.base.service.TaskConfUserService;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class TaskConfUserServiceImpl implements TaskConfUserService {

	@Autowired
	private TaskConfUserDao taskConfUserDao;

	public TaskConfUser findByCodeAndProIdOne(String condition, Object... args) {
		List<TaskConfUser> list = this.taskConfUserDao.listBySQL(condition, args);
		if (list.size() > 0) {
			return (TaskConfUser) list.get(0);
		}
		return null;
	}

	public TaskConfUser findBySQL(String condition, Object... args) {
		List<TaskConfUser> list = this.taskConfUserDao.listBySQL(condition, args);
		if (list.size() > 0) {
			return (TaskConfUser) list.get(0);
		}
		return null;
	}

	public void save(TaskConfUser taskConfUser) {
		this.taskConfUserDao.save(taskConfUser);
	}
}
