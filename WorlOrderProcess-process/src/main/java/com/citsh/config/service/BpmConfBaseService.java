package com.citsh.config.service;

import com.citsh.config.entity.BpmConfBase;
import java.util.List;

public abstract interface BpmConfBaseService
{
  public abstract List<BpmConfBase> findAll();

  public abstract BpmConfBase find(Long paramLong);
}
