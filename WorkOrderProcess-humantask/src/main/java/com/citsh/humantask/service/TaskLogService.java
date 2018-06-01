package com.citsh.humantask.service;

import com.citsh.humantask.entity.TaskLog;
import java.util.Set;

public abstract interface TaskLogService
{
  public abstract void removeAll(Set<TaskLog> paramSet);
}