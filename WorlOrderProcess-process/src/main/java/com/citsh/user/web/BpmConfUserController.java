package com.citsh.user.web;

import com.citsh.assign.entity.BpmConfAssign;
import com.citsh.assign.service.BpmConfAssignService;
import com.citsh.config.entity.BpmConfCountersign;
import com.citsh.config.entity.BpmConfNode;
import com.citsh.config.service.BpmConfCountersignService;
import com.citsh.config.service.BpmConfNodeService;
import com.citsh.humantask.TaskDefinitionConnector;
import com.citsh.humantask.TaskUserDTO;
import com.citsh.user.entity.BpmConfUser;
import com.citsh.user.service.BpmConfUserService;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class BpmConfUserController
{
  private static Logger logger = LoggerFactory.getLogger(BpmConfUserController.class);

  @Autowired
  private BpmConfNodeService bpmConfNodeService;

  @Autowired
  private BpmConfUserService bpmConfUserService;

  @Autowired
  private BpmConfCountersignService bpmConfCountersignService;

  @Autowired
  private BpmConfAssignService bpmConfAssignService;

  @Autowired
  private TaskDefinitionConnector taskDefinitionConnector;

  @GetMapping({"bpm-conf-user-list"})
  public String list(@RequestParam("bpmConfNodeId") Long bpmConfNodeId) { BpmConfNode bpmConfNode = this.bpmConfNodeService.find(bpmConfNodeId);
    Long bpmConfBaseId = bpmConfNode.getBpmConfBase().getId();
    Long bomConfNodeId = bpmConfNode.getId();
    List bpmConfUsers = this.bpmConfUserService.findBy("bpmConfNode.id", bomConfNodeId);
    BpmConfCountersign bpmConfCountersign = this.bpmConfCountersignService.findOneBy("bpmConfNode.id", bomConfNodeId);
    BpmConfAssign bpmConfAssign = this.bpmConfAssignService.findOneBy("bpmConfNode.id", bomConfNodeId);
    Model model = new RedirectAttributesModelMap();
    model.addAttribute("bpmConfBase", bpmConfNode.getBpmConfBase());
    model.addAttribute("bpmConfNode", bpmConfNode);
    model.addAttribute("bpmConfBaseId", bpmConfBaseId);
    model.addAttribute("bpmConfUsers", bpmConfUsers);
    model.addAttribute("bpmConfCountersign", bpmConfCountersign);
    model.addAttribute("bpmConfAssign", bpmConfAssign);
    return "bpm/bpm-conf-user-list"; }

  @PostMapping({"bpm-conf-user-save"})
  public String save(@ModelAttribute BpmConfUser bpmConfUser, @RequestParam("bpmConfNodeId") Long bpmConfNodeId) {
    if (StringUtils.isBlank(bpmConfUser.getValue())) {
      logger.info("bpmConfUser cannot blank");
      return "redirect:/bpm/bpm-conf-user-list.do?bpmConfNodeId=" + bpmConfNodeId;
    }

    if (bpmConfUser.getType().intValue() == 0) {
      BpmConfUser targetBpmConfUser = this.bpmConfUserService.findByNodeIdAndStatusAndType(bpmConfNodeId, 0, 0);
      if (targetBpmConfUser != null) {
        targetBpmConfUser.setStatus(Integer.valueOf(2));
        this.bpmConfUserService.save(targetBpmConfUser);
      }

    }

    if (bpmConfUser.getType().intValue() == 0) {
      BpmConfUser targetBpmConfUser = this.bpmConfUserService.findByNodeIdAndStatusAndType(bpmConfNodeId, 0, 1);
      if (targetBpmConfUser != null) {
        targetBpmConfUser.setValue(bpmConfUser.getValue());
        bpmConfUser = targetBpmConfUser;
      }
    }

    bpmConfUser.setPriority(Integer.valueOf(0));
    bpmConfUser.setStatus(Integer.valueOf(1));
    BpmConfNode bpmConfNode = this.bpmConfNodeService.find(bpmConfNodeId);
    bpmConfUser.setBpmConfNode(bpmConfNode);
    this.bpmConfUserService.save(bpmConfUser);

    BpmConfUser dest = bpmConfUser;

    String taskDefinitionKey = dest.getBpmConfNode().getCode();

    String processDefinitionId = dest.getBpmConfNode().getBpmConfBase().getProcessDefinitionId();
    Integer type = dest.getType();
    String value = dest.getValue();
    TaskUserDTO taskUser = new TaskUserDTO();

    if (type.intValue() == 0)
    {
      taskUser.setCatalog("assignee");
    } else if ((type.intValue() == 1) || (type.intValue() == 2))
    {
      taskUser.setCatalog("candidate");
    } else if (type.intValue() == 3)
    {
      taskUser.setCatalog("notification");
    }

    if ((type.intValue() == 0) || (type.intValue() == 1))
      taskUser.setType("user");
    else if (type.intValue() == 2) {
      taskUser.setType("group");
    }

    taskUser.setValue(value);

    this.taskDefinitionConnector.addTaskUser(taskDefinitionKey, processDefinitionId, taskUser);
    return "redirect:/bpm/bpm-conf-user-list.do?bpmConfNodeId=" + bpmConfNodeId;
  }

  @PostMapping({"bpm-conf-user-remove"})
  public String remove(@RequestParam("id") Long id)
  {
    BpmConfUser bpmConfUser = this.bpmConfUserService.findById(id);
    Long bpmConfNodeId = bpmConfUser.getBpmConfNode().getId();

    if (bpmConfUser.getStatus().intValue() == 0)
    {
      bpmConfUser.setStatus(Integer.valueOf(2));
      this.bpmConfUserService.save(bpmConfUser);
    } else if (bpmConfUser.getStatus().intValue() == 1)
    {
      this.bpmConfUserService.remove(bpmConfUser);
    } else if (bpmConfUser.getStatus().intValue() == 2)
    {
      bpmConfUser.setStatus(Integer.valueOf(0));
      this.bpmConfUserService.save(bpmConfUser);
    }
    BpmConfUser dest = bpmConfUser;
    String taskDefinitionKey = dest.getBpmConfNode().getCode();
    String processDefinitionId = dest.getBpmConfNode().getBpmConfBase().getProcessDefinitionId();

    Integer type = dest.getType();
    String value = dest.getValue();
    TaskUserDTO taskUser = new TaskUserDTO();

    if (type.intValue() == 0)
      taskUser.setCatalog("assignee");
    else if ((type.intValue() == 1) || (type.intValue() == 2))
      taskUser.setCatalog("candidate");
    else if (type.intValue() == 3) {
      taskUser.setCatalog("notification");
    }

    if ((type.intValue() == 0) || (type.intValue() == 1))
      taskUser.setType("user");
    else if (type.intValue() == 2) {
      taskUser.setType("group");
    }

    taskUser.setValue(value);

    if (bpmConfUser.getStatus().intValue() == 0)
    {
      this.taskDefinitionConnector.updateTaskUser(taskDefinitionKey, processDefinitionId, taskUser, "active");
    } else if (bpmConfUser.getStatus().intValue() == 1)
    {
      this.taskDefinitionConnector.removeTaskUser(taskDefinitionKey, processDefinitionId, taskUser);
    } else if (bpmConfUser.getStatus().intValue() == 2)
    {
      this.taskDefinitionConnector.updateTaskUser(taskDefinitionKey, processDefinitionId, taskUser, "disable");
    }

    return "redirect:/bpm/bpm-conf-user-list.do?bpmConfNodeId=" + bpmConfNodeId;
  }
}