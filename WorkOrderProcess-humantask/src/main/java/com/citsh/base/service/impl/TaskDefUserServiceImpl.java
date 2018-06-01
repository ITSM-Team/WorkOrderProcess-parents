package com.citsh.base.service.impl;
import java.util.List;
import com.citsh.base.dao.TaskDefUserDao;
import com.citsh.base.entity.TaskDefUser;
import com.citsh.base.service.TaskDefUserService;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TaskDefUserServiceImpl implements TaskDefUserService {

	@Autowired
	private TaskDefUserDao taskDefUserDao;

	public List<TaskDefUser> findByBaseCodeAndBaseProcessId(String condition, Object... args) {
		List<TaskDefUser> list = this.taskDefUserDao.listBySQL(condition, args);
		return list;
	}

	public TaskDefUser findByBaseCodeAndBaseProcessIdOne(String condition, Object... args) {
		List<TaskDefUser> list = this.taskDefUserDao.listBySQL(condition, args);
		if (list.size() > 0) {
			return (TaskDefUser) list.get(0);
		}
		return null;
	}

	public TaskDefUser save(TaskDefUser taskDefUser) {
		return (TaskDefUser) this.taskDefUserDao.save(taskDefUser);
	}

	public void remover(TaskDefUser taskDefUser) {
		this.taskDefUserDao.delete(taskDefUser);
	}

	public void update(TaskDefUser taskDefUser) {
		this.taskDefUserDao.update(taskDefUser);
	}
}
