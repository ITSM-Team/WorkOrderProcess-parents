package com.citsh.listener.web;

import com.citsh.config.entity.BpmConfBase;
import com.citsh.config.entity.BpmConfNode;
import com.citsh.config.service.BpmConfNodeService;
import com.citsh.id.IdGenerator;
import com.citsh.listener.entity.BpmConfListener;
import com.citsh.listener.service.BpmConfListenerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

@Controller
@RequestMapping({"bpm"})
public class BpmConfListenerController
{

  @Autowired
  private BpmConfNodeService bpmConfNodeService;

  @Autowired
  private BpmConfListenerService bpmConfListenerService;

  @Autowired
  private IdGenerator idGenerator;

  @GetMapping({"bpm-conf-listener-list"})
  public String list(@RequestParam("bpmConfNodeId") Long bpmConfNodeId)
  {
    BpmConfNode bpmConfNode = this.bpmConfNodeService.find(bpmConfNodeId);
    Long bpmConfBaseId = bpmConfNode.getBpmConfBase().getId();
    List bpmConfListeners = this.bpmConfListenerService.listBySQL("bpmConfNode.id=?", new Object[] { bpmConfNode
      .getId() });
    Model model = new RedirectAttributesModelMap();
    model.addAttribute("bpmConfBaseId", bpmConfBaseId);
    model.addAttribute("bpmConfListeners", bpmConfListeners);
    return "bpm/bpm-conf-listener-list";
  }

  @PostMapping({"bpm-conf-listener-save"})
  public String save(@RequestParam("bpmConfNodeId") Long bpmConfNodeId, @RequestParam("value") String value, @RequestParam("type") Integer type)
  {
    BpmConfNode bpmConfNode = this.bpmConfNodeService.find(bpmConfNodeId);
    BpmConfListener bpmConfListener = new BpmConfListener();
    bpmConfListener.setBpmConfNode(bpmConfNode);
    bpmConfListener.setValue(value);
    bpmConfListener.setType(type);
    bpmConfListener.setStatus(Integer.valueOf(1));
    bpmConfListener.setPriority(Integer.valueOf(0));
    bpmConfListener.setId(this.idGenerator.generateId());
    this.bpmConfListenerService.save(bpmConfListener);
    return "redirect:/bpm/bpm-conf-listener-list.do?bpmConfNodeId=" + bpmConfNodeId;
  }

  @PostMapping({"bpm-conf-listener-remove"})
  public String remove(@RequestParam("bpmConfNodeId") Long bpmConfNodeId, @RequestParam("id") Long id)
  {
    this.bpmConfListenerService.remove(id);
    return "redirect:/bpm/bpm-conf-listener-list.do?bpmConfNodeId=" + bpmConfNodeId;
  }
}