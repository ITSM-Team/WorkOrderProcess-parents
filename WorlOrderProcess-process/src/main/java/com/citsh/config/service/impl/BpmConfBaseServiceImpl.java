package com.citsh.config.service.impl;

import com.citsh.config.dao.BpmConfBaseDao;
import com.citsh.config.entity.BpmConfBase;
import com.citsh.config.service.BpmConfBaseService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BpmConfBaseServiceImpl
  implements BpmConfBaseService
{

  @Autowired
  private BpmConfBaseDao bpmConfBaseDao;

  public List<BpmConfBase> findAll()
  {
    return this.bpmConfBaseDao.findAll();
  }

  public BpmConfBase find(Long id)
  {
    return (BpmConfBase)this.bpmConfBaseDao.find(id);
  }
}