package com.citsh.button.service.impl;

import com.citsh.button.dao.ButtoInfoDao;
import com.citsh.button.entity.ButtonInfo;
import com.citsh.button.service.ButtonInfoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ButtonInfoServiceImpl
  implements ButtonInfoService
{

  @Autowired
  private ButtoInfoDao buttoInfoDao;

  public List<ButtonInfo> findAll()
  {
    return this.buttoInfoDao.findAll();
  }

  public ButtonInfo listBySQL(String condition, Object[] objects) {
    List buttonInfos = this.buttoInfoDao.listBySQL(condition, objects);
    if (buttonInfos.size() > 0) {
      return (ButtonInfo)buttonInfos.get(0);
    }
    return null;
  }
}