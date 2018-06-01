package com.citsh.config.service;

import com.citsh.config.entity.BpmConfCountersign;
import java.util.List;

public abstract interface BpmConfCountersignService
{
  public abstract List<BpmConfCountersign> findBy(String paramString, Long paramLong);

  public abstract BpmConfCountersign findOneBy(String paramString, Long paramLong);
}