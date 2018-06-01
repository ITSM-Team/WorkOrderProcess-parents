package com.citsh.config.service;

import com.citsh.config.entity.BpmTaskConf;
import java.util.List;

public abstract interface BpmTaskConfService
{
  public abstract BpmTaskConf listBySQLOne(String paramString, Object[] paramArrayOfObject);

  public abstract List<BpmTaskConf> listBySQL(String paramString, Object[] paramArrayOfObject);
}