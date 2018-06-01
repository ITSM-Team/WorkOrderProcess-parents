package com.citsh.notice.service.impl;

import com.citsh.notice.dao.BpmConfNoticeDao;
import com.citsh.notice.entity.BpmConfNotice;
import com.citsh.notice.service.BpmConfNoticeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class BpmConfNoticeServiceImpl
  implements BpmConfNoticeService
{

  @Autowired
  private BpmConfNoticeDao bpmConfNoticeDao;

  public List<BpmConfNotice> listBySQL(String condition, Object[] objects)
  {
    return this.bpmConfNoticeDao.listBySQL(condition, objects);
  }

  public void save(BpmConfNotice bpmConfNotice) {
    this.bpmConfNoticeDao.save(bpmConfNotice);
  }

  public BpmConfNotice findById(Long id) {
    return (BpmConfNotice)this.bpmConfNoticeDao.find(id);
  }

  public void remove(Long id) {
    this.bpmConfNoticeDao.delete(id);
  }
}