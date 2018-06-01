package com.citsh.dao.jpa;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import com.citsh.page.Page;
import com.citsh.query.PropertyFilter;
@NoRepositoryBean
public interface BaseDao<T,ID extends Serializable> extends  JpaRepository<T,ID>{
	/**
     * 保存
     * */
	 public T add(T entity);
	 /**
	* 查询
	* */
	 public T find(Long id);
	 
	 /**
	  * 查询集合
	  * */
	 public List<T> findAll();
	 
	 /**
	  * 查询总数
	  * */
	 public long count();
	 
	 /**
	  * 通过sql查询集合
	  * */
	 public List<T> listBySQL(String sql);
	 
	 /**
	  * 通过sql查询集合
	  * */
	 public List<T> listBySQL(String condition,Object... objects);
	 
	 /**
		 * 根据实体id删除
		 * */
	public void deleteById(Object entityId);
	
	/**
	 * 根据实体id删除数组
	 * */
	public void deleteByIds(Object[] entityIds);
	
	/**
	 * 修改
	 * */
	public boolean update(T entity);
	
	/**
	 * 根据条件修改 
	 * variable="xxx=?,xxx=?";//要修改的字段，参数用？
	 * condition=" xxx=? and xxx=?";//条件 
	 * updateByCondition(variable, condition, "参数1","参数2","参数3","参数4")
	 * */
	public boolean updateByCondition(String variable,String condition,Object... args);
	
	/**
     * 分页查询函数，根据entityClass和page参数进行查询.
     * 
     * @param <T>
     *            实体类型
     * @param entityClass
     *            实体类型
     * @param page
     *            分页里包含的各种参数
     * @param criterions
     *            条件
     * @return 含总记录数和当前页数据的Page对象.
     */
    public Page pagedQuery(Page page,List<PropertyFilter> propertyFilters);
    
	/**
	 * @param name 查询条件
	 * @param entity 参数
	 * */
	public List<T> findBy(String name,Long id);
	
	public T findOneBy(String name,Long id);
	
	public  Page pageListByHSQL(String paramString, int paramInt1, int paramInt2, Object[] paramArrayOfObject);
	
	public Page pageListByHSQL(String hsql, int pageNo, int pageSize, Map<String, Object> map);
}