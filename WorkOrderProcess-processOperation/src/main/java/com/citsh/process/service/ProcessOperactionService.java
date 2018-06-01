package com.citsh.process.service;

import com.citsh.keyvalue.FormParameter;
import com.citsh.keyvalue.entity.Record;
import java.util.Map;

public abstract interface ProcessOperactionService
{
  public abstract String saveDraft(String paramString1, String paramString2, FormParameter paramFormParameter);

  public abstract void startProcessInstance(String paramString1, String paramString2, String paramString3, Map<String, Object> paramMap, Record paramRecord);

  public abstract void completeTask(String paramString1, String paramString2, FormParameter paramFormParameter, Map<String, Object> paramMap, Record paramRecord, String paramString3);
}