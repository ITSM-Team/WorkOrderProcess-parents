package com.citsh.dao.jpa.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.util.Assert;

import com.citsh.dao.jpa.BaseDao;
import com.citsh.page.Page;
import com.citsh.query.PropertyFilter;
import com.citsh.query.QueuePage;

public class BaseDaoImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseDao<T, ID> {

	private Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);
	// 操作数据库
	@PersistenceContext
	private EntityManager em;

	// 父类没有不带参数的构造方法，这里手动构造父类
	public BaseDaoImpl(Class<T> domainClass, EntityManager entityManager) {
		super(domainClass, entityManager);
		this.em = entityManager;
	}

	/**
	 * 保存
	 */
	@Transactional
	public T add(T entity) {
		em.persist(entity);
		return entity;
	}

	public T saveEntity(T entity) {
		return save(entity);
	}

	/**
	 * 查询
	 */
	public T find(Long id) {
		return em.find(getDomainClass(), id);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		Query query = em.createQuery("from " + getDomainClass().getSimpleName() + " ");
		return query.getResultList();
	}

	public long count() {
		Query query = em.createQuery("from " + getDomainClass().getSimpleName() + " ");
		return query.getResultList().size();
	}

	/**
	 * 通过EntityManager查询
	 */
	@SuppressWarnings("unchecked")
	public List<T> listBySQL(String sql) {
		return em.createNativeQuery(sql).getResultList();
	}

	/**
	 * @param condition
	 *            条件
	 * @param args
	 *            参数
	 */
	@SuppressWarnings("unchecked")
	public List<T> listBySQL(String condition, Object... args) {
		Query queue = em.createQuery("from " + getDomainClass().getSimpleName() + " as o where " + condition);
		for (int i = 0; i < args.length; i++) {
			queue.setParameter(i + 1, args[i]);
		}
		return queue.getResultList();
	}

	/**
	 * @param name
	 *            查询条件
	 * @param entity
	 *            参数
	 */
	@SuppressWarnings("unchecked")
	public List<T> findBy(String name, Long id) {
		String sql = "from " + getDomainClass().getSimpleName() + " where " + name + "=" + id;
		return em.createQuery(sql).getResultList();
	}

	/**
	 * @param name
	 *            查询条件
	 * @param entity
	 *            参数
	 */
	@SuppressWarnings("unchecked")
	public T findOneBy(String name, Long id) {
		String sql = "from " + getDomainClass().getSimpleName() + " where " + name + "=" + id;
		List<T> list = em.createQuery(sql).getResultList();
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据实体id删除
	 */
	@Transactional
	public void deleteById(Object entityId) {
		deleteByIds(new Object[] { entityId });
	}

	/**
	 * 根据实体id删除数组
	 */
	@Transactional
	public void deleteByIds(Object[] entityIds) {
		for (Object id : entityIds) {
			em.remove(em.getReference(getDomainClass(), id));
		}
	}

	/**
	 * 修改
	 */
	@Transactional
	public void update(T entity) {
		em.merge(entity);
	}

	/**
	 * 根据条件修改 variable="xxx=?,xxx=?";//要修改的字段，参数用？ condition=" xxx=? and xxx=?"
	 * ;//条件 updateByCondition(variable, condition, "参数1","参数2","参数3","参数4")
	 */
	@Transactional
	public boolean updateByCondition(String variable, String condition, Object... args) {
		boolean flag = false;
		try {
			Query query = em.createQuery(
					"update " + getDomainClass().getSimpleName() + " as o set " + variable + " where " + condition);
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i + 1, args[i]);
			}
			query.executeUpdate();
			flag = true;
			if (logger.isDebugEnabled()) {
				logger.debug("根据条件更新实体成功，" + em.getClass().getName());
			}
		} catch (Exception e) {
			flag = false;
			logger.error("根据条件更新实体失败", e);
		}
		return flag;
	}

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
	public Page pagedQuery(Page page, List<PropertyFilter> propertyFilters) {
		Integer count = (int) count();
		// 返回分页对象
		if (count < 1) {
			return new Page();
		}
		int start = (page.getPageNo() - 1) * page.getPageSize();
		StringBuffer sb = QueuePage.buildCriterion(propertyFilters);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("from " + getDomainClass().getSimpleName());
		if (sb.length() > 0) {
			stringBuffer.append(" where ");
			stringBuffer.append(sb);
		}
		if (page.isOrderEnabled()) {
			stringBuffer.append(" order by ");
			for (int i = 0; i < page.getOrderBys().size(); i++) {
				String orderBy = page.getOrderBys().get(i);
				String order = page.getOrders().get(i);
				if (i > 0) {
					stringBuffer.append(",");
				}
				if (page.getOrders().size()>0 && "ASC".equals(page.getOrders().get(i))) {
					stringBuffer.append(orderBy + " asc");
				} else {
					stringBuffer.append(orderBy + " desc");
				}
			}
		}

		List list = em.createQuery(stringBuffer.toString()).setFirstResult(start).setMaxResults(page.getPageSize())
				.getResultList();

		Page resultPage = new Page(list, count);
		resultPage.setPageNo(page.getPageNo());
		resultPage.setPageSize(page.getPageSize());
		resultPage.setOrderBys(page.getOrderBys());
		resultPage.setOrders(page.getOrders());

		return resultPage;
	}

	public Page pageListByHSQL(String condition, int pageNo, int pageSize, Object[] args) {
		Assert.isTrue(pageNo >= 1, "pageNo should be eg 1");
		Query queue = this.em.createQuery("from " + getDomainClass().getSimpleName() + " as o where " + condition);
		for (int i = 0; i < args.length; i++) {
			queue.setParameter(i + 1, args[i]);
		}
		int start = (pageNo - 1) * pageSize;
		queue.setFirstResult(start).setMaxResults(pageSize);
		List list = queue.getResultList();
		Integer totalCount = countByHSQL(condition, args);
		Page page = new Page(list, totalCount.intValue());
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		return page;
	}

	public Page pageListByHSQL(String hsql, int pageNo, int pageSize, Map<String, Object> map) {
		Query queue = this.em.createQuery(hsql);
		for (Map.Entry entry : map.entrySet()) {
			queue.setParameter((String) entry.getKey(), entry.getValue());
		}
		int start = (pageNo - 1) * pageSize;
		queue.setFirstResult(start).setMaxResults(pageSize);
		List list = queue.getResultList();
		Integer totalCount = countByHSQL(hsql, new Object[] { map });
		Page page = new Page(list, totalCount.intValue());
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		return page;
	}

	public Integer countByHSQL(String hsql, Map<String, String> map) {
		Query queue = this.em.createQuery(hsql);
		for (Map.Entry entry : map.entrySet()) {
			queue.setParameter((String) entry.getKey(), entry.getValue());
		}
		return Integer.valueOf(queue.getResultList().size());
	}

	public Integer countByHSQL(String condition, Object[] args) {
		Query queue = this.em.createQuery("from " + getDomainClass().getSimpleName() + " as o where " + condition);
		for (int i = 0; i < args.length; i++) {
			queue.setParameter(i + 1, args[i]);
		}
		return Integer.valueOf(queue.getResultList().size());
	}

}