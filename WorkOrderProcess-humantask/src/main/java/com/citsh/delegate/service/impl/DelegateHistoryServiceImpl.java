package com.citsh.delegate.service.impl;

import com.citsh.delegate.dao.DelegateHistoryDao;
import com.citsh.delegate.entity.DelegateHistory;
import com.citsh.delegate.service.DelegateHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DelegateHistoryServiceImpl
  implements DelegateHistoryService
{

  @Autowired
  private DelegateHistoryDao delegateHistoryDao;

  public void save(DelegateHistory delegateHistory)
  {
    this.delegateHistoryDao.save(delegateHistory);
  }
}