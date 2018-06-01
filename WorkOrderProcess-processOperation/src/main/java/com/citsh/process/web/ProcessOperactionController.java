package com.citsh.process.web;

import com.citsh.auto.CurrentUserHolder;
import com.citsh.button.entity.ButtonInfo;
import com.citsh.button.service.ButtonInfoService;
import com.citsh.file.GridFS;
import com.citsh.form.config.Xform;
import com.citsh.form.config.XformBuilder;
import com.citsh.from.FormConnector;
import com.citsh.from.FormDTO;
import com.citsh.handler.MultipartHandler;
import com.citsh.humantask.HumanTaskConnector;
import com.citsh.keyvalue.FormParameter;
import com.citsh.keyvalue.config.RecordBuilder;
import com.citsh.keyvalue.entity.Prop;
import com.citsh.keyvalue.entity.Record;
import com.citsh.keyvalue.service.KeyValueService;
import com.citsh.page.Page;
import com.citsh.process.ProcessConnector;
import com.citsh.process.ProcessDTO;
import com.citsh.process.service.ProcessOperactionService;
import com.citsh.tenant.TenantHolder;
import com.citsh.user.UserConnector;
import com.citsh.util.JsonUtil;
import com.mongodb.gridfs.GridFSFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartResolver;

@Controller
@RequestMapping({ "operation" })
public class ProcessOperactionController {
	private static Logger logger = LoggerFactory.getLogger(ProcessOperactionController.class);
	public static final int STATUS_DRAFT_PROCESS = 0;
	public static final int STATUS_DRAFT_TASK = 1;
	public static final int STATUS_RUNNING = 2;
	private CurrentUserHolder currentUserHolder;

	@Autowired
	private TenantHolder tenantHolder;
	private MultipartResolver multipartResolver;

	@Autowired
	private KeyValueService keyValueService;

	@Autowired
	private ProcessConnector processConnector;

	@Autowired
	private ProcessOperactionService processOperactionService;

	@Autowired
	private GridFS gridFS;

	@Autowired
	private ButtonInfoService buttonInfoService;
	private JsonUtil jsonUtil = new JsonUtil();

	@Autowired
	private FormConnector formConnector;

	@Autowired
	private UserConnector userConnector;

	@Autowired
	private HumanTaskConnector humanTaskConnector;

	@PostMapping({ "process-operation-saveDraft" })
	public String saveDraft(HttpServletRequest request) throws Exception {
		doSaveRecord(request);
		return "operation/process-operation-saveDraft";
	}

	@GetMapping({ "process-operation-listDrafts" })
	public String listDrafts(@ModelAttribute Page page, Model model) throws Exception {
		String userId = this.currentUserHolder.getUserId();
		String tenantId = this.tenantHolder.getTenantId();
		page = this.keyValueService.pagedQuery(page, 0, userId, tenantId);
		model.addAttribute("page", page);

		return "operation/process-operation-listDrafts";
	}

	@PostMapping({ "process-operation-removeDraft" })
	public String removeDraft(@RequestParam("code") String code) {
		this.keyValueService.removeByCode(code);
		return "redirect:/operation/process-operation-listDrafts.do";
	}

	public String viewStartForm(HttpServletRequest request, @RequestParam("bpmProcessId") String bpmProcessId,
			@RequestParam(value = "businessKey", required = false) String businessKey, Model model) {
		String tenantId = this.tenantHolder.getTenantId();
		FormParameter formParameter = new FormParameter();
		formParameter.setBpmProcessId(bpmProcessId);
		formParameter.setBusinessKey(businessKey);

		ProcessDTO processDTO = this.processConnector.findProcess(bpmProcessId);

		String processDefinitionId = processDTO.getProcessDefinitionId();

		FormDTO formDTO = this.processConnector.findStartForm(processDefinitionId);
		formParameter.setFormDto(formDTO);

		if (formDTO.isExists()) {
			if (formDTO.isRedirect()) {
				String redirectUrl = formDTO.getUrl() + "?processDefinitionId=" + formDTO.getProcessDefinitionId();
				return "redirect:" + redirectUrl;
			}

			if (processDTO.isConfigTask()) {
				formParameter.setNextStep("taskConf");
			} else
				formParameter.setNextStep("confirmStartProcess");

			return doViewStartForm(formParameter, model, tenantId);
		}
		if (processDTO.isConfigTask()) {
			formParameter.setProcessDefinitionId(processDefinitionId);
			return doTaskConf(formParameter, model);
		}

		return doConfirmStartProcess(formParameter, model);
	}

