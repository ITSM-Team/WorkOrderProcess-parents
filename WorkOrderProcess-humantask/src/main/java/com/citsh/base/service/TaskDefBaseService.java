package com.citsh.base.service;

import com.citsh.base.entity.TaskDefBase;

public abstract interface TaskDefBaseService {
	public abstract TaskDefBase findByCodeAndProIdOne(String paramString, Object... paramArrayOfObject);

	public abstract TaskDefBase save(TaskDefBase paramTaskDefBase);
}
