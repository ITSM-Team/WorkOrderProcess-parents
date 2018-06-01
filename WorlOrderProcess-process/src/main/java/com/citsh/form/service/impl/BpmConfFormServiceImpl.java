package com.citsh.form.service.impl;

import com.citsh.form.dao.BpmConfFormDao;
import com.citsh.form.entity.BpmConfForm;
import com.citsh.form.service.BpmConfFormService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Transactional
@Service
public class BpmConfFormServiceImpl implements BpmConfFormService {

	@Autowired
	private BpmConfFormDao bpmConfFormDao;

	public List<BpmConfForm> listBySQL(String condition, Object... args) {
		return this.bpmConfFormDao.listBySQL(condition, args);
	}

	public BpmConfForm listBySQLOne(String condition, Object... args) {
		List<BpmConfForm> list = this.bpmConfFormDao.listBySQL(condition, args);
		if (list.size() > 0) {
			return (BpmConfForm) list.get(0);
		}
		return null;
	}

	public void save(BpmConfForm bpmConfForm) {
		this.bpmConfFormDao.save(bpmConfForm);
	}

	public BpmConfForm findById(Long id) {
		return (BpmConfForm) this.bpmConfFormDao.findOne(id);
	}

	public void remove(Long id) {
		this.bpmConfFormDao.delete(id);
	}
}