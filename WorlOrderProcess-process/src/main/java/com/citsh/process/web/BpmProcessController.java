package com.citsh.process.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ProcessEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.citsh.config.entity.BpmConfBase;
import com.citsh.config.service.BpmConfBaseService;
import com.citsh.export.Exportor;
import com.citsh.export.TableModel;
import com.citsh.grop.entity.BpmCategory;
import com.citsh.grop.servcie.BpmCategoryService;
import com.citsh.id.IdGenerator;
import com.citsh.page.Page;
import com.citsh.process.dao.BpmProcessDao;
import com.citsh.process.entity.BpmProcess;
import com.citsh.process.service.BpmProcessService;
import com.citsh.query.PropertyFilter;
import com.citsh.spring.MessageHelper;
import com.citsh.tenant.TenantHolder;
import com.citsh.util.BeanMapper;

@Controller
@RequestMapping("bpm")
@ResponseBody
public class BpmProcessController {
	private static Logger logger = LoggerFactory.getLogger(BpmProcessController.class);
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private MessageHelper messageHelper;
	@Autowired
	private IdGenerator idGenerator;
	@Autowired
	private Exportor exportor;
	@Autowired
	private TenantHolder tenantHolder;
	@Autowired
	private BpmProcessService bpmProcessService;
	@Autowired
	private BpmCategoryService bpmCategoryService;
	@Autowired
	private BpmConfBaseService bpmConfBaseService;
	private BeanMapper beanMapper = new BeanMapper();

	/**
	 * 查询流程列表
	 */
	@GetMapping("bpm-process-list")
	public String list(@ModelAttribute Page page, @RequestParam Map<String, Object> parameterMap,Model model) {
		String tenanId = tenantHolder.getTenantId();
		List<PropertyFilter> propertyFilters = PropertyFilter.buildFromMap(parameterMap);
		propertyFilters.add(new PropertyFilter("EQS_tenantId", tenanId));
		page = bpmProcessService.pagedQuery(page, propertyFilters);
		model.addAttribute("page", page);
		return "bpm/bpm-process-list";
	}

	/**
	 * 根据ID查询
	 */
	@GetMapping("bpm-process-input")
	public String input(@RequestParam(value = "id", required = false) Long id) {
		Model model = new RedirectAttributesModelMap();
		if (id != null) {
			BpmProcess bpmProcess = bpmProcessService.findById(id);
			model.addAttribute("model", bpmProcess);
		}
		List<BpmCategory> bpmCategories = bpmCategoryService.findAll();
		List<BpmConfBase> bpmConfBases = bpmConfBaseService.findAll();

		model.addAttribute("bpmCategories", bpmCategories);
		model.addAttribute("bpmConfBases", bpmConfBases);

		return "bpm/bpm-process-input";
	}

	/**
	 * 保存
	 */
	@RequestMapping("bpm-process-save")
	public String save(@ModelAttribute BpmProcess bpmProcess, @RequestParam("bpmCategoryId") Long bpmCategoryId,
			@RequestParam("bpmConfBaseId") Long bpmConfBaseId) {
		BpmProcess dest = null;
		Long id = bpmProcess.getId();
		if (id != null) {
			bpmProcessService.findById(id);
			beanMapper.copy(bpmProcess, dest);
		} else {
			dest = bpmProcess;
			String tenanId = tenantHolder.getTenantId();
			dest.setTenantId(tenanId);
			dest.setId(idGenerator.generateId());
		}
		BpmCategory bpmCategory = bpmCategoryService.find(bpmCategoryId);
		BpmConfBase bpmConfBase = bpmConfBaseService.find(bpmConfBaseId);
		dest.setBpmCategory(bpmCategory);
		dest.setBpmConfBase(bpmConfBase);
		bpmProcessService.saveEntity(bpmProcess);
		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
		messageHelper.addFlashMessage(redirectAttributes, "core.success.save", "保存成功");

		return "redirect:/bpm/bpm-process-list.do";

	}

	/**
	 * 删除
	 * */
	@RequestMapping("bpm-process-remove")
	public String remove(@RequestParam("selectedItem") List<Long> selectedItem) {
		bpmProcessService.deleteByIds(selectedItem.toArray());		
		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
		messageHelper.addFlashMessage(redirectAttributes, "core.success.delete", "删除成功");
		return "redirect:/bpm/bpm-process-list.do";
	}
	
	/**
	 * 导出
	 * */
    @RequestMapping("bpm-process-export")
    public void export(@ModelAttribute Page page,
            @RequestParam Map<String, Object> parameterMap,
            HttpServletRequest request, HttpServletResponse response){
        List<PropertyFilter> propertyFilters = PropertyFilter.buildFromMap(parameterMap);
        page =bpmProcessService.pagedQuery(page, propertyFilters);
        List<BpmProcess> bpmCategories = (List<BpmProcess>) page.getResult();
        TableModel tableModel = new TableModel();
        tableModel.setName("bpm-process");
        tableModel.addHeaders("id", "name");
        tableModel.setData(bpmCategories);
        exportor.export(request, response, tableModel);
    }
}
