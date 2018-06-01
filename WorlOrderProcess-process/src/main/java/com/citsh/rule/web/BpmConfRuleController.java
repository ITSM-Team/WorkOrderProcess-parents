package com.citsh.rule.web;

import com.citsh.config.entity.BpmConfBase;
import com.citsh.config.entity.BpmConfNode;
import com.citsh.config.service.BpmConfNodeService;
import com.citsh.id.IdGenerator;
import com.citsh.rule.entity.BpmConfRule;
import com.citsh.rule.service.BpmConfRuleService;
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
public class BpmConfRuleController
{

  @Autowired
  private BpmConfNodeService bpmConfNodeService;

  @Autowired
  private BpmConfRuleService bpmConfRuleService;

  @Autowired
  private IdGenerator idGenerator;

  @GetMapping({"bpm-conf-rule-list"})
  public String list(@RequestParam("bpmConfNodeId") Long bpmConfNodeId)
  {
    BpmConfNode bpmConfNode = this.bpmConfNodeService.find(bpmConfNodeId);
    Long bpmConfBaseId = bpmConfNode.getBpmConfBase().getId();
    List bpmConfRules = this.bpmConfRuleService.listBySQL("bpmConfNode.id=?", new Object[] { bpmConfNode.getId() });

    Model model = new RedirectAttributesModelMap();
    model.addAttribute("bpmConfBaseId", bpmConfBaseId);
    model.addAttribute("bpmConfRules", bpmConfRules);

    return "bpm/bpm-conf-rule-list";
  }
  @PostMapping({"bpm-conf-rule-save"})
  public String save(@ModelAttribute BpmConfRule bpmConfRule, @RequestParam("bpmConfNodeId") Long bpmConfNodeId) {
    if ((bpmConfRule.getValue() == null) || ("".equals(bpmConfRule.getValue()))) {
      return "redirect:/bpm/bpm-conf-rule-list.do?bpmConfNodeId=" + bpmConfNodeId;
    }
    BpmConfNode bpmConfNode = this.bpmConfNodeService.find(bpmConfNodeId);
    bpmConfRule.setBpmConfNode(bpmConfNode);
    if (bpmConfRule.getId() == null) {
      bpmConfRule.setId(this.idGenerator.generateId());
    }
    this.bpmConfRuleService.save(bpmConfRule);
    return "redirect:/bpm/bpm-conf-rule-list.do?bpmConfNodeId=" + bpmConfNodeId;
  }
  @PostMapping({"bpm-conf-rule-remove"})
  public String remove(@RequestParam("id") Long id) {
    BpmConfRule bpmConfRule = this.bpmConfRuleService.findById(id);
    Long bpmConfNodeId = bpmConfRule.getBpmConfNode().getId();
    this.bpmConfRuleService.remove(bpmConfRule.getId());
    return "redirect:/bpm/bpm-conf-rule-list.do?bpmConfNodeId=" + bpmConfNodeId;
  }
}