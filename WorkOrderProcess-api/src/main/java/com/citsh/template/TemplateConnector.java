package com.citsh.template;

import java.util.List;

public abstract interface TemplateConnector
{
  public abstract TemplateDTO findByCode(String paramString1, String paramString2);

  public abstract List<TemplateDTO> findAll(String paramString);
}
