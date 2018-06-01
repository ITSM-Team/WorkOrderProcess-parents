package com.citsh.enumitem;

public enum OperationEnum
{
  saveDraft("saveDraft", "保存草稿"), 
  completeTask("completeTask", "完成任务"), 
  rollbackPrevious("rollbackPrevious", "回滚上一页"), 
  rollbackInitiator("rollbackInitiator", ""), 
  transfer("transfer", "转让"), 
  delegateTask("delegateTask", "任务委托"), 
  delegateTaskCreate("delegateTaskCreate", "创建委托任务"), 
  communicate("communicate", "通信"), 
  callback("callback", "回调"), 
  addCounterSign("addCounterSign", "");

  private String key;
  private String value;

  private OperationEnum(String key, String value) { this.key = key;
    this.value = value;
  }

  public String getKey()
  {
    return this.key;
  }
  public void setKey(String key) {
    this.key = key;
  }

  public String getValue()
  {
    return this.value;
  }

  public void setValue(String value)
  {
    this.value = value;
  }
}