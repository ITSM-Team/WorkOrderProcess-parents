package com.citsh.humantask.service.impl;

import com.citsh.activiti.service.InternalProcessService;
import com.citsh.base.entity.TaskConfUser;
import com.citsh.base.service.TaskConfUserService;
import com.citsh.base.service.TaskDefinitionService;
import com.citsh.enumitem.DelegateStatus;
import com.citsh.enumitem.HumanTaskStatus;
import com.citsh.from.FormConnector;
import com.citsh.humantask.HumanTaskDTO;
import com.citsh.humantask.HumanTaskDefinition;
import com.citsh.humantask.ParticipantDTO;
import com.citsh.humantask.entity.TaskDeadline;
import com.citsh.humantask.entity.TaskInfo;
import com.citsh.humantask.entity.TaskParticipant;
import com.citsh.humantask.service.HumanTaskService;
import com.citsh.humantask.service.TaskDeadlineService;
import com.citsh.humantask.service.TaskInfoService;
import com.citsh.humantask.service.TaskLogService;
import com.citsh.humantask.service.TaskParticipantService;
import com.citsh.id.IdGenerator;
import com.citsh.listener.service.ListenerService;
import com.citsh.page.Page;
import com.citsh.process.ProcessTaskDefinition;
import com.citsh.util.BeanMapper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Transactional
@Service
public class HumanTaskServiceImpl implements HumanTaskService {
	private static Logger logger = LoggerFactory.getLogger(HumanTaskServiceImpl.class);

	@Autowired
	private TaskInfoService taskInfoService;

	@Autowired
	private TaskDeadlineService taskDeadlineService;

	@Autowired
	private TaskLogService taskLogService;
	private BeanMapper beanMapper = new BeanMapper();

	@Autowired
	private IdGenerator idGenerator;

	@Autowired
	private List<ListenerService> listenerServices;

	@Autowired
	private InternalProcessService internalProcessService;

	@Autowired
	private TaskDefinitionService taskDefinitionService;

	@Autowired
	private TaskParticipantService taskParticipantService;

	@Autowired
	private FormConnector formConnector;

	@Autowired
	private TaskConfUserService taskConfUserService;

	public HumanTaskDTO createHumanTask() {
		HumanTaskDTO humanTaskDto = new HumanTaskDTO();
		humanTaskDto.setDelegateStatus("none");
		humanTaskDto.setCreateTime(new Date());
		humanTaskDto.setSuspendStatus("none");
		humanTaskDto.setStatus("active");
		return humanTaskDto;
	}

	public void removeHumanTask(String humanTaskId) {
		TaskInfo taskInfo = this.taskInfoService.findById(Long.valueOf(humanTaskId));
		removeHumanTask(taskInfo);
	}

	public void removeHumanTaskByTaskId(String taskId) {
		TaskInfo taskInfo = this.taskInfoService.listBySQLOne("taskId=?", new Object[] { taskId });
		removeHumanTask(taskInfo);
	}

	public void removeHumanTaskByProcessInstanceId(String processInstanceId) {
		List<TaskInfo> taskInfos = this.taskInfoService.listBySQL("status='active' and processInstanceId=?",
				new Object[] { processInstanceId });

		for (TaskInfo taskInfo : taskInfos)
			removeHumanTask(taskInfo);
	}

	public HumanTaskDTO saveHumanTask(HumanTaskDTO humanTaskDto) {
		return saveHumanTask(humanTaskDto, true);
	}

	public HumanTaskDTO saveHumanTaskAndProcess(HumanTaskDTO humanTaskDto) {
		return saveHumanTask(humanTaskDto, true);
	}

