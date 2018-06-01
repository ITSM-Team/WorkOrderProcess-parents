package com.citsh.humantask.service;

import com.citsh.humantask.entity.TaskInfo;
import com.citsh.page.Page;
import java.util.List;
import java.util.Map;

public abstract interface TaskInfoService
{
  public abstract TaskInfo findById(Long paramLong);

  public abstract void remove(Long paramLong);

  public abstract TaskInfo listBySQLOne(String paramString, Object[] paramArrayOfObject);

  public abstract List<TaskInfo> listBySQL(String paramString, Object[] paramArrayOfObject);

  public abstract void save(TaskInfo paramTaskInfo);

  public abstract Page pageQuery(String paramString, int paramInt1, int paramInt2, Object... paramArrayOfObject);

  public abstract Page pageQuery(int paramInt1, int paramInt2, Map<String, Object> paramMap);
}