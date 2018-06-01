package com.citsh.grop.servcie;

import com.citsh.grop.entity.BpmCategory;
import com.citsh.page.Page;
import com.citsh.query.PropertyFilter;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public abstract interface BpmCategoryService
{
  public abstract BpmCategory add(BpmCategory paramBpmCategory);

  public abstract BpmCategory find(Long paramLong);

  public abstract List<BpmCategory> findAll();

  public abstract long count();

  public abstract List<BpmCategory> listBySQL(String paramString);

  public abstract void deleteById(Object paramObject);

  public abstract void deleteByIds(Object[] paramArrayOfObject);

  public abstract void update(BpmCategory paramBpmCategory);

  public abstract boolean updateByCondition(String paramString1, String paramString2, Object[] paramArrayOfObject);

  public abstract Page pagedQuery(Page paramPage, List<PropertyFilter> paramList);

  public abstract void save(BpmCategory paramBpmCategory);
}