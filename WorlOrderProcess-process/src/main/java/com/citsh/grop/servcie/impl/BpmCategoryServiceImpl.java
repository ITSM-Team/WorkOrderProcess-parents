package com.citsh.grop.servcie.impl;

import com.citsh.grop.dao.BpmCategoryDao;
import com.citsh.grop.entity.BpmCategory;
import com.citsh.grop.servcie.BpmCategoryService;
import com.citsh.page.Page;
import com.citsh.query.PropertyFilter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BpmCategoryServiceImpl
  implements BpmCategoryService
{

  @Autowired
  private BpmCategoryDao bpmCategoryDao;

  public BpmCategory add(BpmCategory entity)
  {
    return (BpmCategory)this.bpmCategoryDao.add(entity);
  }

  public BpmCategory find(Long id)
  {
    return (BpmCategory)this.bpmCategoryDao.find(id);
  }

  public List<BpmCategory> findAll()
  {
    return this.bpmCategoryDao.findAll();
  }

  public long count()
  {
    return this.bpmCategoryDao.count();
  }

  public List<BpmCategory> listBySQL(String sql)
  {
    return this.bpmCategoryDao.listBySQL(sql);
  }

  public void deleteById(Object entityId) {
    this.bpmCategoryDao.deleteById(entityId);
  }

  public void deleteByIds(Object[] entityIds)
  {
    this.bpmCategoryDao.deleteByIds(entityIds);
  }

  public void update(BpmCategory entity)
  {
    this.bpmCategoryDao.update(entity);
  }

  public boolean updateByCondition(String variable, String condition, Object[] args)
  {
    return this.bpmCategoryDao.updateByCondition(variable, condition, args);
  }

  public Page pagedQuery(Page page, List<PropertyFilter> propertyFilters)
  {
    return this.bpmCategoryDao.pagedQuery(page, propertyFilters);
  }

  public void save(BpmCategory bpmCategory) {
    this.bpmCategoryDao.save(bpmCategory);
  }
}