package com.citsh.template;

import java.util.HashMap;
import java.util.Map;

public class TemplateDTO
{
  private String code;
  private String name;
  private Map<String, String> fields = new HashMap();

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Map<String, String> getFields() {
    return this.fields;
  }

  public void setFields(Map<String, String> fields) {
    this.fields = fields;
  }

  public String getField(String fieldName) {
    return (String)this.fields.get(fieldName);
  }
}