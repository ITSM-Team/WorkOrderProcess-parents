package com.citsh.notice.service;

import com.citsh.notice.entity.BpmMailTemplate;
import java.util.List;

public abstract interface BpmMailTemplateService
{
  public abstract List<BpmMailTemplate> findAll();
}