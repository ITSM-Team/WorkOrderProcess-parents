package com.citsh.dao.jpa;

import com.citsh.page.Page;
import com.citsh.query.PropertyFilter;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public abstract interface BaseDao<T, ID extends Serializable> extends JpaRepository<T, ID>
{
  public abstract T add(T paramT);

  public abstract T find(Long paramLong);

  public abstract List<T> findAll();

  public abstract long count();

  public abstract List<T> listBySQL(String paramString);

  public abstract List<T> listBySQL(String paramString, Object[] paramArrayOfObject);

  public abstract void deleteById(Object paramObject);

  public abstract void deleteByIds(Object[] paramArrayOfObject);

  public abstract boolean update(T paramT);

  public abstract boolean updateByCondition(String paramString1, String paramString2, Object[] paramArrayOfObject);
  
  public abstract Page pagedQuery(Page paramPage, List<PropertyFilter> paramList);

  public abstract List<T> findBy(String paramString, Long paramLong);

  public abstract T findOneBy(String paramString, Long paramLong);

  public abstract List<T> listByMSQL(String paramString, Object[] paramArrayOfObject);

  public abstract Page pageListByHSQL(String paramString, int paramInt1, int paramInt2, Object[] paramArrayOfObject);

  public abstract Integer countByHSQL(String paramString, Object[] paramArrayOfObject);

  public abstract Page pageListByHSQL(String paramString, int paramInt1, int paramInt2, Map<String, Object> paramMap);
}