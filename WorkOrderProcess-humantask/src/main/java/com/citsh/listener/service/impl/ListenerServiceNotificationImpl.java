package com.citsh.listener.service.impl;

import com.citsh.activiti.service.InternalProcessService;
import com.citsh.base.service.TaskDefinitionService;
import com.citsh.enumitem.HumanTaskEnum;
import com.citsh.humantask.TaskNotificationDTO;
import com.citsh.humantask.entity.TaskInfo;
import com.citsh.listener.service.ListenerService;
import com.citsh.notification.NotificationConnector;
import com.citsh.notification.NotificationDTO;
import com.citsh.user.UserDTO;
import com.citsh.user.service.UserService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ListenerServiceNotificationImpl implements ListenerService {
	private Logger logger = LoggerFactory.getLogger(ListenerServiceNotificationImpl.class);

	@Autowired
	private TaskDefinitionService taskDefinitionService;

	@Autowired
	private NotificationConnector notificationConnector;

	@Autowired
	private UserService userService;

	@Autowired
	private InternalProcessService internalProcessService;

	@Value("${application.baseUrl}")
	private String baseUrl;

	public void onCreate(TaskInfo taskInfo) {
		doNotice(taskInfo, "create");
	}

	public void onComplete(TaskInfo taskInfo) {
		doNotice(taskInfo, "complete");
	}

	public void doNotice(TaskInfo taskInfo, String eventName) {
		String taskDefinitionKey = taskInfo.getCode();
		String processDefinitionId = taskInfo.getProcessDefinitionId();
		List<TaskNotificationDTO> taskNotifications = this.taskDefinitionService
				.findTaskNotifications(taskDefinitionKey, processDefinitionId, eventName);

		Map data = prepareData(taskInfo);
		for (TaskNotificationDTO taskNotification : taskNotifications) {
			String templateCode = taskNotification.getTemplateCode();
			String type = taskNotification.getType();
			String receiver = taskNotification.getReceiver();
			UserDTO userDto = null;
			if (HumanTaskEnum.RECEIVE.getName().equals(receiver)) {
				userDto = this.userService.findById(taskInfo.getAssignee());
			} else if (HumanTaskEnum.INITIATE.getName().equals(receiver)) {
				String initiator = this.internalProcessService.findInitiator(taskInfo.getProcessInstanceId());
				userDto = this.userService.findById(initiator);
			} else {
				userDto = this.userService.findById(receiver);
			}
			if (userDto == null) {
				this.logger.debug("userDto is null : {}", receiver);
				continue;
			}
			NotificationDTO notificationDto = new NotificationDTO();
			notificationDto.setReceiver(userDto.getId());
			notificationDto.setReceiverType("userid");
			notificationDto.setTypes(Arrays.asList(type.split(",")));
			notificationDto.setData(data);
			notificationDto.setTemplate(templateCode);
			this.notificationConnector.send(notificationDto, taskInfo.getTenantId());
		}
	}

	public Map<String, Object> prepareData(TaskInfo taskInfo) {
		String assignee = taskInfo.getAssignee();
		String initiator = this.internalProcessService.findInitiator(taskInfo.getProcessInstanceId());
		UserDTO assigneeUser = null;
		if (StringUtils.isNoneEmpty(new CharSequence[] { assignee })) {
			assigneeUser = this.userService.findById(assignee);
		}
		UserDTO initiatorUser = this.userService.findById(initiator);
		Map data = new HashMap();

		Map taskEntity = new HashMap();
		taskEntity.put("id", taskInfo.getId());
		taskEntity.put("name", taskInfo.getName());
		if (assigneeUser != null) {
			taskEntity.put("assignee", assigneeUser.getDisplayName());
		}

		data.put("task", taskEntity);
		data.put("initiator", initiatorUser.getDisplayName());
		data.put("humanTask", taskInfo);
		data.put("baseUrl", this.baseUrl);
		data.put("humanTaskId", Long.toString(taskInfo.getId().longValue()));

		return data;
	}
}