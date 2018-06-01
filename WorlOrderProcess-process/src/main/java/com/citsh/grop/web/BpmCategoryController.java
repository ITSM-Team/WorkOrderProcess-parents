package com.citsh.grop.web;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import com.citsh.grop.entity.BpmCategory;
import com.citsh.grop.servcie.BpmCategoryService;
import com.citsh.export.Exportor;
import com.citsh.export.TableModel;
import com.citsh.id.IdGenerator;
import com.citsh.page.Page;
import com.citsh.query.PropertyFilter;
import com.citsh.spring.MessageHelper;
import com.citsh.tenant.TenantHolder;
import com.citsh.util.BeanMapper;
import io.swagger.annotations.Api;
@Controller
@RequestMapping("bpm")
@Api(description="流程分类")
public class BpmCategoryController {
	 @Autowired
	 private TenantHolder tenantHolder;
	 @Autowired
	 private  BpmCategoryService bpmCategoryService;
	 @Autowired
	 private IdGenerator idGenerator;
	 @Autowired
	 private MessageHelper messageHelper;
	 @Autowired
	 private Exportor exportor;
	 
	
	 /**
	  * 查询流程分类
	  * */
	@GetMapping("bpm-category-list")
	public String list(@ModelAttribute Page page,@RequestParam  Map<String, Object> parameterMap){
		String tenantId=tenantHolder.getTenantId();
		//解析查询条件
		List<PropertyFilter> propertyFilters=PropertyFilter.buildFromMap(parameterMap);
		//按 equals 查询 tenantId 类型 String
		propertyFilters.add(new PropertyFilter("EQS_tenantId",tenantId));
	    page=bpmCategoryService.pagedQuery(page, propertyFilters);
	    Model model=new RedirectAttributesModelMap();
	    model.addAttribute("page", page);
        return "bpm/bpm-category-list";		
	}
	
	/**
	 * 通过ID查询流程分类
	 * **/
    @GetMapping("bpm-category-input")
    public String input(@RequestParam(value = "id", required = false) Long id) {
        if (id != null) {
            BpmCategory bpmCategory = bpmCategoryService.find(id); 
            Model model=new RedirectAttributesModelMap(); 
            model.addAttribute("model", bpmCategory);
        }
        return "bpm/bpm-category-input";
    }
    
    /**
     * 保存分类
     * */
    @PostMapping("bpm-category-save")
    public String save(@ModelAttribute BpmCategory bpmCategory) {
        BpmCategory dest = null;
        Long id = bpmCategory.getId();
        if (id != null) {
            dest = bpmCategoryService.find(id);
            BeanMapper beanMapper=new BeanMapper();
            beanMapper.copy(bpmCategory, dest);
        } else {
        	id=idGenerator.generateId();
            dest = bpmCategory;
            dest.setId(id);
            String tenantId = tenantHolder.getTenantId();
            dest.setTenantId(tenantId);
        }
        bpmCategoryService.save(dest);      
       RedirectAttributesModelMap redirectAttributesModelMap=new RedirectAttributesModelMap();
        messageHelper.addFlashMessage(redirectAttributesModelMap, "core.success.save",
                "保存成功");
        return "redirect:/bpm/bpm-category-list.do";
    }
    
    /**
     * 根据ids删除
     * */
    @PostMapping("bpm-category-remove")
    public String remove(@RequestParam("selectedItem") List<Long> selectedItem) {
    	bpmCategoryService.deleteById(selectedItem);
        RedirectAttributesModelMap redirectAttributesModelMap=new RedirectAttributesModelMap();
        messageHelper.addFlashMessage(redirectAttributesModelMap,
                "core.success.delete", "删除成功");
        return "redirect:/bpm/bpm-category-list.do";
    }
    
    @GetMapping("bpm-category-export")
    public void export(@ModelAttribute Page page,
            @RequestParam Map<String, Object> parameterMap,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        List<PropertyFilter> propertyFilters = PropertyFilter
                .buildFromMap(parameterMap);       
        page=bpmCategoryService.pagedQuery(page, propertyFilters);
        List<BpmCategory> bpmCategories = (List<BpmCategory>) page.getResult();
        TableModel tableModel = new TableModel();
        tableModel.setName("bpm-category");
        tableModel.addHeaders("id", "name");
        tableModel.setData(bpmCategories);
        exportor.export(request, response, tableModel);       
    }
}