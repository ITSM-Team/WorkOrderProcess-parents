package com.citsh.support.impl;

import com.citsh.cmd.FindFirstTaskFormCmd;
import com.citsh.form.entity.BpmConfForm;
import com.citsh.form.service.BpmConfFormService;
import com.citsh.from.FirstTaskForm;
import com.citsh.from.FormConnector;
import com.citsh.humantask.TaskDefinitionConnector;
import com.citsh.humantask.TaskUserDTO;
import com.citsh.page.Page;
import com.citsh.process.ProcessDTO;
import com.citsh.process.entity.BpmProcess;
import com.citsh.process.service.BpmProcessService;
import com.citsh.support.ProcessService;
import java.util.List;
import java.util.Map;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ProcessServiceImpl implements ProcessService {
	private Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);

	@Autowired
	private ProcessEngine processEngine;

	@Autowired
	private BpmConfFormService bpmConfFormService;

	@Autowired
	private BpmProcessService bpmProcessService;

	@Autowired
	private FormConnector formConnector;

	@Autowired
	private TaskDefinitionConnector taskDefinitionConnector;

    /**
     * 获得启动表单.
     */
	public com.citsh.from.FormDTO findStartForm(String processDefinitionId) {
		ProcessDefinition processDefinition = (ProcessDefinition) this.processEngine.getRepositoryService()
				.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();

		FirstTaskForm firstTaskForm = (FirstTaskForm) this.processEngine.getManagementService()
				.executeCommand(new FindFirstTaskFormCmd(processDefinitionId));

		if ((!firstTaskForm.isExists()) && (firstTaskForm.getActivityId() != null)) {
			com.citsh.humantask.FormDTO formDTO = this.taskDefinitionConnector.findForm(firstTaskForm.getActivityId(),
					processDefinitionId);

			if (formDTO != null) {
				firstTaskForm.setFormKey(formDTO.getKey());
			}
		}
		if (!firstTaskForm.isExists()) {
			this.logger.info("cannot find startForm : {}", processDefinitionId);
			return new com.citsh.from.FormDTO();
		}
		if (!firstTaskForm.isTaskForm()) {
			this.logger.info("find startEventForm : {}", processDefinitionId);
			return findStartEventForm(firstTaskForm);
		}

		List<TaskUserDTO> taskUserDTOs = this.taskDefinitionConnector.findTaskUsers(firstTaskForm.getActivityId(),
				processDefinitionId);

		String assignee = firstTaskForm.getAssignee();
		this.logger.debug("assignee : {}", assignee);
		for (TaskUserDTO taskUserDTO : taskUserDTOs) {
			this.logger.debug("catalog : {}, user : {}", taskUserDTO.getCatalog(), taskUserDTO.getValue());
			if ("assignee".equals(taskUserDTO.getCatalog())) {
				assignee = taskUserDTO.getValue();
				break;
			}
		}
		this.logger.debug("assignee : {}", assignee);
		boolean exists = assignee != null;

		if ((("${" + firstTaskForm.getInitiatorName() + "}").equals(assignee)) || ("常用语:流程发起人".equals(assignee))
				|| ((assignee != null) && (assignee.equals(Authentication.getAuthenticatedUserId()))))
			exists = true;
		else {
			exists = false;
		}
		if (!exists) {
			this.logger.info("cannot find taskForm : {}, {}", processDefinitionId, firstTaskForm.getActivityId());
			return new com.citsh.from.FormDTO();
		}

		com.citsh.humantask.FormDTO formDTO = this.taskDefinitionConnector.findForm(firstTaskForm.getActivityId(),
				processDefinitionId);

		if (formDTO == null) {
			this.logger.info("cannot find bpmConfForm : {}, {}", processDefinitionId, firstTaskForm.getActivityId());
			return new com.citsh.from.FormDTO();
		}

		com.citsh.from.FormDTO formDto = new com.citsh.from.FormDTO();
		formDto.setProcessDefinitionId(firstTaskForm.getProcessDefinitionId());
		formDto.setActivityId(firstTaskForm.getActivityId());
		com.citsh.from.FormDTO contentFormDto = this.formConnector.findForm(formDTO.getKey(),
				processDefinition.getTenantId());
		if (contentFormDto == null) {
			this.logger.error("cannot find form : {}", formDto.getCode());
			return formDto;
		}
		formDto.setCode(formDTO.getKey());
		formDto.setRedirect(contentFormDto.isRedirect());
		formDto.setUrl(contentFormDto.getUrl());
		formDto.setContent(contentFormDto.getContent());

		return formDto;
	}

	public ProcessDTO findProcess(String processId) {
		if (processId == null) {
			this.logger.info("processId is null");
			return null;
		}
		ProcessDTO processDTO = new ProcessDTO();
		BpmProcess bpmProcess = this.bpmProcessService.findById(Long.valueOf(processId));
		String processDefinitionId = bpmProcess.getBpmConfBase().getProcessDefinitionId();
		String processDefinitionName = bpmProcess.getName();
		processDTO.setProcessDefinitionId(processDefinitionId);
		processDTO.setProcessDefinitionName(processDefinitionName);
		processDTO.setConfigTask(Integer.valueOf(1).equals(bpmProcess.getUseTaskConf()));
		return processDTO;
	}

	public String startProcess(String userId, String businessKey, String processDefinitionId,
			Map<String, Object> processParemeters) {
		IdentityService identityService = this.processEngine.getIdentityService();
		identityService.setAuthenticatedUserId(userId);

		ProcessInstance processInstance = this.processEngine.getRuntimeService()
				.startProcessInstanceById(processDefinitionId, businessKey, processParemeters);

		return processInstance.getId();
	}

    /**
     * 未结流程.
     */
	public Page findRunningProcessInstances(String userId, String tenantId, Page page) {
		HistoryService historyService = this.processEngine.getHistoryService();

		long count = historyService.createHistoricProcessInstanceQuery().processInstanceTenantId(tenantId)
				.startedBy(userId).unfinished().count();

		HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery()
				.processInstanceTenantId(tenantId).startedBy(userId).unfinished();
		if (page.getOrderBy() != null) {
			String orderBy = page.getOrderBy();
			if ("processInstanceStartTime".equals(orderBy)) {
				query.orderByProcessInstanceStartTime();
			}
			if (page.isAsc())
				query.asc();
			else {
				query.desc();
			}
		}
		List<HistoricProcessInstance> historicProcessInstances = query.listPage((int) page.getStart(),
				page.getPageSize());
		page.setResult(historicProcessInstances);
		page.setTotalCount(count);
		return page;
	}

    /**
     * 已结流程.
     */
	public Page findCompletedProcessInstances(String userId, String tenantId, Page page) {
		HistoryService historyService = this.processEngine.getHistoryService();

		long count = historyService.createHistoricProcessInstanceQuery().processInstanceTenantId(tenantId)
				.startedBy(userId).finished().count();

		List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery()
				.processInstanceTenantId(tenantId).startedBy(userId).finished()
				.listPage((int) page.getStart(), page.getPageSize());
		page.setResult(historicProcessInstances);
		page.setTotalCount(count);
		return page;
	}

    /**
     * 参与流程.
     */
	public Page findInvolvedProcessInstances(String userId, String tenantId, Page page) {
		HistoryService historyService = this.processEngine.getHistoryService();

		long count = historyService.createHistoricProcessInstanceQuery().processInstanceTenantId(tenantId)
				.involvedUser(userId).count();

		List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery()
				.processInstanceTenantId(tenantId).involvedUser(userId)
				.listPage((int) page.getStart(), page.getPageSize());
		page.setResult(historicProcessInstances);
		page.setTotalCount(count);
		return page;
	}

    /**
     * 待办任务（个人任务）.
     */
	public Page findPersonalTasks(String userId, String tenantId, Page page) {
		TaskService taskService = this.processEngine.getTaskService();
		long count = taskService.createTaskQuery().taskTenantId(tenantId).taskAssignee(userId).active().count();

		List<Task> tasks = taskService.createTaskQuery().taskTenantId(tenantId).taskAssignee(userId).active()
				.listPage((int) page.getStart(), page.getPageSize());
		page.setResult(tasks);
		page.setTotalCount(count);
		return page;
	}

    /**
     * 代领任务（组任务）.
     */
	public Page findGroupTasks(String userId, String tenantId, Page page) {
		TaskService taskService = this.processEngine.getTaskService();
		long count = taskService.createTaskQuery().taskTenantId(tenantId).taskCandidateUser(userId).active().count();

		List<Task> tasks = taskService.createTaskQuery().taskTenantId(tenantId).taskCandidateUser(userId).active()
				.listPage((int) page.getStart(), page.getPageSize());
		page.setResult(tasks);
		page.setTotalCount(count);
		return page;
	}

    /**
     * 已办任务（历史任务）.
     */
	public Page findHistoryTasks(String userId, String tenantId, Page page) {
		HistoryService historyService = this.processEngine.getHistoryService();

		long count = historyService.createHistoricTaskInstanceQuery().taskTenantId(tenantId).taskAssignee(userId)
				.finished().count();

		List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
				.taskTenantId(tenantId).taskAssignee(userId).finished()
				.listPage((int) page.getStart(), page.getPageSize());
		page.setResult(historicTaskInstances);
		page.setTotalCount(count);
		return page;
	}

    /**
     * 代理中的任务（代理人还未完成该任务）.
     */
	public Page findDelegatedTasks(String userId, String tenantId, Page page) {
		TaskService taskService = this.processEngine.getTaskService();

		long count = taskService.createTaskQuery().taskTenantId(tenantId).taskOwner(userId)
				.taskDelegationState(DelegationState.PENDING).count();

		List<Task> tasks = taskService.createTaskQuery().taskTenantId(tenantId).taskOwner(userId)
				.taskDelegationState(DelegationState.PENDING).listPage((int) page.getStart(), page.getPageSize());
		page.setResult(tasks);
		page.setTotalCount(count);
		return page;
	}

    /**
     * 同时返回已领取和未领取的任务.
     */
	public Page findCandidateOrAssignedTasks(String userId, String tenantId, Page page) {
		TaskService taskService = this.processEngine.getTaskService();
		long count = taskService.createTaskQuery().taskTenantId(tenantId).taskCandidateOrAssigned(userId).count();

		List<Task> tasks = taskService.createTaskQuery().taskTenantId(tenantId).taskCandidateOrAssigned(userId)
				.listPage((int) page.getStart(), page.getPageSize());
		page.setResult(tasks);
		page.setTotalCount(count);
		return page;
	}

    /**
     * 流程定义.
     */
	public Page findProcessDefinitions(String tenantId, Page page) {
		RepositoryService repositoryService = this.processEngine.getRepositoryService();
		long count = repositoryService.createProcessDefinitionQuery().processDefinitionTenantId(tenantId).count();

		List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
				.processDefinitionTenantId(tenantId).listPage((int) page.getStart(), page.getPageSize());
		page.setResult(processDefinitions);
		page.setTotalCount(count);
		return page;
	}

    /**
     * 流程实例.
     */
	public Page findProcessInstances(String tenantId, Page page) {
		RuntimeService runtimeService = this.processEngine.getRuntimeService();
		long count = runtimeService.createProcessInstanceQuery().processInstanceTenantId(tenantId).count();

		List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery()
				.processInstanceTenantId(tenantId).listPage((int) page.getStart(), page.getPageSize());
		page.setResult(processInstances);
		page.setTotalCount(count);
		return page;
	}

    /**
     * 任务.
     */
	public Page findTasks(String tenantId, Page page) {
		TaskService taskService = this.processEngine.getTaskService();
		long count = taskService.createTaskQuery().taskTenantId(tenantId).count();
		List<Task> tasks = taskService.createTaskQuery().taskTenantId(tenantId).listPage((int) page.getStart(),
				page.getPageSize());
		page.setResult(tasks);
		page.setTotalCount(count);
		return page;
	}

    /**
     * 部署.
     */
	public Page findDeployments(String tenantId, Page page) {
		RepositoryService repositoryService = this.processEngine.getRepositoryService();
		long count = repositoryService.createDeploymentQuery().deploymentTenantId(tenantId).count();

		List<Deployment> deployments = repositoryService.createDeploymentQuery().deploymentTenantId(tenantId)
				.listPage((int) page.getStart(), page.getPageSize());
		page.setResult(deployments);
		page.setTotalCount(count);
		return page;
	}

    /**
     * 历史流程实例.
     */
	public Page findHistoricProcessInstances(String tenantId, Page page) {
		HistoryService historyService = this.processEngine.getHistoryService();
		long count = historyService.createHistoricProcessInstanceQuery().processInstanceTenantId(tenantId).count();

		List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery()
				.processInstanceTenantId(tenantId).listPage((int) page.getStart(), page.getPageSize());
		page.setResult(historicProcessInstances);
		page.setTotalCount(count);
		return page;
	}

    /**
     * 历史节点.
     */
	public Page findHistoricActivityInstances(String tenantId, Page page) {
		HistoryService historyService = this.processEngine.getHistoryService();
		long count = historyService.createHistoricActivityInstanceQuery().activityTenantId(tenantId).count();

		List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
				.activityTenantId(tenantId).listPage((int) page.getStart(), page.getPageSize());
		page.setResult(historicActivityInstances);
		page.setTotalCount(count);
		return page;
	}

    /**
     * 历史任务.
     */
	public Page findHistoricTaskInstances(String tenantId, Page page) {
		HistoryService historyService = this.processEngine.getHistoryService();
		long count = ((HistoricTaskInstanceQuery) historyService.createHistoricTaskInstanceQuery()
				.taskTenantId(tenantId)).count();

		List<HistoricTaskInstance> historicTaskInstances = ((HistoricTaskInstanceQuery) historyService
				.createHistoricTaskInstanceQuery().taskTenantId(tenantId)).listPage((int) page.getStart(),
						page.getPageSize());
		page.setResult(historicTaskInstances);
		page.setTotalCount(count);
		return page;
	}

    /**
     * 作业.
     */
	public Page findJobs(String tenantId, Page page) {
		ManagementService managementService = this.processEngine.getManagementService();
		long count = managementService.createJobQuery().jobTenantId(tenantId).count();
		List<Job> jobs = managementService.createJobQuery().jobTenantId(tenantId).listPage((int) page.getStart(),
				page.getPageSize());
		page.setResult(jobs);
		page.setTotalCount(count);
		return page;
	}

	public com.citsh.from.FormDTO findStartEventForm(FirstTaskForm firstTaskForm) {
		ProcessDefinition processDefinition = (ProcessDefinition) this.processEngine.getRepositoryService()
				.createProcessDefinitionQuery().processDefinitionId(firstTaskForm.getProcessDefinitionId())
				.singleResult();
		List<BpmConfForm> bpmConfForms = this.bpmConfFormService.listBySQL(
				" bpmConfNode.bpmConfBase.processDefinitionId=? and bpmConfNode.code=?",
				new Object[] { firstTaskForm.getProcessDefinitionId(), firstTaskForm.getActivityId() });
		com.citsh.from.FormDTO formDTO = new com.citsh.from.FormDTO();
		formDTO.setProcessDefinitionId(firstTaskForm.getProcessDefinitionId());
		formDTO.setActivityId(firstTaskForm.getActivityId());
		formDTO.setCode(firstTaskForm.getFormKey());

		if (!bpmConfForms.isEmpty()) {
			BpmConfForm bpmConfForm = (BpmConfForm) bpmConfForms.get(0);
			if (!Integer.valueOf(2).equals(bpmConfForm.getStatus()))
				if (Integer.valueOf(1).equals(bpmConfForm.getType())) {
					formDTO.setRedirect(true);
					formDTO.setUrl(bpmConfForm.getValue());
				} else {
					formDTO.setCode(bpmConfForm.getValue());
				}
		} else {
			this.logger.info("cannot find bpmConfForm : {}, {}", firstTaskForm.getProcessDefinitionId(),
					formDTO.getActivityId());
		}
		com.citsh.from.FormDTO dto = this.formConnector.findForm(formDTO.getCode(), processDefinition.getTenantId());
		if (dto == null) {
			this.logger.error("cannot find form : {}", formDTO.getCode());
			return formDTO;
		}
		formDTO.setRedirect(dto.isRedirect());
		formDTO.setUrl(dto.getUrl());
		formDTO.setContent(dto.getContent());
		return formDTO;
	}
}