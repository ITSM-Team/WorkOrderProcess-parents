package com.citsh.mdao.mongo;

import com.citsh.mdao.mongo.BaseMongoDao;
import com.citsh.page.Page;
import com.citsh.query.PropertyFilter;
import com.citsh.util.MongodbQueryUtil;
import com.citsh.util.ReflectionUtils;
import com.mongodb.WriteResult;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
@Repository
public abstract class BaseMongoDao<T>{

	@Autowired
	protected MongoTemplate mongoTemplate;

	protected abstract Class<T> getEntityClass();

	public T save(T entity) {
		this.mongoTemplate.save(entity);
		return entity;
	}

	public T insert(T entity) {
		this.mongoTemplate.insert(entity);
		return entity;
	}

	public T findById(Long id) {
		return this.mongoTemplate.findById(id, getEntityClass());
	}

	public T findById(String id, String collectionName) {
		return this.mongoTemplate.findById(id, getEntityClass(), collectionName);
	}

	public List<T> findAll() {
		return this.mongoTemplate.findAll(getEntityClass());
	}

	public List<T> findAll(String collectionName) {
		return this.mongoTemplate.findAll(getEntityClass(), collectionName);
	}

	public List<T> find(Query query) {
		return this.mongoTemplate.find(query, getEntityClass());
	}

	public T findOne(Query query) {
		return this.mongoTemplate.findOne(query, getEntityClass());
	}

	public Page findPage(Page page, List<PropertyFilter> propertyFilters, Query query) {
		Map map = MongodbQueryUtil.buildCriterion(propertyFilters);
		List andlist = new ArrayList();
		List orlist = new ArrayList();

		andlist = (List) map.get("and");

		orlist = (List) map.get("or");
		Criteria criteria = new Criteria();

		if ((andlist != null) && (andlist.size() > 0) && (orlist != null) && (orlist.size() > 0))
			criteria.andOperator((Criteria[]) andlist.toArray(new Criteria[andlist.size()]))
					.orOperator((Criteria[]) orlist.toArray(new Criteria[orlist.size()]));
		else if ((andlist != null) && (andlist.size() > 0))
			criteria.andOperator((Criteria[]) andlist.toArray(new Criteria[andlist.size()]));
		else if ((orlist != null) && (orlist.size() > 0)) {
			criteria.orOperator((Criteria[]) orlist.toArray(new Criteria[orlist.size()]));
		}
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		int no = (pageNo - 1) * pageSize;

		query.addCriteria(criteria);
		query = query.skip(no).limit(pageSize);
		List list = this.mongoTemplate.find(query, getEntityClass());
		long count = this.mongoTemplate.count(query, getEntityClass());
		page.setResult(list);
		page.setTotalCount(count);
		map = null;
		andlist = null;
		orlist = null;
		return page;
	}

	public long count(Query query) {
		return this.mongoTemplate.count(query, getEntityClass());
	}

	public WriteResult update(Query query, Update update) {
		if (update == null) {
			return null;
		}
		return this.mongoTemplate.updateMulti(query, update, getEntityClass());
	}

	public T updateOne(Query query, Update update) {
		if (update == null) {
			return null;
		}
		return this.mongoTemplate.findAndModify(query, update, getEntityClass());
	}

	public WriteResult update(T entity) {
		Field[] fields = getEntityClass().getDeclaredFields();
		if ((fields == null) || (fields.length <= 0)) {
			return null;
		}
		Field idfield = null;

		for (Field field : fields) {
			if ((field.getName() != null) && ("id".equals(field.getName().toLowerCase()))) {
				idfield = field;
				break;
			}
		}
		if (idfield == null) {
			return null;
		}
		idfield.setAccessible(true);

		Object id = null;
		try {
			id = idfield.get(entity);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		if ((id == null) || ("".equals(id.toString().trim()))) {
			return null;
		}

		Query query = new Query(Criteria.where("_id").is(id));

		Update update = ReflectionUtils.getUpdateObj(entity);
		if (update == null) {
			return null;
		}
		return this.mongoTemplate.updateFirst(query, update, getEntityClass());
	}

	public void remove(Query query) {
		this.mongoTemplate.remove(query, getEntityClass());
	}

	public void remove(Long id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		this.mongoTemplate.remove(query, getEntityClass());
	}
}