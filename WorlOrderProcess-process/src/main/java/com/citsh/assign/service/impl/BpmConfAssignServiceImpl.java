package com.citsh.assign.service.impl;

import com.citsh.assign.dao.BpmConfAssignDao;
import com.citsh.assign.entity.BpmConfAssign;
import com.citsh.assign.service.BpmConfAssignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BpmConfAssignServiceImpl
  implements BpmConfAssignService
{

  @Autowired
  private BpmConfAssignDao bpmConfAssignDao;

  public BpmConfAssign findOneBy(String name, Long id)
  {
    return (BpmConfAssign)this.bpmConfAssignDao.findOneBy(name, id);
  }

  public BpmConfAssign findById(Long id) {
    return (BpmConfAssign)this.bpmConfAssignDao.findOne(id);
  }

  public void save(BpmConfAssign bpmConfAssign) {
    this.bpmConfAssignDao.save(bpmConfAssign);
  }
}

