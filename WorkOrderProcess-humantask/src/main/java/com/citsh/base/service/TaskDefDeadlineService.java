package com.citsh.base.service;
import com.citsh.base.entity.TaskDefDeadline;
import java.util.List;

public abstract interface TaskDefDeadlineService
{
  public abstract List<TaskDefDeadline> findByBaseCodeAndBaseProcessId(String paramString, Object... paramArrayOfObject);

  public abstract TaskDefDeadline findByBaseCodeAndBaseProcessIdOne(String paramString, Object... paramArrayOfObject);

  public abstract void save(TaskDefDeadline paramTaskDefDeadline);

  public abstract void remove(TaskDefDeadline paramTaskDefDeadline);

}
