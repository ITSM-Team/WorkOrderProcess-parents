package com.citsh.base.service;

import com.citsh.base.entity.TaskConfUser;

public interface TaskConfUserService {
	public TaskConfUser findByCodeAndProIdOne(String paramString, Object... paramArrayOfObject);

	public TaskConfUser findBySQL(String paramString, Object... paramArrayOfObject);

	public void save(TaskConfUser paramTaskConfUser);
}
