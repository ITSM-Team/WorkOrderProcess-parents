package com.citsh.dao.jpa.impl;

import com.citsh.dao.jpa.BaseDao;
import com.citsh.page.Page;
import com.citsh.query.PropertyFilter;
import com.citsh.query.QueuePage;
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

public class BaseDaoImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseDao<T, ID> {
	private Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);

	@PersistenceContext
	private EntityManager em;

	public BaseDaoImpl(Class<T> domainClass, EntityManager entityManager) {
		super(domainClass, entityManager);
		this.em = entityManager;
	}

	@Transactional
	public T add(T entity) {
		this.em.persist(entity);
		return entity;
	}

	public T saveEntity(T entity) {
		return save(entity);
	}

	public T find(Long id) {
		return this.em.find(getDomainClass(), id);
	}

	public List<T> findAllEntity() {
		return findAll();
	}

	public long countsEntity() {
		return count();
	}

	@SuppressWarnings("unchecked")
	public List<T> listBySQL(String sql) {
		return this.em.createQuery(sql).getResultList();
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

	@SuppressWarnings("unchecked")
	public List<T> listByMSQL(String sql, Object[] args) {
		Query queue = this.em.createNamedQuery(sql);
		for (int i = 0; i < args.length; i++) {
			queue.setParameter(i + 1, args[i]);
		}
		return queue.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> listBySQL(String condition, Object[] args) {
		Query queue = this.em.createQuery("from " + getDomainClass().getSimpleName() + " as o where " + condition);
		for (int i = 0; i < args.length; i++) {
			queue.setParameter(i + 1, args[i]);
		}
		return queue.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findBy(String name, Long id) {
		String sql = " from " + getDomainClass().getSimpleName() + " where " + name + "=" + id;
		return this.em.createQuery(sql).getResultList();
	}

	@SuppressWarnings("unchecked")
	public T findOneBy(String name, Long id) {
		String sql = "from " + getDomainClass().getSimpleName() + " where " + name + "=" + id;
		List<T> list = this.em.createQuery(sql).getResultList();
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Transactional
	public void deleteById(Object entityId) {
		deleteByIds(new Object[] { entityId });
	}

	@Transactional
	public void deleteByIds(Object... entityIds) {
		for (Object id : entityIds)
			this.em.remove(this.em.getReference(getDomainClass(), id));
	}

	@Transactional
	public boolean update(T entity) {
		T t = this.em.merge(entity);
		if (t != null) {
			return true;
		} else {
			return false;
		}
	}

	@Transactional
	public boolean updateByCondition(String variable, String condition, Object[] args) {
		boolean flag = false;
		try {
			Query query = this.em.createQuery(
					"update " + getDomainClass().getSimpleName() + " as o set " + variable + " where " + condition);
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i + 1, args[i]);
			}
			query.executeUpdate();
			flag = true;
			if (this.logger.isDebugEnabled())
				this.logger.debug("根据条件更新实体成功，" + this.em.getClass().getName());
		} catch (Exception e) {
			flag = false;
			this.logger.error("根据条件更新实体失败", e);
		}
		return flag;
	}

	public Page pagedQuery(Page page, List<PropertyFilter> propertyFilters) {
		Integer count = Integer.valueOf((int) count());

		if (count.intValue() < 1) {
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
			for (int i = 0; i < page.getOrderBys().size(); i++) {
				String orderBy = (String) page.getOrderBys().get(i);
				String order = (String) page.getOrders().get(i);
				if ("ASC".equals(page.getOrders().get(i)))
					stringBuffer.append(" order by " + orderBy + " asc");
				else {
					stringBuffer.append(" order by " + orderBy + " desc");
				}
			}

		}

		List list = this.em.createQuery(stringBuffer.toString()).setFirstResult(start).setMaxResults(page.getPageSize())
				.getResultList();

		Page resultPage = new Page(list, count.intValue());
		resultPage.setPageNo(page.getPageNo());
		resultPage.setPageSize(page.getPageSize());
		resultPage.setOrderBys(page.getOrderBys());
		resultPage.setOrders(page.getOrders());

		return resultPage;
	}
}