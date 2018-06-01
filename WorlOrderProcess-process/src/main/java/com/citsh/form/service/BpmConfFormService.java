package com.citsh.form.service;

import com.citsh.form.entity.BpmConfForm;
import java.util.List;

public interface BpmConfFormService {
	public List<BpmConfForm> listBySQL(String paramString, Object... paramArrayOfObject);

	public BpmConfForm listBySQLOne(String paramString, Object... paramArrayOfObject);

	public void save(BpmConfForm paramBpmConfForm);

	public BpmConfForm findById(Long paramLong);

	public void remove(Long paramLong);
}