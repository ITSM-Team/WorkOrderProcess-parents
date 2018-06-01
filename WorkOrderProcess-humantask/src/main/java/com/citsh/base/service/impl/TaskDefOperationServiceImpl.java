package com.citsh.base.service.impl;
import com.citsh.base.dao.TaskDefOperationDao;
import com.citsh.base.entity.TaskDefOperation;
import com.citsh.base.service.TaskDefOperationService;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class TaskDefOperationServiceImpl implements TaskDefOperationService {

	@Autowired
	private TaskDefOperationDao taskDefOperationDao;

	public List<TaskDefOperation> findByBaseCodeAndBaseProcessId(String condition, Object... args) {
		List<TaskDefOperation> list = this.taskDefOperationDao.listBySQL(condition, args);
		return list;
	}

	public TaskDefOperation findByBaseCodeAndBaseProcessIdOne(String condition, Object... args) {
		List<TaskDefOperation> list = this.taskDefOperationDao.listBySQL(condition, args);
		if (list.size() > 0) {
			return (TaskDefOperation) list.get(0);
		}
		return null;
	}

	public TaskDefOperation save(TaskDefOperation taskDefOperation) {
		return (TaskDefOperation) this.taskDefOperationDao.save(taskDefOperation);
	}

	public void remove(TaskDefOperation taskDefOperation) {
		this.taskDefOperationDao.delete(taskDefOperation);
	}
}
