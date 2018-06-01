package com.citsh.rule.service;

import com.citsh.rule.entity.BpmConfRule;
import java.util.List;

public abstract interface BpmConfRuleService
{
  public abstract List<BpmConfRule> listBySQL(String paramString, Object[] paramArrayOfObject);

  public abstract void save(BpmConfRule paramBpmConfRule);

  public abstract void remove(Long paramLong);

  public abstract BpmConfRule findById(Long paramLong);
}
