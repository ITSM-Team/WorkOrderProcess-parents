package com.citsh.base.service.impl;
import com.citsh.base.dao.TaskDefNotificationDao;
import com.citsh.base.entity.TaskDefNotification;
import com.citsh.base.service.TaskDefNotificationService;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class TaskDefNotificationServiceImpl implements TaskDefNotificationService {

	@Autowired
	private TaskDefNotificationDao defNotificationDao;

	public List<TaskDefNotification> findByBaseCodeAndBaseProcessId(String condition, Object... args) {
		List<TaskDefNotification> list = this.defNotificationDao.listBySQL(condition, args);
		return list;
	}

	public TaskDefNotification findByBaseCodeAndBaseProcessIdOne(String condition, Object... args) {
		List<TaskDefNotification> list = this.defNotificationDao.listBySQL(condition, args);
		if (list.size() > 0) {
			return (TaskDefNotification) list.get(0);
		}
		return null;
	}

	public void save(TaskDefNotification taskDefNotification) {
		this.defNotificationDao.save(taskDefNotification);
	}

	public void remove(TaskDefNotification taskDefNotification) {
		this.defNotificationDao.delete(taskDefNotification);
	}
}
