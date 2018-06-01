package com.citsh.config.service.impl;

import com.citsh.config.dao.BpmTaskConfDao;
import com.citsh.config.entity.BpmTaskConf;
import com.citsh.config.service.BpmTaskConfService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BpmTaskConfServiceImpl
  implements BpmTaskConfService
{

  @Autowired
  private BpmTaskConfDao bpmTaskConfDao;

  public List<BpmTaskConf> listBySQL(String condition, Object[] objects)
  {
    return this.bpmTaskConfDao.listBySQL(condition, objects);
  }

  public BpmTaskConf listBySQLOne(String condition, Object[] objects) {
    List list = this.bpmTaskConfDao.listBySQL(condition, objects);
    if (list.size() > 0) {
      return (BpmTaskConf)list.get(0);
    }
    return null;
  }
}