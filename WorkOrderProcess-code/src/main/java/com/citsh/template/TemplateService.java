package com.citsh.template;

import java.io.File;
import java.util.Map;

public abstract interface TemplateService
{
  public abstract String renderText(String paramString, Map<String, Object> paramMap);

  public abstract String render(String paramString, Map<String, Object> paramMap);

  public abstract void renderTo(String paramString, Map<String, Object> paramMap, File paramFile);
}
