
package com.citsh.config.service.impl;

import com.citsh.config.dao.BpmConfCountersignDao;
import com.citsh.config.entity.BpmConfCountersign;
import com.citsh.config.service.BpmConfCountersignService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Transactional
@Service
public class BpmConfCountersignServiceImpl implements BpmConfCountersignService {

	@Autowired
	private BpmConfCountersignDao bpmConfCountersignDao;

	public List<BpmConfCountersign> findBy(String name, Long id) {
		return this.bpmConfCountersignDao.findBy(name, id);
	}

	public BpmConfCountersign findOneBy(String name, Long id) {
		return  this.bpmConfCountersignDao.findOneBy(name, id);
	}

	@Override
	public void save(BpmConfCountersign bpmConfCountersign) {
		bpmConfCountersignDao.save(bpmConfCountersign);
	}
}