	public void completeTask(String humanTaskId, String userId, String action, String comment,
			Map<String, Object> taskParameters) {
		Assert.hasText(humanTaskId, "humanTaskId不能为空");
		logger.info("completeTask humanTaskId : {}, userId : {}, comment: {}",
				new Object[] { humanTaskId, userId, comment });
		HumanTaskDTO humanTaskDTO = findHumanTask(humanTaskId);
		if (humanTaskDTO == null) {
			throw new IllegalStateException("任务不存在");
		}
		humanTaskDTO.setStatus("complete");
		humanTaskDTO.setCompleteTime(new Date());
		humanTaskDTO.setAction("完成");
		if (StringUtils.isNotBlank(action)) {
			humanTaskDTO.setAction(action);
		}
		if (StringUtils.isNotBlank(comment)) {
			humanTaskDTO.setComment(comment);
		}
		Long taskId = Long.valueOf(humanTaskDTO.getId());
		List<TaskDeadline> taskDeadlines = this.taskDeadlineService.listBySQL(" taskInfo.id=?",
				new Object[] { taskId });
		if (taskDeadlines.size() > 0) {
			this.taskDeadlineService.removeAll(taskDeadlines);
		}

		if ("copy".equals(humanTaskDTO.getCategory())) {
			humanTaskDTO.setStatus("complete");
			humanTaskDTO.setCompleteTime(new Date());
			humanTaskDTO.setAction("完成");
			saveHumanTask(humanTaskDTO, false);

			return;
		}

		if ("startEvent".equals(humanTaskDTO.getCategory())) {
			humanTaskDTO.setStatus("complete");
			humanTaskDTO.setAction("提交");
			humanTaskDTO.setCompleteTime(new Date());
			saveHumanTask(humanTaskDTO, false);
			this.internalProcessService.signalExecution(humanTaskDTO.getExecutionId());
			return;
		}
		logger.debug("{}", humanTaskDTO.getDelegateStatus());
		if ("pending".equals(humanTaskDTO.getDelegateStatus())) {
			humanTaskDTO.setStatus("active");
			humanTaskDTO.setDelegateStatus("resolved");
			humanTaskDTO.setAssignee(humanTaskDTO.getOwner());
			humanTaskDTO.setAction("完成");
			saveHumanTask(humanTaskDTO, false);
			this.internalProcessService.resolveTask(humanTaskDTO.getTaskId());
			return;
		}

		if ("pendingCreate".equals(humanTaskDTO.getDelegateStatus())) {
			humanTaskDTO.setCompleteTime(new Date());
			humanTaskDTO.setDelegateStatus("resolved");
			humanTaskDTO.setStatus("complete");
			humanTaskDTO.setAction("完成");
			saveHumanTask(humanTaskDTO, false);
			if (humanTaskDTO.getParentId() != null) {
				HumanTaskDTO targetHumanTaskDto = findHumanTask(humanTaskDTO.getParentId());
				targetHumanTaskDto.setStatus("active");
				if (targetHumanTaskDto.getParentId() == null) {
					targetHumanTaskDto.setDelegateStatus("resolved");
				}
				saveHumanTask(targetHumanTaskDto, false);
			}
			return;
		}
		saveHumanTask(humanTaskDTO, false);

		if (("vote".equals(humanTaskDTO.getCatalog())) && (humanTaskDTO.getParentId() != null)) {
			HumanTaskDTO parentTask = findHumanTask(humanTaskDTO.getParentId());
			boolean completed = true;
			for (HumanTaskDTO childTask : parentTask.getChildren()) {
				if (!"complete".equals(childTask.getStatus())) {
					completed = false;

					break;
				}
			}
			if (completed) {
				parentTask.setAssignee(parentTask.getOwner());
				parentTask.setOwner("");
				parentTask.setStatus("complete");
				parentTask.setCompleteTime(new Date());
				parentTask.setAction("完成");
				saveHumanTask(parentTask, false);
				this.internalProcessService.completeTask(humanTaskDTO.getTaskId(), userId, taskParameters);
			}
		} else {
			this.internalProcessService.completeTask(humanTaskDTO.getTaskId(), userId, taskParameters);
		}
		TaskInfo taskInfo;
		if (this.listenerServices != null) {
			Long id = null;
			try {
				id = Long.valueOf(Long.parseLong(humanTaskDTO.getId()));
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}

			if (id == null) {
				return;
			}
			taskInfo = this.taskInfoService.findById(id);
			for (ListenerService listener : this.listenerServices)
				try {
					listener.onComplete(taskInfo);
				} catch (Exception ex) {
					logger.error(ex.getMessage(), ex);
				}
		}
	}

