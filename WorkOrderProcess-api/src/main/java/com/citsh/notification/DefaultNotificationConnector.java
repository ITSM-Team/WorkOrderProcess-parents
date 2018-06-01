package com.citsh.notification;

import com.citsh.template.TemplateConnector;
import com.citsh.template.TemplateDTO;
import com.citsh.template.TemplateService;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultNotificationConnector implements NotificationConnector, NotificationRegistry {
	private Map<String, NotificationHandler> map = new HashMap();
	private TemplateConnector templateConnector;
	private TemplateService templateService;

	public void send(NotificationDTO notificationDto, String tenantId) {
		String subject;
		if (notificationDto.getTemplate() != null) {
			TemplateDTO templateDto = this.templateConnector.findByCode(notificationDto.getTemplate(), tenantId);
			subject = processTemplate(templateDto.getField("subject"), notificationDto.getData());
			String content = processTemplate(templateDto.getField("content"), notificationDto.getData());

			if (subject != null) {
				notificationDto.setSubject(subject);
			}

			if (content != null) {
				notificationDto.setContent(content);
			}
		}

		List<String> types = notificationDto.getTypes();

		for (String type : types)
			sendByType(type, notificationDto, tenantId);
	}

	public void sendByType(String type, NotificationDTO notificationDto, String tenantId) {
		NotificationHandler notificationHandler = (NotificationHandler) this.map.get(type);

		if (notificationHandler == null) {
			return;
		}

		notificationHandler.handle(notificationDto, tenantId);
	}

	public String processTemplate(String template, Map<String, Object> data) {
		return this.templateService.renderText(template, data);
	}

	public void register(NotificationHandler notificationHandler) {
		this.map.put(notificationHandler.getType(), notificationHandler);
	}

	public void unregister(NotificationHandler notificationHandler) {
		this.map.remove(notificationHandler.getType());
	}

	public Collection<String> getTypes(String tenantId) {
		return this.map.keySet();
	}

	public void setMap(Map<String, NotificationHandler> map) {
		this.map = map;
	}

	public void setTemplateConnector(TemplateConnector templateConnector) {
		this.templateConnector = templateConnector;
	}

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}
}