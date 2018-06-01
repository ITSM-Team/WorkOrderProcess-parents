package com.citsh.delegate.service;

import com.citsh.delegate.entity.DelegateInfo;
import java.util.List;

public abstract interface DelegateInfoService
{
  public abstract List<DelegateInfo> listBySQL(String paramString, Object[] paramArrayOfObject);

  public abstract void remove(Long paramLong);

  public abstract void save(DelegateInfo paramDelegateInfo);
}

