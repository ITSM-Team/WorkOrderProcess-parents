package com.citsh.template.service.impl;

import com.citsh.template.TemplateDTO;
import com.citsh.template.dao.TemplateFieldDao;
import com.citsh.template.dao.TemplateInfoDao;
import com.citsh.template.entity.TemplateField;
import com.citsh.template.entity.TemplateInfo;
import com.citsh.template.service.TemplateInfoService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateInfoServiceImpl implements TemplateInfoService {

	@Autowired
	private TemplateInfoDao templateInfoDao;

	@Autowired
	private TemplateFieldDao templateFieldDao;

	public TemplateDTO findByCode(String code, String tenantId) {
		List<TemplateInfo> templateInfos = this.templateInfoDao.listBySQL("code=? and tenantId=?",
				new Object[] { code, tenantId });
		if (templateInfos.size() > 0) {
			return processTemplateInfo((TemplateInfo) templateInfos.get(0));
		}
		return null;
	}

	public List<TemplateDTO> findAll(String tenantId) {
		List<TemplateInfo> templateInfos = this.templateInfoDao.listBySQL("tenantId=?", new Object[] { tenantId });
		List<TemplateDTO> list = new ArrayList<TemplateDTO>();
		for (TemplateInfo templateInfo : templateInfos) {
			TemplateDTO templateDTO = processTemplateInfo(templateInfo);
			list.add(templateDTO);
		}
		return list;
	}

	public TemplateDTO processTemplateInfo(TemplateInfo templateInfo) {
		TemplateDTO templateDto = new TemplateDTO();
		templateDto.setCode(templateInfo.getCode());
		templateDto.setName(templateInfo.getName());
		List<TemplateField> templateFields = this.templateFieldDao.listBySQL("templateInfo.id=?",
				new Object[] { templateInfo.getId() });
		for (TemplateField templateField : templateFields) {
			templateDto.getFields().put(templateField.getName(), templateField.getContent());
		}
		return templateDto;
	}
}