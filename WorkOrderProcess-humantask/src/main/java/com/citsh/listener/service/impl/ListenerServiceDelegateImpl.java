package com.citsh.listener.service.impl;

import com.citsh.delegate.service.DelegateService;
import com.citsh.humantask.entity.TaskInfo;
import com.citsh.listener.service.ListenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ListenerServiceDelegateImpl implements ListenerService {

	@Autowired
	private DelegateService delegateService;

	public void onCreate(TaskInfo taskInfo) {
		String humanTaskId = String.valueOf(taskInfo.getId());
		String assignee = taskInfo.getAssignee();
		String processDefinitionId = taskInfo.getProcessDefinitionId();
		String tenantId = taskInfo.getTenantId();
		String attorney = this.delegateService.findAttorney(assignee, processDefinitionId, taskInfo.getCode(),
				tenantId);
		if (attorney != null) {
			taskInfo.setOwner(assignee);
			taskInfo.setAssignee(attorney);
			this.delegateService.recordDelegate(assignee, attorney, humanTaskId, tenantId);
		}
	}

	public void onComplete(TaskInfo taskInfo) {
	}
}