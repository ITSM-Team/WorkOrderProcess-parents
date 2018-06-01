package com.citsh.humantask.service.impl;

import com.citsh.humantask.dao.TaskInfoDao;
import com.citsh.humantask.entity.TaskInfo;
import com.citsh.humantask.service.TaskInfoService;
import com.citsh.page.Page;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class TaskInfoServiceImpl implements TaskInfoService {

	@Autowired
	private TaskInfoDao taskInfoDao;

	@PersistenceContext
	private EntityManager em;

	public TaskInfo findById(Long id) {
		return (TaskInfo) this.taskInfoDao.findOne(id);
	}

	public void remove(Long id) {
		this.taskInfoDao.delete(id);
	}

	public TaskInfo listBySQLOne(String condition, Object[] objects) {
		List taskInfos = this.taskInfoDao.listBySQL(condition, objects);
		if (taskInfos.size() > 0) {
			return (TaskInfo) taskInfos.get(0);
		}
		return null;
	}

	public List<TaskInfo> listBySQL(String condition, Object[] objects) {
		return this.taskInfoDao.listBySQL(condition, objects);
	}

	public void save(TaskInfo taskInfo) {
		this.taskInfoDao.save(taskInfo);
	}

	public Page pageQuery(String condition, int pageNo, int pageSize, Object... args) {
		return this.taskInfoDao.pageListByHSQL(condition, pageNo, pageSize, args);
	}

	public Page pageQuery(int pageNo, int pageSize, Map<String, Object> map) {
		String hsql = "select distinct t from TaskInfo t join t.taskParticipants p with p.ref in (:partyIds) where t.tenantId=:tenantId and t.assignee=null and t.status='active'";
		Page page = this.taskInfoDao.pageListByHSQL(hsql, pageNo, pageSize, map);
		return page;
	}
}