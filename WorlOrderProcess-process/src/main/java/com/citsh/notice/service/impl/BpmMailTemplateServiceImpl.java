package com.citsh.notice.service.impl;

import com.citsh.notice.dao.BpmMailTemplateDao;
import com.citsh.notice.entity.BpmMailTemplate;
import com.citsh.notice.service.BpmMailTemplateService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BpmMailTemplateServiceImpl
  implements BpmMailTemplateService
{

  @Autowired
  private BpmMailTemplateDao bpmMailTemplateDao;

  public List<BpmMailTemplate> findAll()
  {
    return this.bpmMailTemplateDao.findAll();
  }
}