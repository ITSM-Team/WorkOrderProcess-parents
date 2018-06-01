package com.citsh.form.web;

import com.citsh.config.entity.BpmConfBase;
import com.citsh.config.entity.BpmConfNode;
import com.citsh.config.service.BpmConfNodeService;
import com.citsh.form.entity.BpmConfForm;
import com.citsh.form.service.BpmConfFormService;
import com.citsh.humantask.FormDTO;
import com.citsh.humantask.TaskDefinitionConnector;
import com.citsh.id.IdGenerator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"bpm"})
public class BpmConfFormController
{

  @Autowired
  private BpmConfNodeService bpmConfNodeService;

  @Autowired
  private BpmConfFormService bpmConfFormService;

  @Autowired
  private IdGenerator idGenerator;

  @Autowired
  private TaskDefinitionConnector taskDefinitionConnector;

  @GetMapping({"bpm-conf-form-list"})
  public String list(@RequestParam("bpmConfNodeId") Long bpmConfNodeId, Model model)
  {
    BpmConfNode bpmConfNode = this.bpmConfNodeService.find(bpmConfNodeId);
    Long bpmConfBaseId = bpmConfNode.getBpmConfBase().getId();
    List bpmConfForms = this.bpmConfFormService.listBySQL("bpmConfNode.id=?", new Object[] { bpmConfNode.getId() });
    model.addAttribute("bpmConfBaseId", bpmConfBaseId);
    model.addAttribute("bpmConfForms", bpmConfForms);
    return "bpm/bpm-conf-form-list";
  }
  @PostMapping({"bpm-conf-form-save"})
  public String save(@ModelAttribute BpmConfForm bpmConfForm, @RequestParam("bpmConfNodeId") Long bpmConfNodeId) {
    BpmConfForm dest = this.bpmConfFormService.listBySQLOne("bpmConfNode.id=?", new Object[] { bpmConfNodeId });
    if (dest == null) {
      dest = bpmConfForm;
      BpmConfNode bpmConfNode = this.bpmConfNodeService.find(bpmConfNodeId);
      dest.setBpmConfNode(bpmConfNode);
      dest.setStatus(Integer.valueOf(1));
      if (dest.getId() == null) {
        dest.setId(this.idGenerator.generateId());
      }
      this.bpmConfFormService.save(dest);
    } else {
      dest.setValue(bpmConfForm.getValue());
      dest.setType(bpmConfForm.getType());
      dest.setStatus(Integer.valueOf(1));
      this.bpmConfFormService.save(dest);
    }

    String taskDefinitionKey = dest.getBpmConfNode().getCode();
    String processDefinitionId = dest.getBpmConfNode().getBpmConfBase().getProcessDefinitionId();
    FormDTO form = new FormDTO();
    form.setType(bpmConfForm.getType().intValue() == 0 ? "internal" : "external");
    form.setKey(bpmConfForm.getValue());

    this.taskDefinitionConnector.saveForm(taskDefinitionKey, processDefinitionId, form);

    return "redirect:/bpm/bpm-conf-form-list.do?bpmConfNodeId=" + bpmConfNodeId;
  }
  @PostMapping({"bpm-conf-form-remove"})
  public String remove(@RequestParam("id") Long id) {
    BpmConfForm bpmConfForm = this.bpmConfFormService.findById(id);
    Long bpmConfNodeId = bpmConfForm.getBpmConfNode().getId();
    if (bpmConfForm.getStatus().intValue() == 0) {
      bpmConfForm.setStatus(Integer.valueOf(2));
      this.bpmConfFormService.save(bpmConfForm);
    } else if (bpmConfForm.getStatus().intValue() == 2) {
      bpmConfForm.setStatus(Integer.valueOf(0));
      this.bpmConfFormService.save(bpmConfForm);
    } else if (bpmConfForm.getStatus().intValue() == 1) {
      if (bpmConfForm.getOriginValue() == null) {
        this.bpmConfFormService.remove(bpmConfForm.getId());
      } else {
        bpmConfForm.setStatus(Integer.valueOf(0));
        bpmConfForm.setValue(bpmConfForm.getOriginValue());
        bpmConfForm.setType(bpmConfForm.getOriginType());
        this.bpmConfFormService.save(bpmConfForm);
      }
    }
    BpmConfForm dest = bpmConfForm;
    String taskDefinitionKey = dest.getBpmConfNode().getCode();
    String processDefinitionId = dest.getBpmConfNode().getBpmConfBase().getProcessDefinitionId();
    FormDTO form = new FormDTO();
    form.setType(bpmConfForm.getType().intValue() == 0 ? "internal" : "external");
    form.setKey(bpmConfForm.getValue());
    this.taskDefinitionConnector.saveForm(taskDefinitionKey, processDefinitionId, form);
    return "redirect:/bpm/bpm-conf-form-list.do?bpmConfNodeId=" + bpmConfNodeId;
  }
}