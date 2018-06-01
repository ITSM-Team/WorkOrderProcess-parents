package com.citsh.template.service;

import com.citsh.template.TemplateConnector;
import com.citsh.template.TemplateDTO;
import java.util.List;

public abstract interface TemplateInfoService extends TemplateConnector
{
  public abstract TemplateDTO findByCode(String paramString1, String paramString2);

  public abstract List<TemplateDTO> findAll(String paramString);
}