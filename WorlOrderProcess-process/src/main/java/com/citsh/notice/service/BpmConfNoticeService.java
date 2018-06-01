package com.citsh.notice.service;

import com.citsh.notice.entity.BpmConfNotice;
import java.util.List;

public abstract interface BpmConfNoticeService
{
  public abstract List<BpmConfNotice> listBySQL(String paramString, Object[] paramArrayOfObject);

  public abstract void save(BpmConfNotice paramBpmConfNotice);

  public abstract BpmConfNotice findById(Long paramLong);

  public abstract void remove(Long paramLong);
}