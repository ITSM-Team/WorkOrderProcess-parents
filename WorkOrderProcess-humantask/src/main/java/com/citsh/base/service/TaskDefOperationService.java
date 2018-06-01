package com.citsh.base.service;

import com.citsh.base.entity.TaskDefOperation;
import java.util.List;

public abstract interface TaskDefOperationService
{
  public abstract List<TaskDefOperation> findByBaseCodeAndBaseProcessId(String paramString, Object[] paramArrayOfObject);

  public abstract TaskDefOperation findByBaseCodeAndBaseProcessIdOne(String paramString, Object[] paramArrayOfObject);

  public abstract TaskDefOperation save(TaskDefOperation paramTaskDefOperation);

  public abstract void remove(TaskDefOperation paramTaskDefOperation);
}