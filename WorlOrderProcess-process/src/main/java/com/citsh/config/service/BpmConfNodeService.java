package com.citsh.config.service;

import com.citsh.config.entity.BpmConfBase;
import com.citsh.config.entity.BpmConfNode;
import java.util.List;

public abstract interface BpmConfNodeService
{
  public abstract List<BpmConfNode> findByBpmConfBase(BpmConfBase paramBpmConfBase);

  public abstract BpmConfNode find(Long paramLong);
}