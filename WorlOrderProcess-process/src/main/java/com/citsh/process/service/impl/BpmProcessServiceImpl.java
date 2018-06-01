package com.citsh.process.service.impl;

import com.citsh.page.Page;
import com.citsh.process.dao.BpmProcessDao;
import com.citsh.process.entity.BpmProcess;
import com.citsh.process.service.BpmProcessService;
import com.citsh.query.PropertyFilter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BpmProcessServiceImpl implements BpmProcessService {

	@Autowired
	private BpmProcessDao bpmProcessDao;

	public Page pagedQuery(Page page, List<PropertyFilter> propertyFilters) {
		return this.bpmProcessDao.pagedQuery(page, propertyFilters);
	}

	public List<BpmProcess> findAll() {
		return this.bpmProcessDao.findAll();
	}

	public BpmProcess findById(Long id) {
		return (BpmProcess) this.bpmProcessDao.find(id);
	}

	public BpmProcess saveEntity(BpmProcess bpmProcess) {
		return (BpmProcess) this.bpmProcessDao.save(bpmProcess);
	}

	public void deleteByIds(Object[] entityIds) {
		this.bpmProcessDao.deleteByIds(entityIds);
	}
}
