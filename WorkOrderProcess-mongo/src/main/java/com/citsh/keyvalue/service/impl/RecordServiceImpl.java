package com.citsh.keyvalue.service.impl;

import com.citsh.keyvalue.entity.Record;
import com.citsh.keyvalue.mdao.RecordDao;
import com.citsh.keyvalue.service.RecordService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class RecordServiceImpl
  implements RecordService
{

  @Autowired
  private RecordDao recordDao;

  public Record findById(Long valueOf)
  {
    return this.recordDao.findById(valueOf);
  }

  public Record findOne(Query query) {
    return this.recordDao.findOne(query);
  }

  public void remove(Query query) {
    this.recordDao.remove(query);
  }

  public List<Record> find(Query query)
  {
    return this.recordDao.find(query);
  }

  public long count(Query query) {
    return this.recordDao.count(query);
  }

  public void save(Record record) {
    this.recordDao.save(record);
  }

  public void update(Query query, Update update)
  {
    this.recordDao.update(query, update);
  }
}