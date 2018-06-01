package com.citsh.humantask.service;

import com.citsh.humantask.entity.TaskDeadline;
import java.util.List;
import java.util.Set;

public abstract interface TaskDeadlineService
{
  public abstract void removeAll(Set<TaskDeadline> paramSet);

  public abstract void save(TaskDeadline paramTaskDeadline);

  public abstract List<TaskDeadline> listBySQL(String paramString, Object[] paramArrayOfObject);

  public abstract void removeAll(List<TaskDeadline> paramList);
}