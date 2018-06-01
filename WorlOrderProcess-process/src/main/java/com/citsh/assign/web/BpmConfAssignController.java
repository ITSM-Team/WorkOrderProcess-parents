package com.citsh.assign.web;

import com.citsh.assign.entity.BpmConfAssign;
import com.citsh.assign.service.BpmConfAssignService;
import com.citsh.config.entity.BpmConfBase;
import com.citsh.config.entity.BpmConfNode;
import com.citsh.config.service.BpmConfNodeService;
import com.citsh.humantask.TaskDefinitionConnector;
import com.citsh.id.IdGenerator;
import com.citsh.util.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"bpm"})
public class BpmConfAssignController
{

  @Autowired
  private BpmConfAssignService bpmConfAssignService;

  @Autowired
  private BpmConfNodeService bpmConfNodeService;

  @Autowired
  private IdGenerator idGenerator;
  private BeanMapper beanMapper = new BeanMapper();

  @Autowired
  private TaskDefinitionConnector taskDefinitionConnector;

  @PostMapping({"bpm-conf-assign-save"})
  public String save(@ModelAttribute BpmConfAssign bpmConfAssign, @RequestParam("bpmConfNodeId") Long bpmConfNodeId) { Long id = bpmConfAssign.getId();
    BpmConfAssign dest = null;
    if (id != null) {
      this.bpmConfAssignService.findById(id);
    } else {
      dest = new BpmConfAssign();
      BpmConfNode bpmConfNode = this.bpmConfNodeService.find(bpmConfNodeId);
      dest.setBpmConfNode(bpmConfNode);
      dest.setId(this.idGenerator.generateId());
    }
    this.beanMapper.copy(bpmConfAssign, dest);
    this.bpmConfAssignService.save(bpmConfAssign);
    String taskDefinitionKey = dest.getBpmConfNode().getCode();
    String processDefinitionId = dest.getBpmConfNode().getBpmConfBase().getProcessDefinitionId();
    String assignStrategy = dest.getName();
    this.taskDefinitionConnector.saveAssignStrategy(taskDefinitionKey, processDefinitionId, assignStrategy);
    return "redirect:/bpm/bpm-conf-user-list.do?bpmConfNodeId=" + bpmConfNodeId;
  }
}