package com.citsh.base.service.impl;

import com.citsh.base.entity.TaskConfUser;
import com.citsh.base.entity.TaskDefBase;
import com.citsh.base.entity.TaskDefDeadline;
import com.citsh.base.entity.TaskDefNotification;
import com.citsh.base.entity.TaskDefOperation;
import com.citsh.base.entity.TaskDefUser;
import com.citsh.base.service.TaskConfUserService;
import com.citsh.base.service.TaskDefBaseService;
import com.citsh.base.service.TaskDefDeadlineService;
import com.citsh.base.service.TaskDefNotificationService;
import com.citsh.base.service.TaskDefOperationService;
import com.citsh.base.service.TaskDefUserService;
import com.citsh.base.service.TaskDefinitionService;
import com.citsh.humantask.CounterSignDTO;
import com.citsh.humantask.DeadlineDTO;
import com.citsh.humantask.FormDTO;
import com.citsh.humantask.TaskDefinitionDTO;
import com.citsh.humantask.TaskNotificationDTO;
import com.citsh.humantask.TaskUserDTO;
import com.citsh.id.IdGenerator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskDefinitionServiceImpl implements TaskDefinitionService {
	private static Logger logger = LoggerFactory.getLogger(TaskDefinitionServiceImpl.class);

	@Autowired
	private TaskDefBaseService taskDefBaseService;

	@Autowired
	private TaskDefOperationService taskDefOperationService;

	@Autowired
	private TaskDefUserService taskDefUserService;

	@Autowired
	private TaskDefDeadlineService taskDefDeadlineService;

	@Autowired
	private TaskConfUserService taskConfUserService;

	@Autowired
	private TaskDefNotificationService taskDefNotificationService;

	@Autowired
	private IdGenerator idGenerator;

	public String findTaskAssignStrategy(String taskDefinitionKey, String processDefinitionId) {
		TaskDefBase taskDefBase = this.taskDefBaseService.findByCodeAndProIdOne("code=? and processDefinitionId=?",
				new Object[] { taskDefinitionKey, processDefinitionId });

		if (taskDefBase == null) {
			return null;
		}
		return taskDefBase.getAssignStrategy();
	}

	public CounterSignDTO findCounterSign(String taskDefinitionKey, String processDefinitionId) {
		TaskDefBase taskDefBase = this.taskDefBaseService.findByCodeAndProIdOne("code=? and processDefinitionId=?",
				new Object[] { taskDefinitionKey, processDefinitionId });

		if (taskDefBase == null) {
			return null;
		}
		CounterSignDTO counterSignDto = new CounterSignDTO();
		counterSignDto.setType(taskDefBase.getCountersignType());
		counterSignDto.setParticipants(taskDefBase.getCountersignUser());
		counterSignDto.setStrategy(taskDefBase.getCountersignStrategy());
		counterSignDto.setRate(taskDefBase.getCountersignRate().intValue());
		return counterSignDto;
	}

	public FormDTO findForm(String taskDefinitionKey, String processDefinitionId) {
		TaskDefBase taskDefBase = this.taskDefBaseService.findByCodeAndProIdOne("code=? and processDefinitionId=?",
				new Object[] { taskDefinitionKey, processDefinitionId });

		if (taskDefBase == null) {
			return null;
		}
		if (taskDefBase.getFormType() == null) {
			return null;
		}
		FormDTO formDto = new FormDTO();
		formDto.setType(taskDefBase.getFormType());
		formDto.setKey(taskDefBase.getFormKey());
		return formDto;
	}

	public List<String> findOperations(String taskDefinitionKey, String processDefinitionId) {
		List<TaskDefOperation> taskDefOperations = this.taskDefOperationService.findByBaseCodeAndBaseProcessId(
				"taskDefBase.code=? and taskDefBase.processDefinitionId=?",
				new Object[] { taskDefinitionKey, processDefinitionId });

		if (taskDefOperations.isEmpty()) {
			return Collections.emptyList();
		}
		List<String> list = new ArrayList<String>();
		for (TaskDefOperation taskDefOperation : taskDefOperations) {
			list.add(taskDefOperation.getValue());
		}
		return list;
	}

	public List<TaskUserDTO> findTaskUsers(String taskDefinitionKey, String processDefinitionId) {
		List<TaskDefUser> taskDefUsers = this.taskDefUserService.findByBaseCodeAndBaseProcessId(
				" taskDefBase.code=? and taskDefBase.processDefinitionId=?",
				new Object[] { taskDefinitionKey, processDefinitionId });

		if (taskDefUsers.isEmpty()) {
			return Collections.emptyList();
		}
		List<TaskUserDTO> taskUserDtos = new ArrayList<TaskUserDTO>();
		for (TaskDefUser taskDefUser : taskDefUsers) {
			if ("disable".equals(taskDefUser.getStatus())) {
				continue;
			}
			TaskUserDTO taskUserDto = new TaskUserDTO();
			taskUserDto.setCatalog(taskDefUser.getCatalog());
			taskUserDto.setType(taskDefUser.getType());
			taskUserDto.setValue(taskDefUser.getValue());
			taskUserDtos.add(taskUserDto);
		}
		return taskUserDtos;
	}

	public List<DeadlineDTO> findDeadlines(String taskDefinitionKey, String processDefinitionId) {
		List<TaskDefDeadline> taskDefDeadlines = this.taskDefDeadlineService.findByBaseCodeAndBaseProcessId(
				"taskDefBase.code=? and taskDefBase.processDefinitionId=?",
				new Object[] { taskDefinitionKey, processDefinitionId });

		if (taskDefDeadlines.isEmpty()) {
			return Collections.emptyList();
		}
		List<DeadlineDTO> deadlines = new ArrayList<DeadlineDTO>();
		for (TaskDefDeadline taskDefDeadline : taskDefDeadlines) {
			DeadlineDTO deadline = new DeadlineDTO();
			deadline.setType(taskDefDeadline.getType());
			deadline.setDuration(taskDefDeadline.getDuration());
			deadline.setNotificationType(taskDefDeadline.getNotificationType());
			deadline.setNotificationReceiver(taskDefDeadline.getNotificationReceiver());
			deadline.setNotificationTemplateCode(taskDefDeadline.getNotificationTemplateCode());
			deadlines.add(deadline);
		}
		return deadlines;
	}

	public String findTaskConfUser(String taskDefinitionKey, String businessKey) {
		TaskConfUser taskConfUser = this.taskConfUserService.findByCodeAndProIdOne(" code=? and businessKey=?",
				new Object[] { taskDefinitionKey, businessKey });

		if (taskConfUser == null) {
			return null;
		}

		return taskConfUser.getValue();
	}

	public List<TaskNotificationDTO> findTaskNotifications(String taskDefinitionKey, String processDefinitionId,
			String eventName) {
		List<TaskDefNotification> taskDefNotifications = this.taskDefNotificationService.findByBaseCodeAndBaseProcessId(
				" taskDefBase.code=? and taskDefBase.processDefinitionId=? and eventName=?",
				new Object[] { taskDefinitionKey, processDefinitionId, eventName });

		if (taskDefNotifications.isEmpty()) {
			return Collections.emptyList();
		}
		List<TaskNotificationDTO> taskNotifications = new ArrayList<TaskNotificationDTO>();
		for (TaskDefNotification taskDefNotification : taskDefNotifications) {
			TaskNotificationDTO taskNotification = new TaskNotificationDTO();
			taskNotification.setEventName(eventName);
			taskNotification.setType(taskDefNotification.getType());
			taskNotification.setReceiver(taskDefNotification.getReceiver());
			taskNotification.setTemplateCode(taskDefNotification.getTemplateCode());
			taskNotifications.add(taskNotification);
		}
		return taskNotifications;
	}

	public void create(TaskDefinitionDTO taskDefinition) {
		logger.info("create task definition : {}", taskDefinition.getCode());
		String processDefinitionId = taskDefinition.getProcessDefinitionId();
		String processDefinitionKey = processDefinitionId.split("\\:")[0];
		int processDefinitionVersion = Integer.parseInt(processDefinitionId.split("\\:")[1]);
		TaskDefBase taskDefBase = this.taskDefBaseService.findByCodeAndProIdOne(
				"code=? and processDefinitionKey=? and processDefinitionVersion=?", new Object[] {
						taskDefinition.getCode(), processDefinitionKey, Integer.valueOf(processDefinitionVersion) });

		if (taskDefBase == null) {
			taskDefBase = new TaskDefBase();
			taskDefBase.setId(this.idGenerator.generateId());
			taskDefBase.setCode(taskDefinition.getCode());
			taskDefBase.setProcessDefinitionKey(processDefinitionKey);
			taskDefBase.setProcessDefinitionVersion(Integer.valueOf(processDefinitionVersion));
		}
		if (taskDefBase.getProcessDefinitionId() == null) {
			taskDefBase.setProcessDefinitionId(processDefinitionId);
		}
		taskDefBase.setName(taskDefinition.getName());
		taskDefBase.setAssignStrategy(taskDefinition.getAssignStrategy());

		if (taskDefinition.getForm() != null) {
			taskDefBase.setFormType(taskDefinition.getForm().getType());
			taskDefBase.setFormKey(taskDefinition.getForm().getKey());
		}
		if (taskDefinition.getCounterSign() != null) {
			taskDefBase.setCountersignType(taskDefinition.getCounterSign().getType());
			taskDefBase.setCountersignUser(taskDefinition.getCounterSign().getParticipants());
			taskDefBase.setCountersignStrategy(taskDefinition.getCounterSign().getStrategy());
			taskDefBase.setCountersignRate(Integer.valueOf(taskDefinition.getCounterSign().getRate()));
		}
		this.taskDefBaseService.save(taskDefBase);
		for (TaskUserDTO taskUser : taskDefinition.getTaskUsers()) {
			String value = taskUser.getValue();
			String type = taskUser.getType();
			String catalog = taskUser.getCatalog();
			if (value == null) {
				logger.info("skip : {} {} {}", new Object[] { value, type, catalog });
				continue;
			}
			TaskDefUser taskDefUser = this.taskDefUserService.findByBaseCodeAndBaseProcessIdOne(
					"taskDefBase=? and value=? and type=? and catalog=?",
					new Object[] { taskDefBase, value, type, catalog });

			if (taskDefUser != null) {
				continue;
			}
			taskDefUser = new TaskDefUser();
			taskDefUser.setId(this.idGenerator.generateId());
			taskDefUser.setType(type);
			taskDefUser.setCatalog(catalog);
			taskDefUser.setValue(value);
			taskDefUser.setTaskDefBase(taskDefBase);
			this.taskDefUserService.save(taskDefUser);
		}
	}

	public void saveAssignStrategy(String taskDefinitionKey, String processDefinitoinId, String assigneeStrategy) {
		TaskDefBase taskDefBase = this.taskDefBaseService.findByCodeAndProIdOne("code=? and processDefinitionId=?",
				new Object[] { taskDefinitionKey, processDefinitoinId });

		if (taskDefBase == null) {
			return;
		}
		taskDefBase.setAssignStrategy(assigneeStrategy);
		this.taskDefBaseService.save(taskDefBase);
	}

	public void saveCounterSign(String taskDefinitionKey, String processDefinitionId, CounterSignDTO counterSign) {
		TaskDefBase taskDefBase = this.taskDefBaseService.findByCodeAndProIdOne("code=? and processDefinitionId=?",
				new Object[] { taskDefinitionKey, processDefinitionId });

		if (taskDefBase == null) {
			return;
		}
		if (counterSign.getStrategy() != null) {
			taskDefBase.setCountersignStrategy(counterSign.getStrategy());
		}
		if (counterSign.getRate() != 0) {
			taskDefBase.setCountersignRate(Integer.valueOf(counterSign.getRate()));
		}
		this.taskDefBaseService.save(taskDefBase);
	}

	public void saveForm(String taskDefinitionKey, String processDefinitionId, FormDTO form) {
		TaskDefBase taskDefBase = this.taskDefBaseService.findByCodeAndProIdOne("code=? and processDefinitionId=?",
				new Object[] { taskDefinitionKey, processDefinitionId });

		if (taskDefBase == null) {
			return;
		}
		if (form.getKey() != null) {
			taskDefBase.setFormKey(form.getKey());
		}
		if (form.getType() != null) {
			taskDefBase.setFormType(form.getType());
		}
		this.taskDefBaseService.save(taskDefBase);
	}

	public void addOperation(String taskDefinitionKey, String processDefinitionId, String operation) {
		TaskDefBase taskDefBase = this.taskDefBaseService.findByCodeAndProIdOne("code=? and processDefinitionId=?",
				new Object[] { taskDefinitionKey, processDefinitionId });

		if (taskDefBase == null) {
			return;
		}

		TaskDefOperation taskDefOperation = this.taskDefOperationService.findByBaseCodeAndBaseProcessIdOne(
				" taskDefBase=? and value=?", new Object[] { taskDefBase, operation });

		if (taskDefOperation != null) {
			return;
		}
		taskDefOperation = new TaskDefOperation();
		taskDefOperation.setId(this.idGenerator.generateId());
		taskDefOperation.setTaskDefBase(taskDefBase);
		taskDefOperation.setValue(operation);
		this.taskDefOperationService.save(taskDefOperation);
	}

	public void removeOperation(String taskDefinitionKey, String processDefinitionId, String operation) {
		TaskDefBase taskDefBase = this.taskDefBaseService.findByCodeAndProIdOne("code=? and processDefinitionId=?",
				new Object[] { taskDefinitionKey, processDefinitionId });

		if (taskDefBase == null) {
			return;
		}

		TaskDefOperation taskDefOperation = this.taskDefOperationService.findByBaseCodeAndBaseProcessIdOne(
				" taskDefBase=? and value=?", new Object[] { taskDefBase, operation });

		if (taskDefOperation == null) {
			return;
		}

		this.taskDefOperationService.remove(taskDefOperation);
	}

	public void addTaskUser(String taskDefinitionKey, String processDefinitionId, TaskUserDTO taskUser) {
		TaskDefBase taskDefBase = this.taskDefBaseService.findByCodeAndProIdOne("code=? and processDefinitionId=?",
				new Object[] { taskDefinitionKey, processDefinitionId });

		if (taskDefBase == null) {
			logger.info("cannot find taskDefBase {} {}", taskDefinitionKey, processDefinitionId);
			return;
		}
		TaskDefUser taskDefUser = this.taskDefUserService.findByBaseCodeAndBaseProcessIdOne(
				" taskDefBase=? and catalog=? and type=? and value=?",
				new Object[] { taskDefBase, taskUser.getCatalog(), taskUser.getType(), taskUser.getValue() });
		if (taskDefUser != null) {
			logger.info("cannot find taskDefUser {} {} {} {}", new Object[] { taskDefBase.getId(),
					taskUser.getCatalog(), taskUser.getType(), taskUser.getValue() });
			return;
		}
		taskDefUser = new TaskDefUser();
		taskDefUser.setId(this.idGenerator.generateId());
		taskDefUser.setTaskDefBase(taskDefBase);
		taskDefUser.setCatalog(taskUser.getCatalog());
		taskDefUser.setType(taskUser.getType());
		taskDefUser.setValue(taskUser.getValue());
		this.taskDefUserService.save(taskDefUser);
	}

	public void removeTaskUser(String taskDefinitionKey, String processDefinitionId, TaskUserDTO taskUser) {
		TaskDefBase taskDefBase = this.taskDefBaseService.findByCodeAndProIdOne("code=? and processDefinitionId=?",
				new Object[] { taskDefinitionKey, processDefinitionId });

		if (taskDefBase == null) {
			logger.info("cannot find taskDefBase {} {}", taskDefinitionKey, processDefinitionId);
			return;
		}
		TaskDefUser taskDefUser = this.taskDefUserService.findByBaseCodeAndBaseProcessIdOne(
				" taskDefBase=? and catalog=? and type=? and value=?",
				new Object[] { taskDefBase, taskUser.getCatalog(), taskUser.getType(), taskUser.getValue() });
		if (taskDefUser == null) {
			logger.info("cannot find taskDefUser {} {} {} {}", new Object[] { taskDefBase.getId(),
					taskUser.getCatalog(), taskUser.getType(), taskUser.getValue() });
			return;
		}
		this.taskDefUserService.remover(taskDefUser);
	}

	public void updateTaskUser(String taskDefinitionKey, String processDefinitionId, TaskUserDTO taskUser,
			String status) {
		TaskDefBase taskDefBase = this.taskDefBaseService.findByCodeAndProIdOne("code=? and processDefinitionId=?",
				new Object[] { taskDefinitionKey, processDefinitionId });

		if (taskDefBase == null) {
			logger.info("cannot find taskDefBase {} {}", taskDefinitionKey, processDefinitionId);
			return;
		}
		TaskDefUser taskDefUser = this.taskDefUserService.findByBaseCodeAndBaseProcessIdOne(
				" taskDefBase=? and catalog=? and type=? and value=?",
				new Object[] { taskDefBase, taskUser.getCatalog(), taskUser.getType(), taskUser.getValue() });
		if (taskDefUser == null) {
			logger.info("cannot find taskDefUser {} {} {} {}", new Object[] { taskDefBase.getId(),
					taskUser.getCatalog(), taskUser.getType(), taskUser.getValue() });
			return;
		}
		taskDefUser.setStatus(status);
		this.taskDefUserService.update(taskDefUser);
	}

	public void addTaskNotification(String taskDefinitionKey, String processDefinitionId,
			TaskNotificationDTO taskNotification) {
		TaskDefBase taskDefBase = this.taskDefBaseService.findByCodeAndProIdOne("code=? and processDefinitionId=?",
				new Object[] { taskDefinitionKey, processDefinitionId });

		if (taskDefBase == null) {
			logger.info("cannot find taskDefBase {} {}", taskDefinitionKey, processDefinitionId);
			return;
		}
		TaskDefNotification taskDefNotification = this.taskDefNotificationService.findByBaseCodeAndBaseProcessIdOne(
				"taskDefBase=? and eventName=? and templateCode=?",
				new Object[] { taskDefBase, taskNotification.getEventName(), taskNotification.getTemplateCode() });
		if (taskDefNotification == null) {
			taskDefNotification = new TaskDefNotification();
			taskDefNotification.setId(this.idGenerator.generateId());
			taskDefNotification.setTaskDefBase(taskDefBase);
			taskDefNotification.setEventName(taskNotification.getEventName());
			taskDefNotification.setTemplateCode(taskNotification.getTemplateCode());
			taskDefNotification.setType(taskNotification.getType());
		}
		taskDefNotification.setReceiver(taskNotification.getReceiver());
		taskDefNotification.setType(taskNotification.getType());
		this.taskDefNotificationService.save(taskDefNotification);
	}

	public void removeTaskNotification(String taskDefinitionKey, String processDefinitionId,
			TaskNotificationDTO taskNotification) {
		TaskDefBase taskDefBase = this.taskDefBaseService.findByCodeAndProIdOne("code=? and processDefinitionId=?",
				new Object[] { taskDefinitionKey, processDefinitionId });

		if (taskDefBase == null) {
			logger.info("cannot find taskDefBase {} {}", taskDefinitionKey, processDefinitionId);
			return;
		}
		TaskDefNotification taskDefNotification = this.taskDefNotificationService.findByBaseCodeAndBaseProcessIdOne(
				"taskDefBase=? and eventName=? and templateCode=?",
				new Object[] { taskDefBase, taskNotification.getEventName(), taskNotification.getTemplateCode() });
		if (taskDefNotification == null) {
			return;
		}
		this.taskDefNotificationService.remove(taskDefNotification);
	}

	public void addDeadline(String taskDefinitionKey, String processDefinitionId, DeadlineDTO deadline) {
		TaskDefBase taskDefBase = this.taskDefBaseService.findByCodeAndProIdOne("code=? and processDefinitionId=?",
				new Object[] { taskDefinitionKey, processDefinitionId });

		if (taskDefBase == null) {
			logger.info("cannot find taskDefBase {} {}", taskDefinitionKey, processDefinitionId);
			return;
		}
		TaskDefDeadline taskDefDeadline = this.taskDefDeadlineService.findByBaseCodeAndBaseProcessIdOne(
				" taskDefBase=? and type=? and duration=?",
				new Object[] { taskDefBase, deadline.getType(), deadline.getDuration() });
		if (taskDefDeadline == null) {
			taskDefDeadline = new TaskDefDeadline();
			taskDefDeadline.setId(this.idGenerator.generateId());
			taskDefDeadline.setTaskDefBase(taskDefBase);
			taskDefDeadline.setType(deadline.getType());
			taskDefDeadline.setDuration(deadline.getDuration());
		}
		taskDefDeadline.setNotificationType(deadline.getNotificationType());
		taskDefDeadline.setNotificationReceiver(deadline.getNotificationReceiver());
		taskDefDeadline.setNotificationTemplateCode(deadline.getNotificationTemplateCode());
		this.taskDefDeadlineService.save(taskDefDeadline);
	}

	public void removeDeadline(String taskDefinitionKey, String processDefinitionId, DeadlineDTO deadline) {
		TaskDefBase taskDefBase = this.taskDefBaseService.findByCodeAndProIdOne("code=? and processDefinitionId=?",
				new Object[] { taskDefinitionKey, processDefinitionId });

		if (taskDefBase == null) {
			logger.info("cannot find taskDefBase {} {}", taskDefinitionKey, processDefinitionId);
			return;
		}
		TaskDefDeadline taskDefDeadline = this.taskDefDeadlineService.findByBaseCodeAndBaseProcessIdOne(
				" taskDefBase=? and type=? and duration=?",
				new Object[] { taskDefBase, deadline.getType(), deadline.getDuration() });
		if (taskDefDeadline == null) {
			return;
		}
		this.taskDefDeadlineService.remove(taskDefDeadline);
	}
}