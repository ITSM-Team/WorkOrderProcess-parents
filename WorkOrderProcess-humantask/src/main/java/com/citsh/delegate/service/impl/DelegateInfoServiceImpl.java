package com.citsh.delegate.service.impl;

import com.citsh.delegate.dao.DelegateInfoDao;
import com.citsh.delegate.entity.DelegateInfo;
import com.citsh.delegate.service.DelegateInfoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DelegateInfoServiceImpl
  implements DelegateInfoService
{

  @Autowired
  private DelegateInfoDao delegateInfoDao;

  public List<DelegateInfo> listBySQL(String condition, Object[] objects)
  {
    return this.delegateInfoDao.listBySQL(condition, objects);
  }

  public void remove(Long id) {
    this.delegateInfoDao.delete(id);
  }

  public void save(DelegateInfo delegateInfo) {
    this.delegateInfoDao.save(delegateInfo);
  }
}