package com.citsh.operation.service.impl;

import com.citsh.operation.dao.BpmConfOperationDao;
import com.citsh.operation.entity.BpmConfOperation;
import com.citsh.operation.service.BpmConfOperationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class BpmConfOperationServiceImpl
  implements BpmConfOperationService
{

  @Autowired
  private BpmConfOperationDao bpmConfOperationDao;

  public List<BpmConfOperation> listBySQL(String condition, Object[] objects)
  {
    return this.bpmConfOperationDao.listBySQL(condition, objects);
  }

  public BpmConfOperation findById(Long id) {
    return (BpmConfOperation)this.bpmConfOperationDao.findOne(id);
  }

  public void save(BpmConfOperation bpmConfOperation) {
    this.bpmConfOperationDao.save(bpmConfOperation);
  }

  public void remove(Long id) {
    this.bpmConfOperationDao.delete(id);
  }
}