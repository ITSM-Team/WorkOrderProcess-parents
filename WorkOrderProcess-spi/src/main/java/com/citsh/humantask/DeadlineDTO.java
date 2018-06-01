package com.citsh.humantask;

import java.util.ArrayList;
import java.util.List;

public class DeadlineDTO
{
  private String type;
  private String duration;
  private String notificationType;
  private String notificationReceiver;
  private String notificationTemplateCode;
  private List<EscalationDTO> escalations = new ArrayList();

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDuration() {
    return this.duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public String getNotificationType() {
    return this.notificationType;
  }

  public void setNotificationType(String notificationType) {
    this.notificationType = notificationType;
  }

  public String getNotificationReceiver() {
    return this.notificationReceiver;
  }

  public void setNotificationReceiver(String notificationReceiver) {
    this.notificationReceiver = notificationReceiver;
  }

  public String getNotificationTemplateCode() {
    return this.notificationTemplateCode;
  }

  public void setNotificationTemplateCode(String notificationTemplateCode) {
    this.notificationTemplateCode = notificationTemplateCode;
  }

  public List<EscalationDTO> getEscalation() {
    return this.escalations;
  }

  public void setEscalations(List<EscalationDTO> escalations) {
    this.escalations = escalations;
  }
}