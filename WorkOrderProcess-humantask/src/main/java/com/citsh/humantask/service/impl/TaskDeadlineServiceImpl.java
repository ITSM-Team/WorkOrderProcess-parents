package com.citsh.humantask.service.impl;

import com.citsh.humantask.dao.TaskDeadlineDao;
import com.citsh.humantask.entity.TaskDeadline;
import com.citsh.humantask.service.TaskDeadlineService;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class TaskDeadlineServiceImpl implements TaskDeadlineService {

	@Autowired
	private TaskDeadlineDao taskDeadlineDao;

	public void removeAll(Set<TaskDeadline> taskDeadlines) {
		this.taskDeadlineDao.delete(taskDeadlines);
	}

	public void save(TaskDeadline taskDeadline) {
		this.taskDeadlineDao.save(taskDeadline);
	}

	public List<TaskDeadline> listBySQL(String condition, Object[] objects) {
		return this.taskDeadlineDao.listBySQL(condition, objects);
	}

	public void removeAll(List<TaskDeadline> taskDeadlines) {
		this.taskDeadlineDao.delete(taskDeadlines);
	}
}