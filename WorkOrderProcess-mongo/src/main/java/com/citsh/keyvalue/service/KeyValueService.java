package com.citsh.keyvalue.service;

import com.citsh.keyvalue.entity.Record;
import com.citsh.page.Page;
import java.util.List;
import java.util.Map;

public abstract interface KeyValueService
{
  public abstract Record findByCode(String paramString);

  public abstract Record findByRef(String paramString);

  public abstract Record findByBusinessKey(String paramString);

  public abstract void save(Record paramRecord);

  public abstract void removeByCode(String paramString);

  public abstract void removeByBusinessKey(String paramString);

  public abstract List<Record> findByStatus(int paramInt, String paramString1, String paramString2);

  public abstract Page pagedQuery(Page paramPage, int paramInt, String paramString1, String paramString2);

  public abstract long findTotalCount(String paramString1, String paramString2, String paramString3);

  public abstract List<Map<String, Object>> findResult(Page paramPage, String paramString1, String paramString2, Map<String, String> paramMap, String paramString3);

  public abstract Record copyRecord(Record paramRecord, List<String> paramList);
}
