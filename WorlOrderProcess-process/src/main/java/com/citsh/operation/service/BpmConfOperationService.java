package com.citsh.operation.service;

import com.citsh.operation.entity.BpmConfOperation;
import java.util.List;

public abstract interface BpmConfOperationService
{
  public abstract List<BpmConfOperation> listBySQL(String paramString, Object[] paramArrayOfObject);

  public abstract BpmConfOperation findById(Long paramLong);

  public abstract void save(BpmConfOperation paramBpmConfOperation);

  public abstract void remove(Long paramLong);
}