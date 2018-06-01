package com.citsh.form.service.impl;

import com.citsh.form.entity.FormTemplate;
import com.citsh.form.mdao.FormTemplateDao;
import com.citsh.form.service.FormTemplateService;
import com.citsh.from.FormDTO;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class FormTemplateServiceImpl
  implements FormTemplateService
{
  private Logger logger = LoggerFactory.getLogger(FormTemplateServiceImpl.class);

  @Autowired
  private FormTemplateDao formTemplateDao;

  public List<FormDTO> getAll(String tenantId) {
    List<FormTemplate> formTemplates = this.formTemplateDao.find(Query.query(Criteria.where("tenantId").is(tenantId)));
    List<FormDTO> dtos = new ArrayList<FormDTO>();
    for (FormTemplate formTemplate : formTemplates) {
      FormDTO formDTO = new FormDTO();
      formDTO.setId(String.valueOf(formTemplate.getId()));
      formDTO.setCode(formTemplate.getCode());
      formDTO.setName(formTemplate.getName());
      dtos.add(formDTO);
    }
    return dtos;
  }

  public FormDTO findForm(String code, String tenantId)
  {
    FormTemplate formTemplate = (FormTemplate)this.formTemplateDao
      .findOne(Query.query(Criteria.where("code")
      .is(code).and("tenantId").is(tenantId)));
    if (formTemplate == null) {
      this.logger.error("cannot find form : {}, {}", code, tenantId);
      return null;
    }
    FormDTO formDto = new FormDTO();
    formDto.setId(String.valueOf(formTemplate.getId()));
    formDto.setCode(formTemplate.getCode());
    formDto.setName(formTemplate.getName());
    if (Integer.valueOf(1).equals(formTemplate.getType())) {
      formDto.setRedirect(true);
      formDto.setUrl(formTemplate.getContent());
    } else {
      formDto.setContent(formTemplate.getContent());
    }
    return formDto;
  }
}