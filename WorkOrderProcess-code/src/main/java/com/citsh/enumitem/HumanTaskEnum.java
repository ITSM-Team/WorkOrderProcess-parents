package com.citsh.enumitem;

public enum HumanTaskEnum
{
  ASSIGNEE("assignee", "分配人"), 

  CANDIDATE("candidate", "候选人"), 

  RECEIVE("receive", "任务接收人"), 

  INITIATE("initiate", "流程发起人"), 

  COMPLETE("complete", "完成"), 

  INITCOMPLETE("complete", "提交"), 

  COPY("copy", "抄送"), 

  STARTEVENT("startEvent", "启动");

  private String code;
  private String name;

  private HumanTaskEnum(String code, String name) { this.code = code;
    this.name = name; }

  public String getCode()
  {
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
}