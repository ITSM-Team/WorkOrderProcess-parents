package com.citsh.listener.config;

public enum AssignStrategyType
{
  ON("无"), 

  ONE("当只有一人时采用独占策略"), 

  MIN("资源中任务最少者"), 

  RANDOM("资源中随机分配");

  private String value;

  private AssignStrategyType(String value) { this.value = value;
  }

  public String getValue()
  {
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}