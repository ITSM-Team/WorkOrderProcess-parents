package com.citsh.keyvalue.service.impl;

import com.citsh.keyvalue.entity.Prop;
import com.citsh.keyvalue.mdao.PropDao;
import com.citsh.keyvalue.service.PropService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class PropServiceImpl
  implements PropService
{

  @Autowired
  private PropDao propDao;

  public void remove(Query query)
  {
    this.propDao.remove(query);
  }

  public List<Prop> find(Query query)
  {
    return this.propDao.find(query);
  }

  public void insert(Prop prop) {
    this.propDao.insert(prop);
  }

  public void update(Query query, Update set)
  {
    this.propDao.update(query, set);
  }

  public void save(Prop prop)
  {
    this.propDao.save(prop);
  }
}