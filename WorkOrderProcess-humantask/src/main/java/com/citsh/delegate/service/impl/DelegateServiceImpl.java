package com.citsh.delegate.service.impl;

import com.citsh.delegate.entity.DelegateHistory;
import com.citsh.delegate.entity.DelegateInfo;
import com.citsh.delegate.service.DelegateHistoryService;
import com.citsh.delegate.service.DelegateInfoService;
import com.citsh.delegate.service.DelegateService;
import com.citsh.id.IdGenerator;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DelegateServiceImpl implements DelegateService {
	private Logger logger = LoggerFactory.getLogger(DelegateServiceImpl.class);

	@Autowired
	private DelegateInfoService delegateInfoService;

	@Autowired
	private DelegateHistoryService delegateHistoryService;

	@Autowired
	private IdGenerator idGenerator;

	public String findAttorney(String userId, String processDefinitionId, String taskDefinitionKey, String tenantId) {
		DelegateInfo delegateInfo = getDelegateInfo(userId, processDefinitionId, taskDefinitionKey, tenantId);
		if (delegateInfo == null) {
			return null;
		}
		return delegateInfo.getAttorney();
	}

	public void recordDelegate(String userId, String attorney, String taskId, String tenantId) {
		saveRecord(userId, attorney, taskId, tenantId);
	}

	public void cancel(String taskId, String userId, String tenantId) {
	}

	public void complete(String taskId, String userId, String tenantId) {
	}

	public DelegateInfo getDelegateInfo(String targetAssignee, String targetProcessDefinitionId,
			String targetTaskDefinitionKey, String tenantId) {
		List<DelegateInfo> delegateInfos = this.delegateInfoService.listBySQL(
				" assignee=? and status=1 and tenantId=? order by id desc", new Object[] { targetAssignee, tenantId });

		for (DelegateInfo delegateInfo : delegateInfos) {
			this.logger.debug("delegateInfo : {}", delegateInfo);

			Long id = delegateInfo.getId();
			String assignee = delegateInfo.getAssignee();
			String attorney = delegateInfo.getAttorney();
			String processDefinitionId = delegateInfo.getProcessDefinitionId();
			String taskDefinitionKey = delegateInfo.getTaskDefinitionKey();
			Date startTime = delegateInfo.getStartTime();
			Date endTime = delegateInfo.getEndTime();
			Integer status = delegateInfo.getStatus();

			if (timeNotBetweenNow(startTime, endTime)) {
				this.logger.info("timeNotBetweenNow");
				continue;
			}
			if ((processDefinitionId != null) && (!processDefinitionId.equals(targetProcessDefinitionId))) {
				this.logger.info("processDefinitionId not matches");
				continue;
			}
			if ((taskDefinitionKey != null) && (!taskDefinitionKey.equals(targetTaskDefinitionKey))) {
				this.logger.info("taskDefinitionKey not matches");
				continue;
			}
			this.logger.info("delegate to {}", attorney);
			return delegateInfo;
		}
		return null;
	}

	public void saveRecord(String assignee, String attorney, String taskId, String tenantId) {
		DelegateHistory delegateHistory = new DelegateHistory();
		delegateHistory.setAssignee(assignee);
		delegateHistory.setAttorney(attorney);
		delegateHistory.setDelegateTime(new Date());
		delegateHistory.setTaskId(taskId);
		delegateHistory.setStatus(Integer.valueOf(1));
		delegateHistory.setTenantId(tenantId);
		delegateHistory.setId(this.idGenerator.generateId());
		this.delegateHistoryService.save(delegateHistory);
	}

	public void removeRecord(Long id) {
		this.delegateInfoService.remove(id);
	}

	public void addDelegateInfo(String assignee, String attorney, Date startTime, Date endTime,
			String processDefinitionId, String taskDefinitionKey, String tenantId) {
		DelegateInfo delegateInfo = new DelegateInfo();
		delegateInfo.setAssignee(assignee);
		delegateInfo.setAttorney(attorney);
		delegateInfo.setStartTime(startTime);
		delegateInfo.setEndTime(endTime);
		delegateInfo.setProcessDefinitionId(processDefinitionId);
		delegateInfo.setTaskDefinitionKey(taskDefinitionKey);
		delegateInfo.setStatus(Integer.valueOf(1));
		delegateInfo.setTenantId(tenantId);
		delegateInfo.setId(this.idGenerator.generateId());
		this.delegateInfoService.save(delegateInfo);
	}

	private boolean timeNotBetweenNow(Date startTime, Date endTime) {
		Date now = new Date();

		if (startTime != null) {
			return now.before(startTime);
		}

		if (endTime != null) {
			return now.after(endTime);
		}

		return false;
	}
}