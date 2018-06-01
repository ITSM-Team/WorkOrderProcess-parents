package com.citsh.grop.servcie.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citsh.grop.dao.BpmCategoryDao;
import com.citsh.grop.entity.BpmCategory;
import com.citsh.grop.servcie.BpmCategoryService;
import com.citsh.page.Page;
import com.citsh.query.PropertyFilter;

@Service
public class BpmCategoryServiceImpl implements BpmCategoryService {

	@Autowired
	private BpmCategoryDao bpmCategoryDao;

	public BpmCategory add(BpmCategory entity) {
		// TODO Auto-generated method stub
		return bpmCategoryDao.add(entity);
	}

	public BpmCategory find(Long id) {
		// TODO Auto-generated method stub
		return bpmCategoryDao.find(id);
	}

	public List<BpmCategory> findAll() {
		// TODO Auto-generated method stub
		return bpmCategoryDao.findAll();
	}

	public long count() {
		// TODO Auto-generated method stub
		return bpmCategoryDao.count();
	}

	public List<BpmCategory> listBySQL(String sql) {
		// TODO Auto-generated method stub
		return bpmCategoryDao.listBySQL(sql);
	}

	public void deleteById(Object entityId) {
		bpmCategoryDao.deleteById(entityId);

	}

	public void deleteByIds(Object[] entityIds) {
		bpmCategoryDao.deleteByIds(entityIds);

	}

	public void update(BpmCategory entity) {
		bpmCategoryDao.update(entity);

	}

	public boolean updateByCondition(String variable, String condition, Object... args) {
		// TODO Auto-generated method stub
		return bpmCategoryDao.updateByCondition(variable, condition, args);
	}

	public Page pagedQuery(Page page, List<PropertyFilter> propertyFilters) {
		// TODO Auto-generated method stub
		return bpmCategoryDao.pagedQuery(page, propertyFilters);
	}

	public void save(BpmCategory bpmCategory) {
		bpmCategoryDao.save(bpmCategory);

	}

}
