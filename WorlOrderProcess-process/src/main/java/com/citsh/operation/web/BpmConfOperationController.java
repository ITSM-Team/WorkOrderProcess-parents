package com.citsh.operation.web;

import com.citsh.config.entity.BpmConfBase;
import com.citsh.config.entity.BpmConfNode;
import com.citsh.config.service.BpmConfNodeService;
import com.citsh.humantask.TaskDefinitionConnector;
import com.citsh.operation.entity.BpmConfOperation;
import com.citsh.operation.service.BpmConfOperationService;
import java.util.ArrayList;
import java.util.Iterator;
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
@RequestMapping({ "bpm" })
public class BpmConfOperationController {

	@Autowired
	private BpmConfOperationService bpmConfOperationService;

	@Autowired
	private BpmConfNodeService bpmConfNodeService;

	@Autowired
	private TaskDefinitionConnector taskDefinitionConnector;

	@GetMapping({ "bpm-conf-operation-list" })
	public String list(@RequestParam("bpmConfNodeId") Long bpmConfNodeId) {
		List<String> operations = new ArrayList<String>();
		operations.add("saveDraft");
		operations.add("completeTask");
		operations.add("rollbackPrevious");
		operations.add("rollbackInitiator");
		operations.add("transfer");
		operations.add("delegateTask");
		operations.add("delegateTaskCreate");
		operations.add("communicate");
		operations.add("callback");
		operations.add("addCounterSign");
		BpmConfNode bpmConfNode = this.bpmConfNodeService.find(bpmConfNodeId);
		Long bpmConfBaseId = bpmConfNode.getBpmConfBase().getId();
		List<BpmConfOperation> bpmConfOperations = this.bpmConfOperationService.listBySQL("bpmConfNode.id",
				new Object[] { bpmConfNode.getId() });
		for (Iterator<String> iterator = operations.iterator(); iterator.hasNext();) {
			String value = iterator.next();
			for (BpmConfOperation bpmConfOperation : bpmConfOperations)
				if (value.equals(bpmConfOperation.getValue())) {
					iterator.remove();
					break;
				}
		}
		String value;
		Model model = new RedirectAttributesModelMap();
		model.addAttribute("bpmConfBaseId", bpmConfBaseId);
		model.addAttribute("bpmConfOperations", bpmConfOperations);
		model.addAttribute("operations", operations);

		return "bpm/bpm-conf-operation-list";
	}

	@PostMapping({ "bpm-conf-operation-save" })
	public String save(@ModelAttribute BpmConfOperation bpmConfOperation,
			@RequestParam("bpmConfNodeId") Long bpmConfNodeId) {
		if ((bpmConfOperation.getValue() == null) || ("".equals(bpmConfOperation.getValue()))) {
			return "redirect:/bpm/bpm-conf-operation-list.do?bpmConfNodeId=" + bpmConfNodeId;
		}
		BpmConfNode bpmConfNode = this.bpmConfNodeService.find(bpmConfNodeId);
		bpmConfOperation.setBpmConfNode(bpmConfNode);
		this.bpmConfOperationService.save(bpmConfOperation);
		BpmConfOperation dest = bpmConfOperation;
		String taskDefinitionKey = dest.getBpmConfNode().getCode();
		String processDefinitionId = dest.getBpmConfNode().getBpmConfBase().getProcessDefinitionId();
		String operation = dest.getValue();
		this.taskDefinitionConnector.addOperation(taskDefinitionKey, processDefinitionId, operation);
		return "redirect:/bpm/bpm-conf-operation-list.do?bpmConfNodeId=" + bpmConfNodeId;
	}

	@PostMapping({ "bpm-conf-operation-remove" })
	public String remove(@RequestParam("id") Long id) {
		BpmConfOperation bpmConfOperation = this.bpmConfOperationService.findById(id);
		Long bpmConfNodeId = bpmConfOperation.getBpmConfNode().getId();
		this.bpmConfOperationService.remove(bpmConfOperation.getId());
		BpmConfOperation dest = bpmConfOperation;
		String taskDefinitionKey = dest.getBpmConfNode().getCode();
		String processDefinitionId = dest.getBpmConfNode().getBpmConfBase().getProcessDefinitionId();
		String operation = dest.getValue();
		this.taskDefinitionConnector.removeOperation(taskDefinitionKey, processDefinitionId, operation);
		return "redirect:/bpm/bpm-conf-operation-list.do?bpmConfNodeId=" + bpmConfNodeId;
	}
}