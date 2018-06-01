package com.citsh.form.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.citsh.auto.CurrentUserHolder;
import com.citsh.file.GridFS;
import com.citsh.form.conf.Xform;
import com.citsh.form.conf.XformBuilder;
import com.citsh.form.entity.FormTemplate;
import com.citsh.form.service.FormTemplateService;
import com.citsh.from.FormConnector;
import com.citsh.from.FormDTO;
import com.citsh.id.IdGenerator;
import com.citsh.keyvalue.entity.Record;
import com.citsh.keyvalue.service.KeyValueService;
import com.citsh.page.Page;
import com.citsh.query.PropertyFilter;
import com.citsh.tenant.TenantHolder;
import com.citsh.util.BeanMapper;

@Controller
@RequestMapping("form")
public class FormTemplateController {
	@Autowired
	private TenantHolder tenantHolder;
	@Autowired
	private FormTemplateService formTemplateService;
	@Autowired
	private CurrentUserHolder currentUserHolder;
	private BeanMapper beanMapper = new BeanMapper();
	@Autowired
	private IdGenerator idGenerator;
	@Autowired
	private FormConnector formConnector;
	@Autowired
	private KeyValueService keyValueService;
	@Autowired
	private GridFS gridFS;

	@GetMapping("form-template-list")
	public String list(@ModelAttribute Page page, @RequestParam Map<String, Object> parameterMap, Model model) {
		String tenantId = tenantHolder.getTenantId();
		List<PropertyFilter> propertyFilters = PropertyFilter.buildFromMap(parameterMap);
		propertyFilters.add(new PropertyFilter("EQS_tenantId", tenantId));
		page = formTemplateService.pageQuery(page, propertyFilters, new Query());
		model.addAttribute("page", page);
		return "system/formDesign";
	}

	@PostMapping("form-template-input")
	public String input(@RequestParam(value = "id", required = false) Long id, Model model) {
		if (id != null) {
			FormTemplate formTemplate = formTemplateService.findById(id);
			model.addAttribute("model", formTemplate);
		}
		return "form/form-template-input";
	}

	@PostMapping("form-template-save")
	public String save(@ModelAttribute FormTemplate formTemplate, @RequestParam Map<String, Object> parameterMap,
			RedirectAttributes redirectAttributes) {
		String userId = currentUserHolder.getUserId();
		String tenantId = tenantHolder.getTenantId();
		FormTemplate dest = null;
		Long id = formTemplate.getId();
		if (id != null) {
			dest = formTemplateService.findById(id);
			beanMapper.copy(formTemplate, dest);
		} else {
			dest = formTemplate;
			dest.setType(0);
			dest.setCreateTime(new Date());
			dest.setUserId(userId);
			dest.setTenantId(tenantId);
			dest.setId(idGenerator.generateId());
		}
		formTemplate = formTemplateService.save(dest);
		if (formTemplate == null) {
			return null;
		}
		return "redirect:/form/form-template-list.do";
	}

	@PostMapping("form-template-remove")
	public String remove(@RequestParam("selectedItem") List<Long> selectedItem, RedirectAttributes redirectAttributes) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").in(selectedItem));
		FormTemplate formTemplate = formTemplateService.remove(query);
		if (formTemplate == null) {
			return null;
		}
		return "redirect:/form/form-template-list.do";
	}

	@GetMapping("form-preview")
	public String preview(@RequestParam("code") String code, Model model) {
		String tenantId = tenantHolder.getTenantId();
		FormDTO formDTO = formConnector.findForm(code, tenantId);
		Record record = keyValueService.findByRef(code);
		if (record == null) {
			record = new Record();
			record.setId(idGenerator.generateId());
			record.setName(formDTO.getName());
			record.setRef(formDTO.getCode());
			keyValueService.save(record);
		}
		model.addAttribute("record", record);
		Xform xform = new XformBuilder().setGridFS(gridFS).setContent(formDTO.getContent()).setRecord(record).build();
		model.addAttribute("xform", xform);

		return "system/formDesign_view";
	}
}
