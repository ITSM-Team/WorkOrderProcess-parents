package com.citsh.dao;
import java.util.Map;

import com.citsh.page.Page;

public interface ProcessConnectorDao {
	/**
	 * 启动流程
	 */
	String startProcess(String userId, String businessKey, String processDefinitionId,
			Map<String, Object> processParameters);

	/**
	 * 部署列表.
	 */
	Page findDeployments(String tenantId, Page page);

	/**
	 * 流程定义.
	 */
	Page findProcessDefinitions(String tenantId, Page page);

	/**
	 * 流程实例.
	 */
	Page findProcessInstances(String tenantId, Page page);

	/**
	 * 任务.
	 */
	Page findTasks(String tenantId, Page page);

	/**
	 * 历史流程实例.
	 */
	Page findHistoricProcessInstances(String tenantId, Page page);

	/**
	 * 历史节点.
	 */
	Page findHistoricActivityInstances(String tenantId, Page page);

	/**
	 * 显示历史任务
	 */
	Page findHistoricTaskInstances(String tenantId, Page page);
}

