package com.citsh.keyvalue.mdao;

import com.citsh.keyvalue.entity.Record;
import com.citsh.mdao.mongo.BaseMongoDao;
import org.springframework.stereotype.Repository;

@Repository
public class RecordDao extends BaseMongoDao<Record> {

	@Override
	protected Class<Record> getEntityClass() {
		// TODO Auto-generated method stub
		return Record.class;
	}
}