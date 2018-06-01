package com.citsh.service.impl;

import com.citsh.dao.ProcessConnectorDao;
import com.citsh.page.Page;
import com.citsh.service.ProcessConnectorService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessConnectorServiceImpl implements ProcessConnectorService {

	@Autowired
	private ProcessConnectorDao processConnectorDao;

	public String startProcess(String userId, String businessKey, String processDefinitionId,
			Map<String, Object> processParameters) {
		return this.processConnectorDao.startProcess(userId, businessKey, processDefinitionId, processParameters);
	}

	public Page findDeployments(String tenantId, Page page) {
		return this.processConnectorDao.findDeployments(tenantId, page);
	}

	public Page findProcessDefinitions(String tenantId, Page page) {
		return this.processConnectorDao.findProcessDefinitions(tenantId, page);
	}

	public Page findProcessInstances(String tenantId, Page page) {
		return this.processConnectorDao.findProcessInstances(tenantId, page);
	}

	public Page findTasks(String tenantId, Page page) {
		return this.processConnectorDao.findTasks(tenantId, page);
	}

	public Page findHistoricProcessInstances(String tenantId, Page page) {
		return this.processConnectorDao.findHistoricProcessInstances(tenantId, page);
	}

	public Page findHistoricActivityInstances(String tenantId, Page page) {
		return this.processConnectorDao.findHistoricActivityInstances(tenantId, page);
	}

	public Page findHistoricTaskInstances(String tenantId, Page page) {
		return this.processConnectorDao.findHistoricTaskInstances(tenantId, page);
	}
}