package com.citsh.keyvalue.mdao;

import com.citsh.keyvalue.entity.Prop;
import com.citsh.mdao.mongo.BaseMongoDao;
import org.springframework.stereotype.Repository;

@Repository
public class PropDao extends BaseMongoDao<Prop> {

	@Override
	protected Class<Prop> getEntityClass() {
		// TODO Auto-generated method stub
		return Prop.class;
	}
}