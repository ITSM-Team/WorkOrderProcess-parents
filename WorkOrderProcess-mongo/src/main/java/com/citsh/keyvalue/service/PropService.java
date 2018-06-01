package com.citsh.keyvalue.service;

import com.citsh.keyvalue.entity.Prop;
import java.util.List;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public abstract interface PropService
{
  public abstract void remove(Query paramQuery);

  public abstract List<Prop> find(Query paramQuery);

  public abstract void insert(Prop paramProp);

  public abstract void update(Query paramQuery, Update paramUpdate);

  public abstract void save(Prop paramProp);
}