	public void claimTask(String humanTaskId, String userId) {
		TaskInfo taskInfo = this.taskInfoService.findById(Long.valueOf(humanTaskId));
		if (taskInfo.getAssignee() != null) {
			throw new IllegalStateException("task " + humanTaskId + " already be claimed by " + taskInfo.getAssignee());
		}
		taskInfo.setAssignee(userId);
		this.taskInfoService.save(taskInfo);
	}

	public void releaseTask(String humanTaskId, String comment) {
		TaskInfo taskInfo = this.taskInfoService.findById(Long.valueOf(humanTaskId));
		taskInfo.setAssignee(null);
		this.taskInfoService.save(taskInfo);
	}

	public void transfer(String humanTaskId, String userId, String comment) {
		HumanTaskDTO humanTaskDTO = findHumanTask(humanTaskId);
		humanTaskDTO.setOwner(humanTaskDTO.getAssignee());
		humanTaskDTO.setAssignee(userId);
		saveHumanTask(humanTaskDTO, false);
		this.internalProcessService.transfer(humanTaskDTO.getTaskId(), humanTaskDTO.getAssignee(),
				humanTaskDTO.getOwner());
	}

	public void cancel(String humanTaskId, String userId, String comment) {
		HumanTaskDTO humanTaskDTO = findHumanTask(humanTaskId);
		humanTaskDTO.setAssignee(humanTaskDTO.getOwner());
		humanTaskDTO.setOwner("");
		saveHumanTask(humanTaskDTO, false);
	}

	public void rollbackActivity(String humanTaskId, String activityId, String comment) {
		HumanTaskDTO humanTaskDTO = findHumanTask(humanTaskId);
		if (humanTaskDTO == null) {
			throw new IllegalStateException("任务不存在");
		}
		String taskId = humanTaskDTO.getTaskId();
		this.internalProcessService.rollback(taskId, activityId, null);
	}

	public void rollbackActivityLast(String humanTaskId, String activityId, String comment) {
		HumanTaskDTO humanTaskDTO = findHumanTask(humanTaskId);
		if (humanTaskDTO == null) {
			throw new IllegalStateException("任务不存在");
		}
		String taskId = humanTaskDTO.getTaskId();
		this.internalProcessService.rollbackAuto(taskId, activityId);
	}

	public void rollbackActivityAssignee(String humanTaskId, String activityId, String userId, String comment) {
		HumanTaskDTO humanTaskDTO = findHumanTask(humanTaskId);
		if (humanTaskDTO == null) {
			throw new IllegalStateException("任务不存在");
		}
		String taskId = humanTaskDTO.getTaskId();
		this.internalProcessService.rollback(taskId, activityId, userId);
	}

	public void rollbackPrevious(String humanTaskId, String comment) {
		HumanTaskDTO humanTaskDTO = findHumanTask(humanTaskId);
		if (humanTaskDTO == null) {
			throw new IllegalStateException("任务不存在");
		}
		String taskId = humanTaskDTO.getTaskId();
		this.internalProcessService.rollback(taskId, null, null);
	}

	public void rollbackPreviousLast(String humanTaskId, String comment) {
		HumanTaskDTO humanTaskDTO = findHumanTask(humanTaskId);
		if (humanTaskDTO == null) {
			throw new IllegalStateException("任务不存在");
		}
		String taskId = humanTaskDTO.getTaskId();
		this.internalProcessService.rollbackAuto(taskId, null);
	}

	public void rollbackPreviousAssignee(String humanTaskId, String userId, String comment) {
		HumanTaskDTO humanTaskDTO = findHumanTask(humanTaskId);
		if (humanTaskDTO == null) {
			throw new IllegalStateException("任务不存在");
		}
		String taskId = humanTaskDTO.getTaskId();
		this.internalProcessService.rollback(taskId, null, userId);
	}

