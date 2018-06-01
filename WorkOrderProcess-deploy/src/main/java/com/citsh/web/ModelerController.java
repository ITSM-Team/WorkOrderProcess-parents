package com.citsh.web;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.citsh.cmd.SyncProcessCmd;
import com.citsh.tenant.TenantHolder;
import com.citsh.util.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Controller
@RequestMapping("/modeler")
public class ModelerController {
	private static Logger logger = LoggerFactory.getLogger(ModelerController.class);
	@Autowired
	private ProcessEngine processEngine;
	private JsonUtil jsonUtil = new JsonUtil();
	@Autowired
	private TenantHolder tenantHolder;
	
	@GetMapping("testzh")
	public String testzh(HttpServletRequest request){
		return "system/processDesign";
	}

	@GetMapping("modeler-list")
	public String list(org.springframework.ui.Model model) {
		List<Model> models = processEngine.getRepositoryService().createModelQuery().list();
		model.addAttribute("models", models);
		 return "system/processDesign";
	}

	/**
	 * 新建模型创建模板
	 */
	@GetMapping("modeler-open")
	public String open(@RequestParam(value = "id", required = false) String id) {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		Model model = null;
		if (!StringUtils.isEmpty(id)) {
			model = repositoryService.getModel(id);
		} else {
			model = repositoryService.newModel();
			repositoryService.saveModel(model);
			id = model.getId();
		}
		return "redirect:/cdn/modeler/modeler.html?modelId=" + id;
	}

	/**
	 * 删除
	 */
	@PostMapping("modeler-remove")
	public String remove(@RequestParam("id") String id) {
		processEngine.getRepositoryService().deleteModel(id);

		return "redirect:/modeler/modeler-list.do";
	}

	/**
	 * 发布
	 */
	@PostMapping("/modeler-deploy")
	public String deploy(@RequestParam("id") String id, org.springframework.ui.Model theModel) throws Exception {
		// 暂时
		String tenantId = tenantHolder.getTenantId();
		RepositoryService repositoryService = processEngine.getRepositoryService();
		Model modelData = repositoryService.getModel(id);
		byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());

		if (bytes == null) {
			theModel.addAttribute("message", "模型数据为空，请先设计流程并成功保存，再进行发布。");

			return "modeler/failure";
		}

		JsonNode modelNode = (JsonNode) new ObjectMapper().readTree(bytes);
		byte[] bpmnBytes = null;

		BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
		bpmnBytes = new BpmnXMLConverter().convertToXML(model);

		String processName = modelData.getName() + ".bpmn20.xml";
		Deployment deployment = repositoryService.createDeployment().name(modelData.getName())
				.addString(processName, new String(bpmnBytes, "UTF-8")).tenantId(tenantId).deploy();
		modelData.setDeploymentId(deployment.getId());
		repositoryService.saveModel(modelData);

		List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
				.deploymentId(deployment.getId()).list();

		for (ProcessDefinition processDefinition : processDefinitions) {
			processEngine.getManagementService().executeCommand(new SyncProcessCmd(processDefinition.getId()));
		}

		return "redirect:/modeler/modeler-list.do";
	}

	@GetMapping("model/{modelId}/json")
	@ResponseBody
	public String openModel(@PathVariable("modelId") String modelId) throws Exception {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		Model model = repositoryService.getModel(modelId);

		if (model == null) {
			logger.info("model({}) is null", modelId);
			model = repositoryService.newModel();
			repositoryService.saveModel(model);
		}

		Map root = new HashMap();
		root.put("modelId", model.getId());
		root.put("name", "name");
		root.put("revision", 1);
		root.put("description", "description");

		byte[] bytes = repositoryService.getModelEditorSource(model.getId());

		if (bytes != null) {
			String modelEditorSource = new String(bytes, "utf-8");
			logger.info("modelEditorSource : {}", modelEditorSource);

			Map modelNode = jsonUtil.fromJson(modelEditorSource, Map.class);
			root.put("model", modelNode);
		} else {
			Map modelNode = new HashMap();
			modelNode.put("id", "canvas");
			modelNode.put("resourceId", "canvas");

			Map stencilSetNode = new HashMap();
			stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
			modelNode.put("stencilset", stencilSetNode);

			logger.info("root:" + root.toString());
			model.setMetaInfo(jsonUtil.toJson(root));
			model.setName("name");
			model.setKey("key");

			root.put("model", modelNode);
		}

		logger.info("model : {}", root);

		return jsonUtil.toJson(root);
	}

	@GetMapping("editor/stencilset")
	@ResponseBody
	public String stencilset() throws Exception {
		InputStream stencilsetStream = this.getClass().getClassLoader().getResourceAsStream("stencilset.json");

		try {
			return IOUtils.toString(stencilsetStream, "utf-8");
		} catch (Exception e) {
			throw new RuntimeException("Error while loading stencil set", e);
		}
	}

	@PostMapping("model/{modelId}/save")
	@ResponseBody
	public String modelSave(@PathVariable("modelId") String modelId, @RequestParam("description") String description,
			@RequestParam("json_xml") String jsonXml, @RequestParam("name") String name,
			@RequestParam("svg_xml") String svgXml) throws Exception {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		Model model = repositoryService.getModel(modelId);
		model.setName(name);
		// model.setMetaInfo(root.toString());
		logger.info("jsonXml : {}", jsonXml);
		repositoryService.saveModel(model);
		repositoryService.addModelEditorSource(model.getId(), jsonXml.getBytes("utf-8"));

		return "{}";
	}

	@PostMapping("xml2json")
	public String xml2json(@RequestParam("processDefinitionId") String processDefinitionId) throws Exception {
		RepositoryService repositoryService = processEngine.getRepositoryService();

		ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefinitionId);

		Model model = repositoryService.newModel();
		model.setName(processDefinition.getName());
		model.setDeploymentId(processDefinition.getDeploymentId());
		repositoryService.saveModel(model);

		BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
		ObjectNode objectNode = new BpmnJsonConverter().convertToJson(bpmnModel);

		String json = objectNode.toString();

		repositoryService.addModelEditorSource(model.getId(), json.getBytes("utf-8"));
		return "redirect:/modeler/modeler-list.do";
	}

}
