package com.citsh.listener.service;

import com.citsh.humantask.entity.TaskInfo;

public abstract interface ListenerService
{
  public abstract void onCreate(TaskInfo paramTaskInfo);

  public abstract void onComplete(TaskInfo paramTaskInfo);
}