	public void rollbackStart(String humanTaskId, String comment) {
		HumanTaskDTO humanTaskDTO = findHumanTask(humanTaskId);
		if (humanTaskDTO == null) {
			throw new IllegalStateException("任务不存在");
		}
		String taskId = humanTaskDTO.getTaskId();
		String processInstanceId = humanTaskDTO.getProcessInstanceId();
		String processDefinitionId = humanTaskDTO.getProcessDefinitionId();
		String startEventId = this.internalProcessService.findInitialActivityId(processDefinitionId);
		String startUserId = this.internalProcessService.findInitiator(processInstanceId);
		this.internalProcessService.rollback(taskId, startEventId, startUserId);
	}

	public void rollbackInitiator(String humanTaskId, String comment) {
		HumanTaskDTO humanTaskDTO = findHumanTask(humanTaskId);
		if (humanTaskDTO == null) {
			throw new IllegalStateException("任务不存在");
		}
		humanTaskDTO.setAction("回退（发起人）");
		humanTaskDTO.setComment(comment);
		saveHumanTask(humanTaskDTO, false);
		humanTaskDTO = findHumanTask(humanTaskId);

		String taskId = humanTaskDTO.getTaskId();
		String processInstanceId = humanTaskDTO.getProcessInstanceId();
		String processDefinitionId = humanTaskDTO.getProcessDefinitionId();
		String statUserId = this.internalProcessService.findInitiator(processInstanceId);
		String startEvent = this.internalProcessService.findFirstUserTaskActivityId(processDefinitionId, statUserId);
		this.internalProcessService.rollback(taskId, startEvent, statUserId);
	}

	public void withdraw(String humanTaskId, String comment) {
		HumanTaskDTO humanTaskDTO = findHumanTask(humanTaskId);
		if (humanTaskDTO == null) {
			throw new IllegalStateException("任务不存在");
		}
		String taskId = humanTaskDTO.getTaskId();
		this.internalProcessService.withdrawTask(taskId);
	}

	public void delegateTask(String humanTaskId, String userId, String comment) {
		HumanTaskDTO humanTaskDTO = findHumanTask(humanTaskId);
		if (humanTaskDTO == null) {
			throw new IllegalStateException("任务不存在");
		}
		humanTaskDTO.setOwner(humanTaskDTO.getAssignee());
		humanTaskDTO.setAssignee(userId);
		humanTaskDTO.setDelegateStatus(DelegateStatus.getPending());
		saveHumanTask(humanTaskDTO, false);
		String taskId = humanTaskDTO.getTaskId();
		this.internalProcessService.delegateTask(taskId, userId);
	}

	public void delegateTaskCreate(String humanTaskId, String userId, String comment) {
		HumanTaskDTO humanTaskDTO = findHumanTask(humanTaskId);

		humanTaskDTO.setDelegateStatus(DelegateStatus.getPindingcreate());
		humanTaskDTO.setStatus(HumanTaskStatus.getPending());
		saveHumanTask(humanTaskDTO, false);

		HumanTaskDTO createHumanTaskDTO = createHumanTask();
		this.beanMapper.copy(humanTaskDTO, createHumanTaskDTO);
		createHumanTaskDTO.setStatus(HumanTaskStatus.getActive());
		createHumanTaskDTO.setParentId(humanTaskDTO.getId());
		createHumanTaskDTO.setId(null);
		createHumanTaskDTO.setOwner(humanTaskDTO.getAssignee());
		createHumanTaskDTO.setAssignee(userId);
		saveHumanTask(createHumanTaskDTO, false);

		if (humanTaskDTO.getParentId() == null) {
			this.internalProcessService.delegateTask(humanTaskDTO.getTaskId(), userId);
		}
	}

