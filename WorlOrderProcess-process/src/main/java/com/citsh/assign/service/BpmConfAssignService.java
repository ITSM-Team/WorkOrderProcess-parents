package com.citsh.assign.service;

import com.citsh.assign.entity.BpmConfAssign;

public abstract interface BpmConfAssignService
{
  public abstract BpmConfAssign findOneBy(String paramString, Long paramLong);

  public abstract BpmConfAssign findById(Long paramLong);

  public abstract void save(BpmConfAssign paramBpmConfAssign);
}
