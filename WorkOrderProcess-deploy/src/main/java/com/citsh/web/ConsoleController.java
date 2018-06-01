package com.citsh.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.citsh.cmd.ProcessDefinitionDiagramCmd;
import com.citsh.config.ChangeSubTaskCmd;
import com.citsh.config.JumpCmd;
import com.citsh.config.ListActivityCmd;
import com.citsh.config.ReOpenProcessCmd;
import com.citsh.dao.ProcessConnectorDao;
import com.citsh.page.Page;
import com.citsh.process.ProcessConnector;
import com.citsh.service.ProcessConnectorService;
import com.citsh.tenant.TenantHolder;
import com.citsh.util.DOM4JUtil;
import com.citsh.util.IoUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 管理控制台.
 */
@Controller
@RequestMapping("deploy")
@Api(value = "deploy", description = "管理控制台")
public class ConsoleController {
	private static Logger logger = LoggerFactory.getLogger(ConsoleController.class);
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private TenantHolder tenantHolder;
	@Autowired
	private ProcessConnector processConnector;

	/**
	 * 部署列表.
	 */
	@GetMapping("listDeployments")
	@ApiOperation(value = "部署列表")
	@ApiImplicitParam(name = "page", value = "分页对象", dataType = "Page", paramType = "query")
	public String listDeployments(@ModelAttribute Page page,Model model) {
		String tenantId = tenantHolder.getTenantId();
		page = processConnector.findDeployments(tenantId, page);
		model.addAttribute("page", page);
		return "system/processDeployment";
	}

	/**
	 * 显示每个部署包里的资源.
	 */
	@GetMapping("listDeploymentResourceNames")
	@ApiOperation(value = "显示每个部署包里的资源")
	@ApiImplicitParam(name = "deploymentId", value = "部署id", dataType = "String", paramType = "query")
	public String listDeploymentResourceNames(@RequestParam("deploymentId") String deploymentId,Model model) {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		List<String> deploymentResourceNames = repositoryService.getDeploymentResourceNames(deploymentId);
		model.addAttribute("deploymentResourceNames", deploymentResourceNames);
		return "system/processDeployment_view";
	}

	/**
	 * 删除部署
	 */
	@PostMapping("removeDeployment")
	@ApiOperation(value = "删除部署")
	@ApiImplicitParam(name = "deploymentId", value = "部署id", dataType = "String", paramType = "query")
	public String removeDeployment(@RequestParam("deploymentId") String deploymentId) {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.deleteDeployment(deploymentId, true);
		return "redirect:/bpm/console-listDeployments.do";
	}