	public void communicate(String humanTaskId, String userId, String comment) {
		HumanTaskDTO humanTaskDTO = findHumanTask(humanTaskId);
		HumanTaskDTO createHumanTaskDTO = new HumanTaskDTO();
		this.beanMapper.copy(humanTaskDTO, createHumanTaskDTO);
		createHumanTaskDTO.setId(null);
		createHumanTaskDTO.setCatalog("communicate");
		createHumanTaskDTO.setAssignee(userId);
		createHumanTaskDTO.setMessage(comment);
		createHumanTaskDTO.setParentId(humanTaskDTO.getId());
		saveHumanTask(createHumanTaskDTO, false);
	}

	public void callback(String humanTaskId, String userId, String comment) {
		HumanTaskDTO humanTaskDTO = findHumanTask(humanTaskId);
		humanTaskDTO.setStatus(HumanTaskStatus.getComlpete());
		humanTaskDTO.setCompleteTime(new Date());
		humanTaskDTO.setAction("反馈");
		humanTaskDTO.setComment(comment);
		saveHumanTask(humanTaskDTO, false);
	}

	public void skip(String humanTaskId, String userId, String comment) {
		HumanTaskDTO humanTaskDTO = findHumanTask(humanTaskId);
		humanTaskDTO.setStatus(HumanTaskStatus.getComlpete());
		humanTaskDTO.setCompleteTime(new Date());
		humanTaskDTO.setAction("跳过");
		humanTaskDTO.setComment(comment);
		humanTaskDTO.setOwner(humanTaskDTO.getAssignee());
		humanTaskDTO.setAssignee(userId);
		saveHumanTask(humanTaskDTO, false);
		this.internalProcessService.completeTask(humanTaskDTO.getTaskId(), userId, Collections.emptyMap());
	}

	public void saveParticipant(ParticipantDTO participantDto) {
		TaskParticipant taskParticipant = new TaskParticipant();
		taskParticipant.setRef(participantDto.getCode());
		taskParticipant.setType(participantDto.getType());
		TaskInfo taskInfo = this.taskInfoService.findById(Long.valueOf(participantDto.getHumanTaskId()));
		taskParticipant.setTaskInfo(taskInfo);
		taskParticipant.setId(this.idGenerator.generateId());
		this.taskParticipantService.save(taskParticipant);
	}

	public HumanTaskDTO findHumanTaskByTaskId(String taskId) {
		TaskInfo taskInfo = this.taskInfoService.listBySQLOne("taskId=?", new Object[] { taskId });
		return convertHumanTaskDto(taskInfo);
	}

	public List<HumanTaskDTO> findHumanTasksByProcessInstanceId(String processInstanceId) {
		List<TaskInfo> taskInfos = this.taskInfoService.listBySQL(" processInstanceId=? order by createTime asc",
				new Object[] { processInstanceId });

		return convertHumanTaskDtos(taskInfos);
	}

	public HumanTaskDTO findHumanTask(String humanTaskId) {
		Assert.hasText(humanTaskId, "humanTaskId不能为空");
		TaskInfo taskInfo = this.taskInfoService.findById(Long.valueOf(humanTaskId));
		return convertHumanTaskDto(taskInfo);
	}

	public List<HumanTaskDTO> findSubTasks(String parentTaskId) {
		List<TaskInfo> taskInfos = this.taskInfoService.listBySQL("taskInfo.id",
				new Object[] { Long.valueOf(parentTaskId) });
		return convertHumanTaskDtos(taskInfos);
	}

