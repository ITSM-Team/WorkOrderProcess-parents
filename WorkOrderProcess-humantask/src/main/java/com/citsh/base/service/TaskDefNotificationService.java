package com.citsh.base.service;

import com.citsh.base.entity.TaskDefNotification;
import java.util.List;

public abstract interface TaskDefNotificationService {
	public abstract List<TaskDefNotification> findByBaseCodeAndBaseProcessId(String paramString,
			Object... paramArrayOfObject);

	public abstract TaskDefNotification findByBaseCodeAndBaseProcessIdOne(String paramString,
			Object... paramArrayOfObject);

	public abstract void save(TaskDefNotification paramTaskDefNotification);

	public abstract void remove(TaskDefNotification paramTaskDefNotification);
}
