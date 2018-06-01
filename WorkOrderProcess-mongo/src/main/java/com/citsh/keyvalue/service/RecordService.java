package com.citsh.keyvalue.service;

import com.citsh.keyvalue.entity.Record;
import java.util.List;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public abstract interface RecordService
{
  public abstract Record findById(Long paramLong);

  public abstract Record findOne(Query paramQuery);

  public abstract void remove(Query paramQuery);

  public abstract List<Record> find(Query paramQuery);

  public abstract long count(Query paramQuery);

  public abstract void save(Record paramRecord);

  public abstract void update(Query paramQuery, Update paramUpdate);
}