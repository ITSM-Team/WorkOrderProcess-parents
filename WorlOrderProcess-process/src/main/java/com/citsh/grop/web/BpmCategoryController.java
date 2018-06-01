package com.citsh.grop.web;

import com.citsh.export.Exportor;
import com.citsh.export.TableModel;
import com.citsh.grop.entity.BpmCategory;
import com.citsh.grop.servcie.BpmCategoryService;
import com.citsh.id.IdGenerator;
import com.citsh.page.Page;
import com.citsh.query.PropertyFilter;
import com.citsh.spring.MessageHelper;
import com.citsh.tenant.TenantHolder;
import com.citsh.util.BeanMapper;
import io.swagger.annotations.Api;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

@Controller
@RequestMapping({"bpm"})
@Api(description="流程分类")
public class BpmCategoryController
{
  private static final Logger mongoLog = LoggerFactory.getLogger(BpmCategoryController.class);

  @Autowired
  private TenantHolder tenantHolder;

  @Autowired
  private BpmCategoryService bpmCategoryService;

  @Autowired
  private IdGenerator idGenerator;

  @Autowired
  private MessageHelper messageHelper;

  @Autowired
  private Exportor exportor;

  @GetMapping({"bpmCategorysave"})
  @ResponseBody
  public BpmCategory bpmCategorysave() { BpmCategory category = new BpmCategory();
    category.setId(Long.valueOf(new Date().getTime()));
    category.setName("测试");
    category.setTenantId(this.tenantHolder.getTenantId());
    this.bpmCategoryService.add(category);
    category = this.bpmCategoryService.find(Long.valueOf(1L));
    return category;
  }

  @GetMapping({"bpm-category-list"})
  public String list(@ModelAttribute Page page, @RequestParam Map<String, Object> parameterMap)
  {
    String tenantId = this.tenantHolder.getTenantId();

    List<PropertyFilter> propertyFilters = PropertyFilter.buildFromMap(parameterMap);

    propertyFilters.add(new PropertyFilter("EQS_tenantId", tenantId));
    page = this.bpmCategoryService.pagedQuery(page, propertyFilters);
    Model model = new RedirectAttributesModelMap();
    model.addAttribute("page", page);
    return "bpm/bpm-category-list";
  }

  @GetMapping({"bpm-category-input"})
  public String input(@RequestParam(value="id", required=false) Long id)
  {
    if (id != null) {
      BpmCategory bpmCategory = this.bpmCategoryService.find(id);
      Model model = new RedirectAttributesModelMap();
      model.addAttribute("model", bpmCategory);
    }
    return "bpm/bpm-category-input";
  }

  @PostMapping({"bpm-category-save"})
  public String save(@ModelAttribute BpmCategory bpmCategory)
  {
    BpmCategory dest = null;
    Long id = bpmCategory.getId();
    if (id != null) {
      dest = this.bpmCategoryService.find(id);
      BeanMapper beanMapper = new BeanMapper();
      beanMapper.copy(bpmCategory, dest);
    } else {
      id = this.idGenerator.generateId();
      dest = bpmCategory;
      dest.setId(id);
      String tenantId = this.tenantHolder.getTenantId();
      dest.setTenantId(tenantId);
    }
    this.bpmCategoryService.save(dest);
    RedirectAttributesModelMap redirectAttributesModelMap = new RedirectAttributesModelMap();
    this.messageHelper.addFlashMessage(redirectAttributesModelMap, "core.success.save", "保存成功");

    return "redirect:/bpm/bpm-category-list.do";
  }

  @PostMapping({"bpm-category-remove"})
  public String remove(@RequestParam("selectedItem") List<Long> selectedItem)
  {
    this.bpmCategoryService.deleteById(selectedItem);
    RedirectAttributesModelMap redirectAttributesModelMap = new RedirectAttributesModelMap();
    this.messageHelper.addFlashMessage(redirectAttributesModelMap, "core.success.delete", "删除成功");

    return "redirect:/bpm/bpm-category-list.do";
  }

  @GetMapping({"bpm-category-export"})
  public void export(@ModelAttribute Page page, @RequestParam Map<String, Object> parameterMap, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    List propertyFilters = PropertyFilter.buildFromMap(parameterMap);

    page = this.bpmCategoryService.pagedQuery(page, propertyFilters);
    List bpmCategories = (List)page.getResult();
    TableModel tableModel = new TableModel();
    tableModel.setName("bpm-category");
    tableModel.addHeaders(new String[] { "id", "name" });
    tableModel.setData(bpmCategories);
    this.exportor.export(request, response, tableModel);
  }
}