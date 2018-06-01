package com.citsh.form.service;

import com.citsh.form.entity.BpmConfForm;
import java.util.List;

public abstract interface BpmConfFormService
{
  public abstract List<BpmConfForm> listBySQL(String paramString, Object[] paramArrayOfObject);

  public abstract BpmConfForm listBySQLOne(String paramString, Object[] paramArrayOfObject);

  public abstract void save(BpmConfForm paramBpmConfForm);

  public abstract BpmConfForm findById(Long paramLong);

  public abstract void remove(Long paramLong);
}