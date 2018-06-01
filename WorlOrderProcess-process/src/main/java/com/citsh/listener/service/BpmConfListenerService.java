package com.citsh.listener.service;

import com.citsh.listener.entity.BpmConfListener;
import java.util.List;

public  interface BpmConfListenerService
{
  public  List<BpmConfListener> listBySQL(String paramString, Object... paramArrayOfObject);

  public  void save(BpmConfListener paramBpmConfListener);

  public  void remove(Long paramLong);
  
  public BpmConfListener findByHSQLOne(String condition,Object...objects);
}