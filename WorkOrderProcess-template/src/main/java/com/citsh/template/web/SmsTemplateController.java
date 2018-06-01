package com.citsh.template.web;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.citsh.page.Page;
import com.citsh.query.PropertyFilter;
import com.citsh.template.entity.SmsTemplate;
import com.citsh.template.service.SmsTemplateService;
import com.citsh.tenant.TenantHolder;

@Controller
@RequestMapping("/system")
public class SmsTemplateController {

	@Autowired
	private TenantHolder tenantHolder;

	@Autowired
	private SmsTemplateService smsTemplateService;

	/**
	 * 分页查询短信模版信息
	 */
	@RequestMapping(value = "/smsTemplate", method = RequestMethod.GET)
	public String paginationQueryAll(@ModelAttribute Page page, @RequestParam Map<String, Object> parameterMap,
			Model model) {
		String tenanId = tenantHolder.getTenantId();
		List<PropertyFilter> propertyFilters = PropertyFilter.buildFromMap(parameterMap);
		propertyFilters.add(new PropertyFilter("EQS_tenantId", tenanId));
		page = smsTemplateService.paginationQueryAll(page, propertyFilters);
		List<SmsTemplate> smsTemplateList = (List<SmsTemplate>) page.getResult();
		page.setResult(smsTemplateList);
		model.addAttribute("page", page);
		return "system/smsTemplate";
	}

	/**
	 * 跳转页面
	 */
	@RequestMapping(value = "/smsTemplate_input", params = "action=view")
	public String addview(@ModelAttribute Page page, Model model,
			@RequestParam(value = "id", required = false) Long id) {
		if (id == null) {
			model.addAttribute("page", page);
			return "system/smsTemplate_input";
		} else {
			SmsTemplate smsTemplate = smsTemplateService.findById(id);
			if (smsTemplate != null) {
				model.addAttribute("page", page);
				model.addAttribute("smsTemplate", smsTemplate);
				return "system/smsTemplate_input";
			}
		}
		return null;

	}

	/**
	 * 新增短信模版
	 */
	@RequestMapping(value = "/smsTemplate_input", method = RequestMethod.POST, params = "action=add")
	public String addSmsTemplate(SmsTemplate smsTemplate) {
		if (smsTemplate != null) {
			smsTemplate.setTenantId(tenantHolder.getTenantId());
			boolean flag = smsTemplateService.addSmsTemplate(smsTemplate);
			if (flag) {
				return "system/smsTemplate";
			}
		}
		return null;
	}

	/**
	 * 修改短信模版
	 */
	@RequestMapping(value = "/smsTemplate_input", method = RequestMethod.POST, params = "action=update")
	public String updateSmsTemplate(@ModelAttribute Page page, Model model, @ModelAttribute SmsTemplate smsTemplate) {
		if (smsTemplate != null) {
			boolean flag = smsTemplateService.updateSmsTemplate(smsTemplate);
			if (flag) {
				model.addAttribute("page", page);
				return "system/smsTemplate";
			}
		}
		return null;
	}

	/**
	 * 删除短信模版
	 * 
	 */
	@RequestMapping("/smsTemplate_delete")
	public String deleteSmsTemplate(Long... smsIds) {
		if (smsIds.length > 0 && smsIds != null) {
			smsTemplateService.deleteSmsTemplate(smsIds);
			return "system/smsTemplate";
		}
		return null;
	}
}
