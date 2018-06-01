package com.citsh.grop.servcie;

import java.util.List;

import org.springframework.stereotype.Service;

import com.citsh.grop.dao.BpmCategoryDao;
import com.citsh.grop.entity.BpmCategory;
import com.citsh.page.Page;
import com.citsh.query.PropertyFilter;

@Service
public interface BpmCategoryService {
	/**
	 * 保存
	 */
	public BpmCategory add(BpmCategory entity);

	/**
	 * 查询
	 */
	public BpmCategory find(Long id);

	/**
	 * 查询集合
	 */
	public List<BpmCategory> findAll();

	/**
	 * 查询总数
	 */
	public long count();

	/**
	 * 通过sql查询集合
	 */
	public List<BpmCategory> listBySQL(String sql);

	/**
	 * 根据实体id删除
	 */
	public void deleteById(Object entityId);

	/**
	 * 根据实体id删除数组
	 */
	public void deleteByIds(Object[] entityIds);

	/**
	 * 修改
	 */
	public void update(BpmCategory entity);

	/**
	 * 根据条件修改 variable="xxx=?,xxx=?";//要修改的字段，参数用？ condition=" xxx=? and xxx=?"
	 * ;//条件 updateByCondition(variable, condition, "参数1","参数2","参数3","参数4")
	 */
	public boolean updateByCondition(String variable, String condition, Object... args);

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
	public Page pagedQuery(Page page, List<PropertyFilter> propertyFilters);

	/**
	 * 保存
	 */
	public void save(BpmCategory bpmCategory);
}