package com.citsh.activiti.service.impl;

import com.citsh.activiti.service.QueryRuTaskService;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Service;

@Service
public class QueryRuTaskServiceImpl
  implements QueryRuTaskService
{

  @PersistenceContext
  private EntityManager em;

  public Integer count(String sql, Object[] args)
  {
    Query query = this.em.createNamedQuery(sql);
    for (int i = 0; i < args.length; i++) {
      query.setParameter(i + 1, args[i]);
    }
    Integer count = Integer.valueOf(query.getResultList().size());
    return count;
  }
}