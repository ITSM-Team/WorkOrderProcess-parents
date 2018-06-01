
package com.citsh.config.service;

import com.citsh.config.entity.BpmConfCountersign;
import java.util.List;

public interface BpmConfCountersignService {
	public List<BpmConfCountersign> findBy(String paramString, Long paramLong);

	public BpmConfCountersign findOneBy(String paramString, Long paramLong);

	public void save(BpmConfCountersign bpmConfCountersign);
}

