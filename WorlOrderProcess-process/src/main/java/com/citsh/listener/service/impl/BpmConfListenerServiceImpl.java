package com.citsh.listener.service.impl;

import com.citsh.listener.dao.BpmConfListenerDao;
import com.citsh.listener.entity.BpmConfListener;
import com.citsh.listener.service.BpmConfListenerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BpmConfListenerServiceImpl
  implements BpmConfListenerService
{

  @Autowired
  private BpmConfListenerDao bpmConfListenerDao;

  public List<BpmConfListener> listBySQL(String condition, Object[] args)
  {
    return this.bpmConfListenerDao.listBySQL(condition, args);
  }

  public void save(BpmConfListener bpmConfListener) {
    this.bpmConfListenerDao.save(bpmConfListener);
  }

  public void remove(Long id) {
    this.bpmConfListenerDao.delete(id);
  }
}