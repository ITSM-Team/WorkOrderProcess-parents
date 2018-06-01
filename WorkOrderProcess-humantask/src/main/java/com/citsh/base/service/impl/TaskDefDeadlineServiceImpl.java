package com.citsh.base.service.impl;

import com.citsh.base.dao.TaskDefDeadlineDao;
import com.citsh.base.entity.TaskDefDeadline;
import com.citsh.base.service.TaskDefDeadlineService;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class TaskDefDeadlineServiceImpl implements TaskDefDeadlineService {

	@Autowired
	private TaskDefDeadlineDao taskDefDeadlineDao;

	public List<TaskDefDeadline> findByBaseCodeAndBaseProcessId(String condition, Object... args) {
		List<TaskDefDeadline> list = this.taskDefDeadlineDao.listBySQL(condition, args);
		return list;
	}

	public TaskDefDeadline findByBaseCodeAndBaseProcessIdOne(String condition, Object... args) {
		List<TaskDefDeadline> list = this.taskDefDeadlineDao.listBySQL(condition, args);
		if (list.size() > 0) {
			return (TaskDefDeadline) list.get(0);
		}
		return null;
	}

	public void save(TaskDefDeadline taskDefDeadline) {
		this.taskDefDeadlineDao.save(taskDefDeadline);
	}

	public void remove(TaskDefDeadline taskDefDeadline) {
    this.taskDefDeadlineDao.delete(taskDefDeadline);
  }
}