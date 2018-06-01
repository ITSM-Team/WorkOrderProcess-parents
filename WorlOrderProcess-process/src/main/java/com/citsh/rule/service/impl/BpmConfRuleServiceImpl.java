package com.citsh.rule.service.impl;

import com.citsh.rule.dao.BpmConfRuleDao;
import com.citsh.rule.entity.BpmConfRule;
import com.citsh.rule.service.BpmConfRuleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BpmConfRuleServiceImpl
  implements BpmConfRuleService
{

  @Autowired
  private BpmConfRuleDao bpmConfRuleDao;

  public List<BpmConfRule> listBySQL(String condition, Object[] args)
  {
    return this.bpmConfRuleDao.listBySQL(condition, args);
  }

  public void save(BpmConfRule bpmConfRule) {
    this.bpmConfRuleDao.save(bpmConfRule);
  }

  public void remove(Long id) {
    this.bpmConfRuleDao.delete(id);
  }

  public BpmConfRule findById(Long id) {
    return (BpmConfRule)this.bpmConfRuleDao.findOne(id);
  }
}