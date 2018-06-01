package com.citsh.user.service;

import com.citsh.user.entity.BpmConfUser;
import java.util.List;

public  interface BpmConfUserService
{
  public  List<BpmConfUser> findBy(String paramString, Long paramLong);

  public  BpmConfUser findByNodeIdAndStatusAndType(Long paramLong, int paramInt1, int paramInt2);

  public  BpmConfUser save(BpmConfUser paramBpmConfUser);

  public  BpmConfUser findById(Long paramLong);

  public  void remove(BpmConfUser paramBpmConfUser);

  public  List<BpmConfUser> listBySQL(String paramString, Object[] paramArrayOfObject);
  
  public BpmConfUser findByHSQLOne(String condition, Object... objects);
}
