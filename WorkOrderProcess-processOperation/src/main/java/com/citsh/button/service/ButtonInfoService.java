package com.citsh.button.service;

import com.citsh.button.entity.ButtonInfo;
import java.util.List;

public abstract interface ButtonInfoService
{
  public abstract List<ButtonInfo> findAll();

  public abstract ButtonInfo listBySQL(String paramString, Object[] paramArrayOfObject);
}