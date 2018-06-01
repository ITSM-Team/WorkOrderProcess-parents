package com.citsh.template.service;

import java.util.List;

import com.citsh.page.Page;
import com.citsh.query.PropertyFilter;
import com.citsh.template.TemplateConnector;
import com.citsh.template.TemplateDTO;
import com.citsh.template.entity.SmsTemplate;

public interface SmsTemplateService {

	/**
	 * 根据id查找
	 * @param smsId
	 * @return
	 */
	public  SmsTemplate findById(Long smsId);
	
	/**
	 * 分页查找
	 */
	public  Page paginationQueryAll(Page page,List<PropertyFilter> propertyFilters);
	
	/**
	 * 新增
	 */
	public  boolean addSmsTemplate(SmsTemplate smsTemplate); 
	
	/**
	 * 修改
	 */
	public  boolean updateSmsTemplate(SmsTemplate smsTemplate); 
	
	/**
	 * 删除
	 */
	public  void deleteSmsTemplate(Long... smsIds);
	
}
