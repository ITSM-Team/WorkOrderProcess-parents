package com.citsh.base.service;

import com.citsh.base.entity.TaskConfUser;

public abstract interface TaskConfUserService
{
  public abstract TaskConfUser findByCodeAndProIdOne(String paramString, Object[] paramArrayOfObject);

  public abstract TaskConfUser findBySQL(String paramString, Object[] paramArrayOfObject);

  public abstract void save(TaskConfUser paramTaskConfUser);
}