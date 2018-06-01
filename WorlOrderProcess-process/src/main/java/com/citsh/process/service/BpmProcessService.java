package com.citsh.process.service;

import com.citsh.page.Page;
import com.citsh.process.entity.BpmProcess;
import com.citsh.query.PropertyFilter;
import java.util.List;

public abstract interface BpmProcessService
{
  public abstract Page pagedQuery(Page paramPage, List<PropertyFilter> paramList);

  public abstract List<BpmProcess> findAll();

  public abstract BpmProcess findById(Long paramLong);

  public abstract BpmProcess saveEntity(BpmProcess paramBpmProcess);

  public abstract void deleteByIds(Object[] paramArrayOfObject);
}