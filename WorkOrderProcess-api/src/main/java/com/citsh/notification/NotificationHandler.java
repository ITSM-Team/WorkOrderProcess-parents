package com.citsh.notification;

public abstract interface NotificationHandler
{
  public abstract String getType();

  public abstract void handle(NotificationDTO paramNotificationDTO, String paramString);
}