package com.citsh.humantask;

public class ParticipantDTO
{
  private String id;
  private String code;
  private String type;
  private String humanTaskId;

  public String getId()
  {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getHumanTaskId() {
    return this.humanTaskId;
  }

  public void setHumanTaskId(String humanTaskId) {
    this.humanTaskId = humanTaskId;
  }
}