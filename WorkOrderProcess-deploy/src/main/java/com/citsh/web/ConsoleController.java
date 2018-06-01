package com.citsh.web;

import com.citsh.config.ChangeSubTaskCmd;
import com.citsh.config.JumpCmd;
import com.citsh.config.ListActivityCmd;
import com.citsh.config.ReOpenProcessCmd;
import com.citsh.page.Page;
import com.citsh.service.ProcessConnectorService;
import com.citsh.tenant.TenantHolder;
import com.citsh.util.DOM4JUtil;
import com.citsh.util.IoUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.IOUtils;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

@Controller
@RequestMapping({ "bpm" })
@Api(value = "bpm", description = "管理控制台")
public class ConsoleController {
	private static Logger logger = LoggerFactory.getLogger(ConsoleController.class);

	@Autowired
	private ProcessEngine processEngine;

	@Autowired
	private TenantHolder tenantHolder;

	@Autowired
	private ProcessConnectorService processConnectorService;

	@GetMapping({ "console-listDeployments" })
	@ApiOperation("部署列表")
	@ApiImplicitParam(name = "page", value = "分页对象", dataType = "Page", paramType = "query")
	public String listDeployments(@ModelAttribute Page page) {
		String tenantId = this.tenantHolder.getTenantId();
		page = this.processConnectorService.findDeployments(tenantId, page);
		Model model = new RedirectAttributesModelMap();
		model.addAttribute("page", page);
		return "bpm/console-listDeployments";
	}

	@GetMapping({ "console-listDeploymentResourceNames" })
	@ApiOperation("显示每个部署包里的资源")
	@ApiImplicitParam(name = "deploymentId", value = "部署id", dataType = "String", paramType = "query")
	public String listDeploymentResourceNames(@RequestParam("deploymentId") String deploymentId) {
		RepositoryService repositoryService = this.processEngine.getRepositoryService();

		List deploymentResourceNames = repositoryService.getDeploymentResourceNames(deploymentId);

		Model model = new RedirectAttributesModelMap();
		model.addAttribute("deploymentResourceNames", deploymentResourceNames);
		return "bpm/console-listDeploymentResourceNames";
	}

	@PostMapping({ "console-removeDeployment" })
	@ApiOperation("删除部署")
	@ApiImplicitParam(name = "deploymentId", value = "部署id", dataType = "String", paramType = "query")
	public String removeDeployment(@RequestParam("deploymentId") String deploymentId) {
		RepositoryService repositoryService = this.processEngine.getRepositoryService();
		repositoryService.deleteDeployment(deploymentId, true);
		return "redirect:/bpm/console-listDeployments.do";
	}

