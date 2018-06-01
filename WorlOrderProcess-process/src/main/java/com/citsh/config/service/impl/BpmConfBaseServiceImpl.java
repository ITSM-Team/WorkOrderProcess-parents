
package com.citsh.config.service.impl;

import com.citsh.config.dao.BpmConfBaseDao;
import com.citsh.config.entity.BpmConfBase;
import com.citsh.config.service.BpmConfBaseService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class BpmConfBaseServiceImpl implements BpmConfBaseService {

	@Autowired
	private BpmConfBaseDao bpmConfBaseDao;

	public List<BpmConfBase> findAll() {
		return this.bpmConfBaseDao.findAll();
	}

	public BpmConfBase find(Long id) {
		return this.bpmConfBaseDao.find(id);
	}

	public BpmConfBase findByHSQLOne(String condition, Object... objects) {
		List<BpmConfBase> bpmConfBases = bpmConfBaseDao.listBySQL(condition, objects);
		if (bpmConfBases.size() > 0) {
			return bpmConfBases.get(0);
		}
		return null;
	}

	public void save(BpmConfBase bpmConfBase) {
		bpmConfBaseDao.save(bpmConfBase);
	}

}

