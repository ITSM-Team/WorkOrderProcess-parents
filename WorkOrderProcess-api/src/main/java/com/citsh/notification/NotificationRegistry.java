package com.citsh.notification;

public abstract interface NotificationRegistry
{
  public abstract void register(NotificationHandler paramNotificationHandler);

  public abstract void unregister(NotificationHandler paramNotificationHandler);
}
