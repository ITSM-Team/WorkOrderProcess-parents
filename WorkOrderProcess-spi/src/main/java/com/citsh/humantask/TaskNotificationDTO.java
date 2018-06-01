package com.citsh.humantask;

public class TaskNotificationDTO
{
  private String eventName;
  private String type;
  private String receiver;
  private String templateCode;

  public String getEventName()
  {
    return this.eventName;
  }

  public void setEventName(String eventName) {
    this.eventName = eventName;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getReceiver() {
    return this.receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public String getTemplateCode() {
    return this.templateCode;
  }

  public void setTemplateCode(String templateCode) {
    this.templateCode = templateCode;
  }
}
