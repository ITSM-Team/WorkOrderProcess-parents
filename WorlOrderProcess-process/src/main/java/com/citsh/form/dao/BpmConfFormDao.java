package com.citsh.form.dao;

import com.citsh.dao.jpa.BaseDao;
import com.citsh.form.entity.BpmConfForm;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface BpmConfFormDao extends BaseDao<BpmConfForm, Long>
{
}