package com.citsh.user.service.impl;

import com.citsh.user.dao.BpmConfUserDao;
import com.citsh.user.entity.BpmConfUser;
import com.citsh.user.service.BpmConfUserService;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class BpmConfUserServiceImpl
  implements BpmConfUserService
{

  @Autowired
  private BpmConfUserDao bpmConfUserDao;

  public List<BpmConfUser> findBy(String name, Long id)
  {
    return this.bpmConfUserDao.findBy(name, id);
  }

  public BpmConfUser findByNodeIdAndStatusAndType(Long id, int status, int type) {
    String sql = "from BpmConfUser where bpmConfNode.id=" + id + " and type=" + status + " and status=" + type;
    List list = this.bpmConfUserDao.listBySQL(sql);
    BpmConfUser bpmConfUser = new BpmConfUser();
    if (list.size() > 0) {
      bpmConfUser = (BpmConfUser)list.get(0);
    }
    return bpmConfUser;
  }

  public BpmConfUser save(BpmConfUser bpmConfUser) {
    return (BpmConfUser)this.bpmConfUserDao.save(bpmConfUser);
  }

  public BpmConfUser findById(Long id) {
    return (BpmConfUser)this.bpmConfUserDao.find(id);
  }

  public void remove(BpmConfUser bpmConfUser) {
    this.bpmConfUserDao.delete(bpmConfUser);
  }

  public List<BpmConfUser> listBySQL(String condition, Object[] objects) {
    return this.bpmConfUserDao.listBySQL(condition, objects);
  }
}