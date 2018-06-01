package com.citsh.humantask;

public class HumanTaskDefinition
{
  private String key;
  private String name;
  private String assignee;

  public String getKey()
  {
    return this.key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAssignee() {
    return this.assignee;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }
}