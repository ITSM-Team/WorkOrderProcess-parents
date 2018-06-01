package com.citsh.notice.web;

import com.citsh.config.entity.BpmConfBase;
import com.citsh.config.entity.BpmConfNode;
import com.citsh.config.service.BpmConfNodeService;
import com.citsh.humantask.DeadlineDTO;
import com.citsh.humantask.TaskDefinitionConnector;
import com.citsh.humantask.TaskNotificationDTO;
import com.citsh.notice.entity.BpmConfNotice;
import com.citsh.notice.service.BpmConfNoticeService;
import com.citsh.notice.service.BpmMailTemplateService;
import com.citsh.notification.NotificationConnector;
import com.citsh.tenant.TenantHolder;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

@Controller
@RequestMapping({"bpm"})
public class BpmConfNoticeController
{

  @Autowired
  private BpmConfNodeService bpmConfNodeService;

  @Autowired
  private BpmConfNoticeService bpmConfNoticeService;

  @Autowired
  private BpmMailTemplateService bpmMailTemplateService;

  @Autowired
  private TenantHolder tenantHolder;

  // @Autowired
  // private TemplateInfoService templateInfoService;

  @Autowired
  private NotificationConnector notificationConnector;

  @Autowired
  private TaskDefinitionConnector taskDefinitionConnector;

  @GetMapping({"bpm-conf-notice-list"})
  public String list(@RequestParam("bpmConfNodeId") Long bpmConfNodeId)
  {
    BpmConfNode bpmConfNode = this.bpmConfNodeService.find(bpmConfNodeId);
    Long bpmConfBaseId = bpmConfNode.getBpmConfBase().getId();
    List bpmConfNotices = this.bpmConfNoticeService.listBySQL("bpmConfNode.id", new Object[] { bpmConfNode.getId() });
    List bpmMailTemplates = this.bpmMailTemplateService.findAll();

    Model model = new RedirectAttributesModelMap();
    model.addAttribute("bpmConfBaseId", bpmConfBaseId);
    model.addAttribute("bpmConfNotices", bpmConfNotices);
    model.addAttribute("bpmMailTemplates", bpmMailTemplates);
    return "bpm/bpm-conf-notice-list";
  }

  public String input() {
    String tenantId = this.tenantHolder.getTenantId();
  //   List templateDTOs = this.templateInfoService.findAll(tenantId);
    Model model = new RedirectAttributesModelMap();
    model.addAttribute("templateDtos", null);
    Collection types = this.notificationConnector.getTypes(tenantId);
    model.addAttribute("types", types);

    return "bpm/bpm-conf-notice-input";
  }

  @PostMapping({"bpm-conf-notice-save"})
  public String save(@ModelAttribute BpmConfNotice bpmConfNotice, @RequestParam("bpmConfNodeId") Long bpmConfNodeId, @RequestParam("templateCode") String templateCode, @RequestParam("notificationTypes") List<String> notificationTypes)
  {
    BpmConfNode bpmConfNode = this.bpmConfNodeService.find(bpmConfNodeId);
    bpmConfNotice.setBpmConfNode(bpmConfNode);
    bpmConfNotice.setTemplateCode(templateCode);
    bpmConfNotice.setNotificationType(join(notificationTypes));
    this.bpmConfNoticeService.save(bpmConfNotice);

    BpmConfNotice dest = bpmConfNotice;
    String taskDefinitionKey = dest.getBpmConfNode().getCode();
    String processDefinitionId = dest.getBpmConfNode().getBpmConfBase().getProcessDefinitionId();
    String receiver = bpmConfNotice.getReceiver();
    String notificationType = bpmConfNotice.getNotificationType();
    String duration = bpmConfNotice.getDueDate();
    if (bpmConfNotice.getType().intValue() == 0) {
      TaskNotificationDTO taskNotification = new TaskNotificationDTO();
      taskNotification.setEventName("create");
      taskNotification.setTemplateCode(templateCode);
      taskNotification.setReceiver(receiver);
      taskNotification.setType(notificationType);
      this.taskDefinitionConnector.addTaskNotification(taskDefinitionKey, processDefinitionId, taskNotification);
    } else if (bpmConfNotice.getType().intValue() == 1) {
      TaskNotificationDTO taskNotification = new TaskNotificationDTO();
      taskNotification.setEventName("complete");
      taskNotification.setTemplateCode(templateCode);
      taskNotification.setReceiver(receiver);
      taskNotification.setType(notificationType);
      this.taskDefinitionConnector.addTaskNotification(taskDefinitionKey, processDefinitionId, taskNotification);
    } else if (bpmConfNotice.getType().intValue() == 2) {
      DeadlineDTO deadline = new DeadlineDTO();
      deadline.setType("completion");
      deadline.setDuration(duration);
      deadline.setNotificationTemplateCode(templateCode);
      deadline.setNotificationReceiver(receiver);
      deadline.setNotificationType(notificationType);
      this.taskDefinitionConnector.addDeadline(taskDefinitionKey, processDefinitionId, deadline);
    }
    return new StringBuilder().append("redirect:/bpm/bpm-conf-notice-list.do?bpmConfNodeId=").append(bpmConfNodeId).toString();
  }
  @RequestMapping({"bpm-conf-notice-remove"})
  public String remove(@RequestParam("id") Long id) {
    BpmConfNotice bpmConfNotice = this.bpmConfNoticeService.findById(id);
    Long bpmConfNodeId = bpmConfNotice.getBpmConfNode().getId();
    this.bpmConfNoticeService.remove(id);
    BpmConfNotice dest = bpmConfNotice;
    String taskDefinitionKey = dest.getBpmConfNode().getCode();
    String processDefinitionId = dest.getBpmConfNode().getBpmConfBase().getProcessDefinitionId();

    String templateCode = dest.getTemplateCode();
    String receiver = dest.getReceiver();
    String notificationType = dest.getNotificationType();
    String duration = bpmConfNotice.getDueDate();

    if (bpmConfNotice.getType().intValue() == 0) {
      TaskNotificationDTO taskNotification = new TaskNotificationDTO();
      taskNotification.setEventName("create");
      taskNotification.setTemplateCode(templateCode);
      this.taskDefinitionConnector.removeTaskNotification(taskDefinitionKey, processDefinitionId, taskNotification);
    } else if (bpmConfNotice.getType().intValue() == 1) {
      TaskNotificationDTO taskNotification = new TaskNotificationDTO();
      taskNotification.setEventName("complete");
      taskNotification.setTemplateCode(templateCode);
      this.taskDefinitionConnector.removeTaskNotification(taskDefinitionKey, processDefinitionId, taskNotification);
    } else if (bpmConfNotice.getType().intValue() == 2) {
      DeadlineDTO deadline = new DeadlineDTO();
      deadline.setType("completion");
      deadline.setDuration(duration);
      deadline.setNotificationTemplateCode(templateCode);
      deadline.setNotificationReceiver(receiver);
      deadline.setNotificationType(notificationType);
      this.taskDefinitionConnector.removeDeadline(taskDefinitionKey, processDefinitionId, deadline);
    }

    return new StringBuilder().append("redirect:/bpm/bpm-conf-notice-list.do?bpmConfNodeId=").append(bpmConfNodeId).toString();
  }

  public String join(List<String> notificationTypes) {
    if (notificationTypes.isEmpty()) {
      return "";
    }

    StringBuilder buff = new StringBuilder();

    for (String text : notificationTypes) {
      buff.append(text).append(",");
    }

    buff.deleteCharAt(buff.length() - 1);

    return buff.toString();
  }
}