	@GetMapping({ "console-graphProcessDefinition" })
	@ApiOperation("显示流程定义图形")
	@ApiImplicitParam(name = "processDefinitionId", value = "流程实例id", dataType = "String", paramType = "query")
	public void graphProcessDefinition(@RequestParam("processDefinitionId") String processDefinitionId,
			HttpServletResponse response) {
		Deployment deployment = (Deployment) this.processEngine.getRepositoryService().createDeploymentQuery()
				.processDefinitionKey(processDefinitionId).singleResult();

		List<String> list = this.processEngine.getRepositoryService().getDeploymentResourceNames(deployment.getId());

		ProcessDefinition processDefinition = (ProcessDefinition) this.processEngine.getRepositoryService()
				.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		String nameimg = processDefinition.getName();
		System.err.println("processDefinition.getName():" + nameimg);

		String resourceName = "";
		if ((list != null) && (list.size() > 0)) {
			for (String name : list) {
				if (name.indexOf(".png") >= 0) {
					resourceName = name;
				}
			}

		}

		InputStream in = this.processEngine.getRepositoryService().getResourceAsStream(deployment.getId(),
				resourceName);
		response.setContentType("image/png");
		try {
			IOUtils.copy(in, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@PostMapping({ "console-process-upload" })
	@ApiOperation("上传发布流程定义")
	@ApiImplicitParam(name = "file", value = "流程实例id", dataType = "file", paramType = "query")
	public String processUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes)
			throws Exception {
		String tenantId = this.tenantHolder.getTenantId();
		String fileName = file.getOriginalFilename();

		Deployment deployment = this.processEngine.getRepositoryService().createDeployment()
				.addInputStream(fileName, file.getInputStream()).tenantId(tenantId).deploy();
		return "redirect:/bpm/console-listProcessDefinitions.do";
	}

	@GetMapping({ "console-listProcessDefinitions" })
	@ApiOperation("显示流程定义列表")
	@ApiImplicitParam(name = "page", value = "分页", dataType = "Page", paramType = "query")
	public String listProcessDefinitions(@ModelAttribute Page page) {
		String tenantId = this.tenantHolder.getTenantId();
		page = this.processConnectorService.findProcessDefinitions(tenantId, page);
		Model model = new RedirectAttributesModelMap();
		model.addAttribute("page", page);
		return "bpm/console-listProcessDefinitions";
	}

	@PostMapping({ "console-suspendProcessDefinition" })
	@ApiOperation("暂停流程定义")
	@ApiImplicitParam(name = "processDefinitionId", value = "流程实例id", dataType = "String", paramType = "query")
	public String suspendProcessDefinition(@RequestParam("processDefinitionId") String processDefinitionId) {
		RepositoryService repositoryService = this.processEngine.getRepositoryService();
		repositoryService.suspendProcessDefinitionById(processDefinitionId, true, null);

		return "redirect:/bpm/console-listProcessDefinitions.do";
	}

	@PostMapping({ "console-activeProcessDefinition" })
	@ApiOperation("恢复流程定义")
	@ApiImplicitParam(name = "processDefinitionId", value = "流程实例id", dataType = "String", paramType = "query")
	public String activeProcessDefinition(@RequestParam("processDefinitionId") String processDefinitionId) {
		RepositoryService repositoryService = this.processEngine.getRepositoryService();
		repositoryService.activateProcessDefinitionById(processDefinitionId, true, null);

		return "redirect:/bpm/console-listProcessDefinitions.do";
	}

	@RequestMapping({ "console-listProcessInstances" })
	@ApiOperation("显示流程实例列表")
	@ApiImplicitParam(name = "page", value = "分页", dataType = "Page", paramType = "query")
	public String listProcessInstances(@ModelAttribute Page page) {
		String tenantId = this.tenantHolder.getTenantId();
		page = this.processConnectorService.findProcessInstances(tenantId, page);
		Model model = new RedirectAttributesModelMap();
		model.addAttribute("page", page);
		return "bpm/console-listProcessInstances";
	}

	@RequestMapping({ "console-removeProcessInstance" })
	public String removeProcessInstance(@RequestParam("processInstanceId") String processInstanceId,
			@RequestParam("deleteReason") String deleteReason) {
		RuntimeService runtimeService = this.processEngine.getRuntimeService();
		runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
		return "redirect:/bpm/console-listProcessInstances.do";
	}

	@RequestMapping({ "console-suspendProcessInstance" })
	public String suspendProcessInstance(@RequestParam("processInstanceId") String processInstanceId) {
		RuntimeService runtimeService = this.processEngine.getRuntimeService();
		runtimeService.suspendProcessInstanceById(processInstanceId);
		return "redirect:/bpm/console-listProcessInstances.do";
	}

	@RequestMapping({ "console-activeProcessInstance" })
	public String activeProcessInstance(@RequestParam("processInstanceId") String processInstanceId) {
		RuntimeService runtimeService = this.processEngine.getRuntimeService();
		runtimeService.activateProcessInstanceById(processInstanceId);

		return "redirect:/bpm/console-listProcessInstances.do";
	}

	@RequestMapping({ "console-deleteProcessInstance" })
	public String deleteProcessInstance(@RequestParam("id") String id) {
		this.processEngine.getRuntimeService().deleteProcessInstance(id, "delete");
		this.processEngine.getHistoryService().deleteHistoricProcessInstance(id);
		return "redirect:/bpm/console-listProcessInstances.do";
	}

	@RequestMapping({ "console-listTasks" })
	public String listTasks(@ModelAttribute Page page, Model model) {
		String tenantId = this.tenantHolder.getTenantId();
		page = this.processConnectorService.findTasks(tenantId, page);
		model.addAttribute("page", page);
		return "bpm/console-listTasks";
	}

	@RequestMapping({ "console-listHistoricProcessInstances" })
	public String listHistoricProcessInstances(@ModelAttribute Page page, Model model) {
		String tenantId = this.tenantHolder.getTenantId();
		page = this.processConnectorService.findHistoricProcessInstances(tenantId, page);

		model.addAttribute("page", page);

		return "bpm/console-listHistoricProcessInstances";
	}

	@RequestMapping({ "console-listHistoricActivityInstances" })
	public String listHistoricActivityInstances(@ModelAttribute Page page, Model model) {
		String tenantId = this.tenantHolder.getTenantId();
		page = this.processConnectorService.findHistoricActivityInstances(tenantId, page);
		model.addAttribute("page", page);
		return "bpm/console-listHistoricActivityInstances";
	}

	@RequestMapping({ "console-listHistoricTasks" })
	public String listHistoricTasks(@ModelAttribute Page page, Model model) {
		String tenantId = this.tenantHolder.getTenantId();
		page = this.processConnectorService.findHistoricTaskInstances(tenantId, page);
		model.addAttribute("page", page);

		return "bpm/console-listHistoricTasks";
	}

	@RequestMapping({ "console-prepareJump" })
	public String prepareJump(@RequestParam("executionId") String executionId) {
		Command cmd = new ListActivityCmd(executionId);
		Map activityMap = (Map) this.processEngine.getManagementService().executeCommand(cmd);
		Model model = new RedirectAttributesModelMap();
		model.addAttribute("activityMap", activityMap);
		return "bpm/console-prepareJump";
	}

	@RequestMapping({ "console-jump" })
	public String jump(@RequestParam("executionId") String executionId, @RequestParam("activityId") String activityId) {
		Command cmd = new JumpCmd(executionId, activityId);
		this.processEngine.getManagementService().executeCommand(cmd);
		return "redirect:/bpm/console-listTasks.do";
	}

	@RequestMapping({ "console-beforeUpdateProcess" })
	public String beforeUpdateProcess(@RequestParam("processDefinitionId") String processDefinitionId, Model model)
			throws Exception {
		ProcessDefinition processDefinition = this.processEngine.getRepositoryService()
				.getProcessDefinition(processDefinitionId);

		InputStream is = this.processEngine.getRepositoryService()
				.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getResourceName());
		String xml = IoUtils.readString(is);

		model.addAttribute("xml", xml);

		return "bpm/console-beforeUpdateProcess";
	}

	@RequestMapping({ "console-migrateInput" })
	public String migrateInput(@RequestParam("processInstanceId") String processInstanceId, Model model) {
		model.addAttribute("processInstanceId", processInstanceId);
		model.addAttribute("processDefinitions",
				this.processEngine.getRepositoryService().createProcessDefinitionQuery().list());

		return "bpm/console-migrateInput";
	}

	@RequestMapping({ "console-reopen" })
	public String reopen(@RequestParam("processInstanceId") String processInstanceId) {
		this.processEngine.getManagementService().executeCommand(new ReOpenProcessCmd(processInstanceId));

		return "redirect:/bpm/console-listHistoricProcessInstances.do";
	}

	@RequestMapping({ "console-addSubTaskInput" })
	public String addSubTaskInput(@RequestParam("taskId") String taskId) {
		return "bpm/console-addSubTaskInput";
	}

	@RequestMapping({ "console-addSubTask" })
	public String addSubTask(@RequestParam("taskId") String taskId, @RequestParam("userId") String userId) {
		this.processEngine.getManagementService().executeCommand(new ChangeSubTaskCmd(taskId, userId));

		return "redirect:/bpm/console-listTasks.do";
	}

	@RequestMapping({ "console-completeTask" })
	public String completeTask(@RequestParam("taskId") String taskId) {
		Map map = DOM4JUtil.completemission(taskId);
		this.processEngine.getTaskService().complete(taskId, map);
		return "redirect:/bpm/console-listTasks.do";
	}
}