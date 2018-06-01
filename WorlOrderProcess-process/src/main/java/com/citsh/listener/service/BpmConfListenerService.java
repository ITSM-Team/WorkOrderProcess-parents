package com.citsh.listener.service;

import com.citsh.listener.entity.BpmConfListener;
import java.util.List;

public abstract interface BpmConfListenerService
{
  public abstract List<BpmConfListener> listBySQL(String paramString, Object[] paramArrayOfObject);

  public abstract void save(BpmConfListener paramBpmConfListener);

  public abstract void remove(Long paramLong);
}