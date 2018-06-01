package com.citsh.base.service.impl;

import com.citsh.base.dao.TaskDefBaseDao;
import com.citsh.base.entity.TaskDefBase;
import com.citsh.base.service.TaskDefBaseService;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class TaskDefBaseServiceImpl implements TaskDefBaseService {

	@Autowired
	private TaskDefBaseDao taskDefBaseDao;

	public TaskDefBase findByCodeAndProIdOne(String condition, Object... args) {
		List<TaskDefBase> list = this.taskDefBaseDao.listBySQL(condition, args);
		if (list.size() > 0) {
			return (TaskDefBase) list.get(0);
		}
		return null;
	}

	public TaskDefBase save(TaskDefBase taskDefBase) {
		return (TaskDefBase) this.taskDefBaseDao.save(taskDefBase);
	}
}