	public com.citsh.from.FormDTO findTaskForm(String humanTaskId) {
		HumanTaskDTO humanTaskDTO = findHumanTask(humanTaskId);
		com.citsh.from.FormDTO formDTO = null;
		if (humanTaskDTO.getTaskId() != null) {
			com.citsh.humantask.FormDTO dto = this.taskDefinitionService.findForm(humanTaskDTO.getCode(),
					humanTaskDTO.getProcessDefinitionId());
			if (dto == null) {
				logger.info("cannot find form by code : {}, processDefinition : {}", humanTaskDTO.getCode(),
						humanTaskDTO.getProcessDefinitionId());
			} else {
				formDTO = new com.citsh.from.FormDTO();
				formDTO.setCode(dto.getKey());
				List<String> operations = this.taskDefinitionService.findOperations(formDTO.getCode(),
						humanTaskDTO.getProcessDefinitionId());
				formDTO.setButtons(operations);
				formDTO.setActivityId(humanTaskDTO.getCode());
				formDTO.setProcessDefinitionId(humanTaskDTO.getProcessDefinitionId());
			}
		} else {
			formDTO = new com.citsh.from.FormDTO();
			formDTO.setCode(humanTaskDTO.getForm());
			formDTO.setActivityId(humanTaskDTO.getCode());
			formDTO.setProcessDefinitionId(humanTaskDTO.getProcessDefinitionId());
		}
		if (formDTO == null) {
			logger.error("cannot find form : {}", humanTaskId);
			return new com.citsh.from.FormDTO();
		}
		formDTO.setTaskId(humanTaskId);
		com.citsh.from.FormDTO dto = this.formConnector.findForm(formDTO.getCode(), formDTO.getTaskId());
		if (dto == null) {
			logger.error("cannot find form : {}", formDTO.getCode());
			return formDTO;
		}
		formDTO.setRedirect(dto.isRedirect());
		formDTO.setUrl(dto.getUrl());
		formDTO.setContent(dto.getContent());
		return formDTO;
	}

	public List<HumanTaskDefinition> findHumanTaskDefinitions(String processDefinitionId) {
		List<ProcessTaskDefinition> taskDefinitions = this.internalProcessService
				.findTaskDefinitions(processDefinitionId);
		List<HumanTaskDefinition> humanTaskDefinitions = new ArrayList<HumanTaskDefinition>();
		for (ProcessTaskDefinition taskDefinition : taskDefinitions) {
			HumanTaskDefinition humanTaskDefinition = new HumanTaskDefinition();
			this.beanMapper.copy(taskDefinition, humanTaskDefinition);
			humanTaskDefinitions.add(humanTaskDefinition);
		}
		return humanTaskDefinitions;
	}

	public void configTaskDefinitions(String businessKey, List<String> taskDefinitionKeys, List<String> taskAssigness) {
		if (taskDefinitionKeys == null) {
			return;
		}
		int index = 0;
		for (String taskDefinitionKey : taskDefinitionKeys) {
			String taskAssignee = (String) taskAssigness.get(index++);
			TaskConfUser taskConfUser = this.taskConfUserService.findBySQL(" businessKey=? and code=?",
					new Object[] { businessKey, taskDefinitionKey });

			if (taskConfUser == null) {
				taskConfUser = new TaskConfUser();
			}
			taskConfUser.setBusinessKey(businessKey);
			taskConfUser.setCode(taskDefinitionKey);
			taskConfUser.setValue(taskAssignee);
			taskConfUser.setId(this.idGenerator.generateId());
			this.taskConfUserService.save(taskConfUser);
		}
	}

	public Page findPersonalTasks(String userId, String tenantId, int pageNo, int pageSize) {
		Page page = this.taskInfoService.pageQuery(" assignee=? and tenantId=? and status='active' ", pageNo, pageSize,
				new Object[] { userId, tenantId });

		List taskInfos = (List) page.getResult();
		List humanTaskDTOs = convertHumanTaskDtos(taskInfos);
		page.setResult(humanTaskDTOs);
		return page;
	}

	public Page findFinishedTasks(String userId, String tenantId, int pageNo, int pageSize) {
		Page page = this.taskInfoService.pageQuery(" assignee=? and tenantId=? and status='complete' ", pageNo,
				pageSize, new Object[] { userId, tenantId });

		List taskInfos = (List) page.getResult();
		List humanTaskDTOs = convertHumanTaskDtos(taskInfos);
		page.setResult(humanTaskDTOs);
		return page;
	}

	public Page findGroupTasks(String userId, String tenantId, int pageNo, int pageSize) {
		List partyIds = new ArrayList();
		partyIds.addAll(findGroupIds(userId));
		partyIds.add(userId);
		logger.debug("party ids : {}", partyIds);
		if (partyIds.isEmpty()) {
			return new Page();
		}
		Map map = new HashMap();
		map.put("partyIds", partyIds);
		map.put("tenantId", tenantId);
		Page page = this.taskInfoService.pageQuery(pageNo, pageSize, map);
		List taskInfos = (List) page.getResult();
		List humanTaskDtos = convertHumanTaskDtos(taskInfos);
		page.setResult(humanTaskDtos);

		return page;
	}

