package com.citsh.notification;

import java.util.Collection;

public abstract interface NotificationConnector
{
  public abstract void send(NotificationDTO paramNotificationDTO, String paramString);

  public abstract Collection<String> getTypes(String paramString);
}
