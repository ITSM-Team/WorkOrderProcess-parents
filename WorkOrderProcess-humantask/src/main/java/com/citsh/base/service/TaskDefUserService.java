package com.citsh.base.service;
import com.citsh.base.entity.TaskDefUser;
import java.util.List;

public abstract interface TaskDefUserService
{
  public abstract List<TaskDefUser> findByBaseCodeAndBaseProcessId(String paramString, Object... paramArrayOfObject);

  public abstract TaskDefUser findByBaseCodeAndBaseProcessIdOne(String paramString, Object... paramArrayOfObject);

  public abstract TaskDefUser save(TaskDefUser paramTaskDefUser);

  public abstract void remover(TaskDefUser paramTaskDefUser);

  public abstract void update(TaskDefUser paramTaskDefUser);
}