	/**
	 * 显示流程定义图形.
	 */
	@GetMapping("graphProcessDefinition")
	@ApiOperation(value = "显示流程定义图形")
	@ApiImplicitParam(name = "processDefinitionId", value = "流程实例id", dataType = "String", paramType = "query")
	public void graphProcessDefinition(@RequestParam("processDefinitionId") String processDefinitionId,
			HttpServletResponse response) {
		Command<InputStream> cmd = new ProcessDefinitionDiagramCmd(processDefinitionId);
		InputStream inputStream = processEngine.getManagementService().executeCommand(cmd);
		response.setContentType("image/png");
		try {
			IOUtils.copy(inputStream, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 显示流程定义xml
	 */
	@GetMapping("viewXml")
	public void viewXml(@RequestParam("processDefinitionId") String processDefinitionId, HttpServletResponse response) {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId).singleResult();
		String resourceName = processDefinition.getResourceName();
		InputStream inputStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
		response.setContentType("text/xml;charset=UTF-8");
		try {
			IOUtils.copy(inputStream, response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 上传发布流程定义.
	 */
	@PostMapping("process-upload")
	@ApiOperation(value = "上传发布流程定义")
	@ApiImplicitParam(name = "file", value = "流程实例id", dataType = "file", paramType = "query")
	public String processUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes)
			throws Exception {
		String tenantId = tenantHolder.getTenantId();
		String fileName = file.getOriginalFilename();
		Deployment deployment = processEngine.getRepositoryService().createDeployment()
				.addInputStream(fileName, file.getInputStream()).tenantId(tenantId).deploy();
		return "redirect:/bpm/console-listProcessDefinitions.do";
	}

	/**
	 * 显示流程定义列表.
	 */
	@GetMapping("listProcessDefinitions")
	@ApiOperation(value = "显示流程定义列表")
	@ApiImplicitParam(name = "page", value = "分页", dataType = "Page", paramType = "query")
	public String listProcessDefinitions(@ModelAttribute Page page, Model model) {
		String tenantId = tenantHolder.getTenantId();
		page = processConnector.findProcessDefinitions(tenantId, page);
		model.addAttribute("page", page);
		return "system/processDefinition";
	}

	/**
	 * 暂停流程定义.
	 */
	@PostMapping("suspendProcessDefinition")
	@ResponseBody
	@ApiOperation(value = "暂停流程定义")
	@ApiImplicitParam(name = "processDefinitionId", value = "流程实例id", dataType = "String", paramType = "query")
	public String suspendProcessDefinition(@RequestParam("processDefinitionId") String processDefinitionId) {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.suspendProcessDefinitionById(processDefinitionId, true, null);
		return "success";
	}

	/**
	 * 恢复流程定义.
	 */
	@PostMapping("activeProcessDefinition")
	@ResponseBody
	@ApiOperation(value = "恢复流程定义")
	@ApiImplicitParam(name = "processDefinitionId", value = "流程实例id", dataType = "String", paramType = "query")
	public String activeProcessDefinition(@RequestParam("processDefinitionId") String processDefinitionId) {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.activateProcessDefinitionById(processDefinitionId, true, null);
		return "success";
	}

	/**
	 * 显示流程实例列表.
	 */
	@RequestMapping("listProcessInstances")
	@ApiOperation(value = "显示流程实例列表")
	@ApiImplicitParam(name = "page", value = "分页", dataType = "Page", paramType = "query")
	public String listProcessInstances(@ModelAttribute Page page) {
		String tenantId = tenantHolder.getTenantId();
		page = processConnector.findProcessInstances(tenantId, page);
		Model model = new RedirectAttributesModelMap();
		model.addAttribute("page", page);
		return "system/processDefinition";
	}

	/**
	 * 删除流程实例.
	 */
	@RequestMapping("removeProcessInstance")
	public String removeProcessInstance(@RequestParam("processInstanceId") String processInstanceId,
			@RequestParam("deleteReason") String deleteReason) {
		RuntimeService runtimeService = processEngine.getRuntimeService();
		runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
		return "redirect:/bpm/console-listProcessInstances.do";
	}

	/**
	 * 暂停流程实例.
	 */
	@RequestMapping("suspendProcessInstance")
	public String suspendProcessInstance(@RequestParam("processInstanceId") String processInstanceId) {
		RuntimeService runtimeService = processEngine.getRuntimeService();
		runtimeService.suspendProcessInstanceById(processInstanceId);
		return "redirect:/bpm/console-listProcessInstances.do";
	}

	/**
	 * 恢复流程实例.
	 */
	@RequestMapping("activeProcessInstance")
	public String activeProcessInstance(@RequestParam("processInstanceId") String processInstanceId) {
		RuntimeService runtimeService = processEngine.getRuntimeService();
		runtimeService.activateProcessInstanceById(processInstanceId);

		return "redirect:/bpm/console-listProcessInstances.do";
	}

	/**
	 * 删除流程实例，包含历史.
	 */
	@RequestMapping("deleteProcessInstance")
	public String deleteProcessInstance(@RequestParam("id") String id) {
		processEngine.getRuntimeService().deleteProcessInstance(id, "delete");
		processEngine.getHistoryService().deleteHistoricProcessInstance(id);
		return "redirect:/bpm/console-listProcessInstances.do";
	}

	/**
	 * 显示任务列表.
	 */
	@RequestMapping("listTasks")
	public String listTasks(@ModelAttribute Page page, Model model) {
		String tenantId = tenantHolder.getTenantId();
		page = processConnector.findTasks(tenantId, page);
		model.addAttribute("page", page);
		return "bpm/console-listTasks";
	}

	// The task cannot be deleted because is part of a running process
	/**
	 * 显示历史流程实例.
	 */
	@RequestMapping("listHistoricProcessInstances")
	public String listHistoricProcessInstances(@ModelAttribute Page page, Model model) {
		String tenantId = tenantHolder.getTenantId();
		page = processConnector.findHistoricProcessInstances(tenantId, page);

		model.addAttribute("page", page);

		return "bpm/console-listHistoricProcessInstances";
	}

	/**
	 * 显示历史节点实例.
	 */
	@RequestMapping("listHistoricActivityInstances")
	public String listHistoricActivityInstances(@ModelAttribute Page page, Model model) {
		String tenantId = tenantHolder.getTenantId();
		page = processConnector.findHistoricActivityInstances(tenantId, page);
		model.addAttribute("page", page);
		return "bpm/console-listHistoricActivityInstances";
	}

	/**
	 * 显示历史任务.
	 */
	@RequestMapping("listHistoricTasks")
	public String listHistoricTasks(@ModelAttribute Page page, Model model) {
		String tenantId = tenantHolder.getTenantId();
		page = processConnector.findHistoricTaskInstances(tenantId, page);
		model.addAttribute("page", page);

		return "bpm/console-listHistoricTasks";
	}

	// ~ ======================================================================
	/**
	 * 自由流执行之前，选择跳转到哪个节点.
	 */
	@RequestMapping("prepareJump")
	public String prepareJump(@RequestParam("executionId") String executionId) {
		Command<Map<String, String>> cmd = new ListActivityCmd(executionId);
		Map activityMap = processEngine.getManagementService().executeCommand(cmd);
		Model model = new RedirectAttributesModelMap();
		model.addAttribute("activityMap", activityMap);
		return "bpm/console-prepareJump";
	}

	/**
	 * 自由流.自由跳转
	 */
	@RequestMapping("jump")
	public String jump(@RequestParam("executionId") String executionId, @RequestParam("activityId") String activityId) {
		Command<Object> cmd = new JumpCmd(executionId, activityId);
		processEngine.getManagementService().executeCommand(cmd);
		return "redirect:/bpm/console-listTasks.do";
	}

	/**
	 * 更新流程之前，填写xml.
	 */
	@RequestMapping("beforeUpdateProcess")
	public String beforeUpdateProcess(@RequestParam("processDefinitionId") String processDefinitionId, Model model)
			throws Exception {
		ProcessDefinition processDefinition = processEngine.getRepositoryService()
				.getProcessDefinition(processDefinitionId);
		InputStream is = processEngine.getRepositoryService().getResourceAsStream(processDefinition.getDeploymentId(),
				processDefinition.getResourceName());
		String xml = IoUtils.readString(is);

		model.addAttribute("xml", xml);

		return "bpm/console-beforeUpdateProcess";
	}

	/**
	 * 更新流程，不生成新版本.
	 */
	/*
	 * @RequestMapping("console-doUpdateProcess") public String doUpdateProcess(
	 * 
	 * @RequestParam("processDefinitionId") String processDefinitionId,
	 * 
	 * @RequestParam("xml") String xml) throws Exception { byte[] bytes =
	 * xml.getBytes("utf-8"); UpdateProcessCmd updateProcessCmd = new
	 * UpdateProcessCmd( processDefinitionId, bytes);
	 * processEngine.getManagementService().executeCommand(updateProcessCmd);
	 * 
	 * return "redirect:/bpm/console-listProcessInstances.do"; }
	 */

	/**
	 * 准备迁移流程.
	 */
	@RequestMapping("migrateInput")
	public String migrateInput(@RequestParam("processInstanceId") String processInstanceId, Model model) {
		model.addAttribute("processInstanceId", processInstanceId);
		model.addAttribute("processDefinitions",
				processEngine.getRepositoryService().createProcessDefinitionQuery().list());

		return "bpm/console-migrateInput";
	}

	/**
	 * 迁移流程实例.
	 */
	/*
	 * @RequestMapping("console-migrateSave") public String migrateInput(
	 * 
	 * @RequestParam("processInstanceId") String processInstanceId,
	 * 
	 * @RequestParam("processDefinitionId") String processDefinitionId) {
	 * processEngine.getManagementService().executeCommand( new
	 * MigrateCmd(processInstanceId, processDefinitionId));
	 * 
	 * return "redirect:/bpm/console-listProcessInstances.do"; }
	 */

	/**
	 * 重新开启流程.
	 */
	@RequestMapping("reopen")
	public String reopen(@RequestParam("processInstanceId") String processInstanceId) {
		processEngine.getManagementService().executeCommand(new ReOpenProcessCmd(processInstanceId));

		return "redirect:/bpm/console-listHistoricProcessInstances.do";
	}

	/**
	 * 添加子任务，之前，设置添加的子任务的执行人.
	 */
	@RequestMapping("addSubTaskInput")
	public String addSubTaskInput(@RequestParam("taskId") String taskId) {
		return "bpm/console-addSubTaskInput";
	}

	/**
	 * 添加子任务.
	 */
	@RequestMapping("addSubTask")
	public String addSubTask(@RequestParam("taskId") String taskId, @RequestParam("userId") String userId) {
		processEngine.getManagementService().executeCommand(new ChangeSubTaskCmd(taskId, userId));
		return "redirect:/bpm/console-listTasks.do";
	}

	/**
	 * 直接完成任务.
	 */
	@RequestMapping("completeTask")
	public String completeTask(@RequestParam("taskId") String taskId) {
		Map<String, Object> map = DOM4JUtil.completemission(taskId);
		processEngine.getTaskService().complete(taskId, map);
		return "redirect:/bpm/console-listTasks.do";
	}

}
