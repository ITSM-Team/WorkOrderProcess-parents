package com.citsh.config.service.impl;

import com.citsh.config.dao.BpmConfNodeDao;
import com.citsh.config.entity.BpmConfBase;
import com.citsh.config.entity.BpmConfNode;
import com.citsh.config.service.BpmConfNodeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BpmConfNodeServiceImpl
  implements BpmConfNodeService
{

  @Autowired
  private BpmConfNodeDao bpmConfNodeDao;

  public List<BpmConfNode> findByBpmConfBase(BpmConfBase bpmConfBase)
  {
    String sql = "from BpmConfNode where bpmConfBase=? order by priority";
    return this.bpmConfNodeDao.listBySQL(sql);
  }

  public BpmConfNode find(Long id)
  {
    return (BpmConfNode)this.bpmConfNodeDao.find(id);
  }
}