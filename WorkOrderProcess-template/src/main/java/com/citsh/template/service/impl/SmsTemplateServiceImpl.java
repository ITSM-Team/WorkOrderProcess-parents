package com.citsh.template.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.citsh.page.Page;
import com.citsh.query.PropertyFilter;
import com.citsh.template.dao.SmsTemplateDao;
import com.citsh.template.entity.SmsTemplate;
import com.citsh.template.service.SmsTemplateService;

@Service
public class SmsTemplateServiceImpl implements SmsTemplateService {

	@Autowired
	private SmsTemplateDao smsTemplateDao;

	/**
	 * 根据id查找到单个短信模版信息
	 */
	public SmsTemplate findById(Long smsId) {
		if (smsId != null) {
			SmsTemplate smsTemplate = smsTemplateDao.find(smsId);
			if (smsTemplate != null) {
				return smsTemplate;
			} else {
				return null;
			}
		}
		return null;
	}

	/**
	 * 分页查找全部短信模版信息
	 */
	public Page paginationQueryAll(Page page, List<PropertyFilter> propertyFilters) {
		// 降序排列
		List<String> list = new ArrayList<String>();
		page.setOrderBy("creationTime");
		page.setOrder("DESC");
		return smsTemplateDao.pagedQuery(page, propertyFilters);
	}

	/**
	 * 添加短信模版
	 */
	public boolean addSmsTemplate(SmsTemplate smsTemplate) {
		if (smsTemplate != null) {
			SmsTemplate smsBean = smsTemplateDao.add(smsTemplate);
			if (smsBean != null) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * 修改短信模版
	 */
	public boolean updateSmsTemplate(SmsTemplate smsTemplate) {
		if (smsTemplate != null) {
			boolean flag = smsTemplateDao.update(smsTemplate);
			if (flag) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * 删除短信模版
	 */
	public void deleteSmsTemplate(Long... smsIds) {
		if (smsIds.length > 0 && smsIds != null) {
			smsTemplateDao.deleteByIds(smsIds);
		}
	}

}
