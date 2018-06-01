package com.citsh.user.service;

import com.citsh.user.entity.BpmConfUser;
import java.util.List;

public abstract interface BpmConfUserService
{
  public abstract List<BpmConfUser> findBy(String paramString, Long paramLong);

  public abstract BpmConfUser findByNodeIdAndStatusAndType(Long paramLong, int paramInt1, int paramInt2);

  public abstract BpmConfUser save(BpmConfUser paramBpmConfUser);

  public abstract BpmConfUser findById(Long paramLong);

  public abstract void remove(BpmConfUser paramBpmConfUser);

  public abstract List<BpmConfUser> listBySQL(String paramString, Object[] paramArrayOfObject);
}