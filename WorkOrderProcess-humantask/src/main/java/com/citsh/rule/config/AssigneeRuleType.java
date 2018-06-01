package com.citsh.rule.config;

public enum AssigneeRuleType
{
  Activiti("常用语:直接上级"), 

  Initiator("常用语:流程发起人"), 

  Position("岗位"), 

  Superior("环节处理人");

  private String value;

  private AssigneeRuleType(String value) { this.value = value; }

  public String getValue()
  {
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}