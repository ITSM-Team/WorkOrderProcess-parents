package com.citsh.config.web;
import com.citsh.config.entity.BpmConfBase;
import com.citsh.config.entity.BpmConfNode;
import com.citsh.config.service.BpmConfBaseService;
import com.citsh.config.service.BpmConfNodeService;
import com.google.j2objc.annotations.ReflectionSupport;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"bpm"})
public class BpmConfNodeController
{

  @Autowired
  private BpmConfBaseService bpmConfBaseService;

  @Autowired
  private BpmConfNodeService bpmConfNodeService;

  @RequestMapping({"bpm-conf-node-list"})
  public String list(@RequestParam("bpmConfBaseId") Long bpmConfBaseId, Model model)
  {
    BpmConfBase bpmConfBase = this.bpmConfBaseService.find(bpmConfBaseId);
    List<BpmConfNode> bpmConfNodes = this.bpmConfNodeService.findByBpmConfBase(bpmConfBase.getId());
    model.addAttribute("bpmConfBase", bpmConfBase);
    model.addAttribute("bpmConfNodes", bpmConfNodes);
    return "bpm/bpm-conf-node-list";
  }
}
