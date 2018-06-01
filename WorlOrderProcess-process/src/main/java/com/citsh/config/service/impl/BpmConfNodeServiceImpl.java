
package com.citsh.config.service.impl;

import com.citsh.config.dao.BpmConfNodeDao;
import com.citsh.config.entity.BpmConfBase;
import com.citsh.config.entity.BpmConfNode;
import com.citsh.config.service.BpmConfNodeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Transactional
@Service
public class BpmConfNodeServiceImpl implements BpmConfNodeService {

	@Autowired
	private BpmConfNodeDao bpmConfNodeDao;

	public List<BpmConfNode> findByBpmConfBase(Long id) {
		String sql = " bpmConfBase.id=? order by priority";
		return this.bpmConfNodeDao.listBySQL(sql,id);
	}

	public BpmConfNode find(Long id) {
		return (BpmConfNode) this.bpmConfNodeDao.find(id);
	}

	@Override
	public BpmConfNode findByHSQLOne(String condition, Object... objects) {
		List<BpmConfNode> bpmConfNodes=bpmConfNodeDao.listBySQL(condition, objects);
		if(bpmConfNodes.size()>0){
			return bpmConfNodes.get(0);
		}
		return null;
	}

	@Override
	public void save(BpmConfNode bpmConfNode) {
		bpmConfNodeDao.save(bpmConfNode);
		
	}
}