	@RequestMapping({ "process-operation-taskConf" })
	public String taskConf(HttpServletRequest request, Model model) {
		FormParameter formParameter = doSaveRecord(request);
		ProcessDTO processDTO = this.processConnector.findProcess(formParameter.getBpmProcessId());
		String processDefinitionId = processDTO.getProcessDefinitionId();
		formParameter.setProcessDefinitionId(processDefinitionId);
		if (processDTO.isConfigTask()) {
			formParameter.setNextStep("confirmStartProcess");
			return doTaskConf(formParameter, model);
		}

		return doConfirmStartProcess(formParameter, model);
	}

	@RequestMapping({ "process-operation-confirmStartProcess" })
	public String confirmStartProcess(HttpServletRequest request, Model model) {
		FormParameter formParameter = doSaveRecord(request);
		formParameter.setNextStep("startProcessInstance");
		doConfirmStartProcess(formParameter, model);
		return "operation/process-operation-confirmStartProcess";
	}

	@RequestMapping({ "process-operation-startProcessInstance" })
	public String startProcessInstance(HttpServletRequest request, Model model) {
		FormParameter formParameter = doSaveRecord(request);
		doConfirmStartProcess(formParameter, model);
		Record record = this.keyValueService.findByCode(formParameter.getBusinessKey());
		ProcessDTO processDto = this.processConnector.findProcess(formParameter.getBpmProcessId());
		String processDefinitionId = processDto.getProcessDefinitionId();
		FormDTO formDTO = this.processConnector.findStartForm(processDefinitionId);

		Xform xform = new XformBuilder().setGridFS(this.gridFS).setUserConnector(this.userConnector)
				.setContent(formDTO.getContent()).setRecord(record).build();
		Map processParameters = null;
		try {
			processParameters = xform.getMapData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("processParameters : {}", processParameters);
		String userId = this.currentUserHolder.getUserId();
		String businessKey = formParameter.getBusinessKey();
		this.processOperactionService.startProcessInstance(userId, businessKey, processDefinitionId, processParameters,
				record);

		return "operation/process-operation-startProcessInstance";
	}

	public FormParameter doSaveRecord(HttpServletRequest request) {
		String userId = this.currentUserHolder.getUserId();
		String tenantId = this.tenantHolder.getTenantId();

		MultipartHandler multipartHandler = new MultipartHandler(this.multipartResolver);
		FormParameter formParameter = null;
		try {
			multipartHandler.handle(request);
			logger.debug("multiValueMap : {}", multipartHandler.getMultiValueMap());
			logger.debug("multiFileMap : {}", multipartHandler.getMultiFileMap());

			formParameter = buildFormParameter(multipartHandler);

			String businessKey = this.processOperactionService.saveDraft(userId, tenantId, formParameter);

			if ((formParameter.getBusinessKey() == null) || ("".equals(formParameter.getBusinessKey().trim()))) {
				formParameter.setBusinessKey(businessKey);
			}

			Record record = this.keyValueService.findByCode(businessKey);

			record = new RecordBuilder().build(record, multipartHandler, this.gridFS, tenantId);
			this.keyValueService.save(record);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			multipartHandler.clear();
		}

		return null;
	}

	public String doViewStartForm(FormParameter formParameter, Model model, String tenantId) {
		model.addAttribute("formDto", formParameter.getFormDto());
		model.addAttribute("bpmProcessId", formParameter.getBpmProcessId());
		model.addAttribute("businessKey", formParameter.getBusinessKey());
		model.addAttribute("nextStep", formParameter.getNextStep());

		List buttons = new ArrayList();
		List buttonInfos = new ArrayList();

		ButtonInfo buttonInfo = this.buttonInfoService.listBySQL("code=?", new Object[] { "saveDraft" });
		ButtonInfo buttonInfoNextStep = this.buttonInfoService.listBySQL("code=?",
				new Object[] { formParameter.getNextStep() });
		buttonInfos.add(buttonInfo);
		buttonInfos.add(buttonInfoNextStep);

		model.addAttribute("buttons", buttonInfos);

		String json = findStartFormData(formParameter.getBusinessKey());
		if (json != null) {
			model.addAttribute("json", json);
		}

		Record record = this.keyValueService.findByCode(formParameter.getBusinessKey());

		FormDTO formDTO = this.formConnector.findForm(formParameter.getFormDto().getCode(), tenantId);

		if (record != null) {
			Xform xform = new XformBuilder().setGridFS(this.gridFS).setUserConnector(this.userConnector)
					.setContent(formDTO.getContent()).setRecord(record).build();
			model.addAttribute("xform", xform);
		} else {
			Xform xform = new XformBuilder().setContent(formDTO.getContent()).build();
			model.addAttribute("xform", xform);
		}
		return "operation/process-operation-viewStartForm";
	}

	public String findStartFormData(String businessKey) {
		Record record = this.keyValueService.findByCode(businessKey);
		if (record == null) {
			return null;
		}
		String json = null;
		Map map = new HashMap();
		for (Prop prop : record.getProps().values()) {
			if ("file".equals(prop.getCode())) {
				String filename = this.gridFS.getFile(prop.getValue()).getFilename();
				map.put(prop.getCode(), filename);
				continue;
			}
			map.put(prop.getCode(), prop.getValue());
		}
		try {
			json = this.jsonUtil.toJson(map);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

	private String doTaskConf(FormParameter formParameter, Model model) {
		model.addAttribute("bpmProcessId", formParameter.getBpmProcessId());
		model.addAttribute("businessKey", formParameter.getBusinessKey());
		model.addAttribute("nextStep", formParameter.getNextStep());

		List humanTaskDefinitions = this.humanTaskConnector
				.findHumanTaskDefinitions(formParameter.getProcessDefinitionId());
		model.addAttribute("humanTaskDefinitions", humanTaskDefinitions);
		return "operation/process-operation-taskConf";
	}

	private String doConfirmStartProcess(FormParameter formParameter, Model model) {
		this.humanTaskConnector.configTaskDefinitions(formParameter.getBusinessKey(),
				formParameter.getList("taskDefinitionKeys"), formParameter.getList("taskAssignees"));
		model.addAttribute("businessKey", formParameter.getBusinessKey());
		model.addAttribute("nextStep", formParameter.getNextStep());
		model.addAttribute("bpmProcessId", formParameter.getBpmProcessId());

		return "operation/process-operation-confirmStartProcess";
	}

	public FormParameter buildFormParameter(MultipartHandler multipartHandler) {
		FormParameter formParameter = new FormParameter();
		formParameter.setMultiValueMap(multipartHandler.getMultiValueMap());
		formParameter.setMultiFileMap(multipartHandler.getMultiFileMap());
		formParameter.setBusinessKey((String) multipartHandler.getMultiValueMap().getFirst("businessKey"));
		formParameter.setBpmProcessId((String) multipartHandler.getMultiValueMap().getFirst("bpmProcessId"));
		formParameter.setHumanTaskId((String) multipartHandler.getMultiValueMap().getFirst("humanTaskId"));

		return formParameter;
	}
}