	public Page findDelegateTasks(String userId, String tenantId, int pageNo, int pageSize) {
		Page page = this.taskInfoService.pageQuery("  owner=? and tenantId=? and status='active' ", pageNo, pageSize,
				new Object[] { userId, tenantId });

		List taskInfos = (List) page.getResult();
		List humanTaskDTOs = convertHumanTaskDtos(taskInfos);
		page.setResult(humanTaskDTOs);
		return page;
	}

	public void removeHumanTask(TaskInfo taskInfo) {
		if (taskInfo == null) {
			logger.info("!!!caveat removeHumanTask  param taskInfo is null ");
			return;
		}
		this.taskDeadlineService.removeAll(taskInfo.getTaskDeadlines());
		this.taskLogService.removeAll(taskInfo.getTaskLogs());
		this.taskInfoService.remove(taskInfo.getId());
	}

	public HumanTaskDTO saveHumanTask(HumanTaskDTO humanTaskDto, boolean triggerListener) {
		Long id = null;
		if (humanTaskDto.getId() != null) {
			try {
				id = Long.valueOf(Long.parseLong(humanTaskDto.getId()));
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
		TaskInfo taskInfo = new TaskInfo();
		if (id != null) {
			taskInfo = this.taskInfoService.findById(id);
		}
		this.beanMapper.copy(humanTaskDto, taskInfo, HumanTaskDTO.class, TaskInfo.class);
		logger.debug("action : {}", humanTaskDto.getAction());
		logger.debug("comment : {}", humanTaskDto.getComment());
		logger.debug("action : {}", taskInfo.getAction());
		logger.debug("comment : {}", taskInfo.getComment());
		TaskInfo parentTaskInfo;
		if (humanTaskDto.getParentId() != null) {
			parentTaskInfo = this.taskInfoService.findById(Long.valueOf(humanTaskDto.getParentId()));
			taskInfo.setTaskInfo(parentTaskInfo);
		}
		if (triggerListener) {
			if ((id == null) && (this.listenerServices != null)) {
				try {
					for (ListenerService listenerService : this.listenerServices)
						listenerService.onCreate(taskInfo);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			humanTaskDto.setAssignee(taskInfo.getAssignee());
			humanTaskDto.setOwner(taskInfo.getOwner());
		}

		Long taskInfoID = taskInfo.getId();
		if (taskInfoID == null) {
			taskInfo.setId(this.idGenerator.generateId());
		}
		this.taskInfoService.save(taskInfo);
		humanTaskDto.setId(String.valueOf(taskInfo.getId()));
		return humanTaskDto;
	}

	public HumanTaskDTO convertHumanTaskDto(TaskInfo taskInfo) {
		if (taskInfo == null) {
			return null;
		}
		HumanTaskDTO humanTaskDTO = new HumanTaskDTO();
		this.beanMapper.copy(taskInfo, humanTaskDTO);
		if (taskInfo.getTaskInfo() != null) {
			humanTaskDTO.setParentId(String.valueOf(taskInfo.getTaskInfo().getId()));
		}
		if (taskInfo.getTaskInfos().isEmpty()) {
			List children = convertHumanTaskDtos(taskInfo.getTaskInfos());
			humanTaskDTO.setChildren(children);
		}
		return humanTaskDTO;
	}

	public List<HumanTaskDTO> convertHumanTaskDtos(Collection<TaskInfo> taskInfos) {
		List humanTaskDtos = new ArrayList();
		for (TaskInfo taskInfo : taskInfos) {
			humanTaskDtos.add(convertHumanTaskDto(taskInfo));
		}
		return humanTaskDtos;
	}

	public List<String> findGroupIds(String userId) {
		List partyIds = new ArrayList();
		return partyIds;
	}
}