package com.citsh.listener.service.impl;

import com.citsh.base.service.TaskDefinitionService;
import com.citsh.humantask.TaskUserDTO;
import com.citsh.humantask.entity.TaskInfo;
import com.citsh.humantask.entity.TaskParticipant;
import com.citsh.humantask.service.TaskInfoService;
import com.citsh.id.IdGenerator;
import com.citsh.listener.config.AssignStrategyType;
import com.citsh.listener.service.ListenerService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ListenerAssignStrategyServiceImpl implements ListenerService {
	private static Logger logger = LoggerFactory.getLogger(ListenerAssignStrategyServiceImpl.class);

	@Autowired
	private TaskDefinitionService taskDefinitionService;

	@Autowired
	private TaskInfoService taskInfoService;

	@Autowired
	private IdGenerator idGenerator;

	public void onCreate(TaskInfo taskInfo) {
		if (taskInfo.getAssignee() != null) {
			return;
		}
		List<TaskParticipant> taskParticipants = new ArrayList<TaskParticipant>();
		String taskDefinitionKey = taskInfo.getCode();
		logger.debug("taskDefinitionKey:{}", taskDefinitionKey);
		String processDefinitionId = taskInfo.getProcessDefinitionId();
		logger.debug("processDefinitionId:{}", processDefinitionId);
		String strategy = this.taskDefinitionService.findTaskAssignStrategy(taskDefinitionKey, processDefinitionId);
		logger.debug("strategy:{}", strategy);
		List<TaskUserDTO> taskUsers = this.taskDefinitionService.findTaskUsers(taskDefinitionKey, processDefinitionId);
		for (TaskUserDTO taskUser : taskUsers) {
			String catalog = taskUser.getCatalog();
			String type = taskUser.getType();
			String value = taskUser.getValue();
			if ("assignee".equals(catalog)) {
				taskInfo.setAssignee(value);
				TaskParticipant taskParticipant = new TaskParticipant();
				taskParticipant.setId(this.idGenerator.generateId());
				taskParticipant.setCategory(catalog);
				taskParticipant.setRef(value);
				taskParticipant.setType(type);
				taskParticipant.setTaskInfo(taskInfo);
				taskParticipants.add(taskParticipant);
			}
		}
		String catalog;
		if (strategy == null) {
			return;
		}
		if (AssignStrategyType.ON.getValue().equals(strategy)) {
			return;
		}
		if (AssignStrategyType.ONE.getValue().equals(strategy)) {
			if (taskParticipants.size() != 1) {
				logger.info("candidateUsers size is {}", Integer.valueOf(taskParticipants.size()));
				return;
			}
			String userId = String.valueOf(((TaskParticipant) taskParticipants.get(0)).getId());
			logger.debug("userId:{}", userId);
			taskInfo.setAssignee(userId);
		} else {
			String userId;
			int taskCount;
			if (AssignStrategyType.MIN.getValue().equals(strategy)) {
				if (taskParticipants.size() <= 0) {
					logger.info("candidateUsers is empty");
					return;
				}
				userId = taskParticipants.get(0).getRef();
				taskCount = 0;
				for (TaskParticipant taskParticipant : taskParticipants) {
					int count = this.taskInfoService
							.listBySQL(" assignee=? and status='active' ", new Object[] { taskParticipant.getRef() })
							.size();
					if ((taskCount == 0) || (count < taskCount)) {
						taskCount = count;
						userId = taskParticipant.getRef();
					}
					logger.debug("taskCount:{}", Integer.valueOf(taskCount));
					logger.debug("userId:{}", userId);
					taskInfo.setAssignee(userId);
				}
			} else if (AssignStrategyType.RANDOM.getValue().equals(strategy)) {
				if (taskParticipants.size() <= 0) {
					logger.info("candidateUsers is empty");
					return;
				}

				Collections.shuffle(taskParticipants);
				userId = taskParticipants.get(0).getRef();
				logger.info("userId : {}", userId);
				taskInfo.setAssignee(userId);
			} else {
				logger.warn("unsupport strategy : {}", strategy);
			}
		}
	}

	public void onComplete(TaskInfo taskInfo) {
	}
}