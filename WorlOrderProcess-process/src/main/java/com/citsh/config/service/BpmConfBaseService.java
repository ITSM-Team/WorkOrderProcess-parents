
package com.citsh.config.service;

import com.citsh.config.entity.BpmConfBase;
import java.util.List;

public interface BpmConfBaseService {
	public List<BpmConfBase> findAll();

	public BpmConfBase find(Long paramLong);

	public BpmConfBase findByHSQLOne(String condition, Object... objects);
	
	public void save(BpmConfBase bpmConfBase);
}
