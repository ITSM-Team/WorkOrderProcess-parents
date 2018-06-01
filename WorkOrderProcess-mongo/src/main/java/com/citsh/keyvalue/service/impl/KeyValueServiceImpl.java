package com.citsh.keyvalue.service.impl;

import com.citsh.id.IdGenerator;
import com.citsh.keyvalue.entity.Prop;
import com.citsh.keyvalue.entity.Record;
import com.citsh.keyvalue.mdao.PropDao;
import com.citsh.keyvalue.mdao.RecordDao;
import com.citsh.keyvalue.service.KeyValueService;
import com.citsh.page.Page;
import com.citsh.query.PropertyFilter;
import com.citsh.util.MongodbQueryUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class KeyValueServiceImpl implements KeyValueService {
	private static Logger logger = LoggerFactory.getLogger(KeyValueServiceImpl.class);
	public static final int STATUS_DRAFT_PROCESS = 0;

	@Autowired
	private IdGenerator idGenerator;

	@Autowired
	private PropDao propService;

	@Autowired
	private RecordDao recordService;

	public Record findByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		Record record = null;
		record = (Record) this.recordService.findById(Long.valueOf(code));
		record = convertRecord(record);
		return record;
	}

	public Record findByRef(String ref) {
		if (StringUtils.isBlank(ref)) {
			return null;
		}
		Record record = null;
		try {
			record = (Record) this.recordService.findOne(Query.query(Criteria.where("ref").is(ref)));
			record = convertRecord(record);
		} catch (EmptyResultDataAccessException ex) {
			logger.info("cannot find record by ref : {}", ref);
			return null;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

		return record;
	}

	public Record findByBusinessKey(String businessKey) {
		if (StringUtils.isBlank(businessKey)) {
			return null;
		}
		Record record = null;
		try {
			record = (Record) this.recordService.findOne(Query.query(Criteria.where("BUSINESS_KEY").is(businessKey)));
			record = convertRecord(record);
		} catch (EmptyResultDataAccessException ex) {
			logger.info("cannot find record by businessKey : {}", businessKey);

			return null;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

		return record;
	}

	public void save(Record record) {
		if (record.getCode() == null)
			insert(record);
		else
			update(record);
	}

	public void removeByCode(String code) {
		Long id = Long.valueOf(code);
		this.propService.remove(Query.query(Criteria.where("recordId").is(id)));
		this.recordService.remove(Query.query(Criteria.where("_id").is(id)));
	}

	public void removeByBusinessKey(String businessKey) {
		Record record = (Record) this.recordService.findOne(Query.query(Criteria.where("businessKey").is(businessKey)));
		Long id = record.getId();
		this.propService.remove(Query.query(Criteria.where("recordId").is(id)));
		this.recordService.remove(Query.query(Criteria.where("_id").is(id)));
	}

	public List<Record> findByStatus(int status, String userId, String tenantId) {
		List<Record> records = new ArrayList<Record>();
		records = this.recordService.find(Query.query(Criteria.where("status").is(Integer.valueOf(status)).and("userId")
				.is(userId).and("tenantId").is(tenantId)));
		for (Record record : records) {
			record = convertRecord(record);
		}
		return records;
	}

	public Page pagedQuery(Page page, int status, String userId, String tenantId) {
		List<Record> records = new ArrayList<Record>();
		long totalCount = this.recordService.count(Query.query(Criteria.where("status").is(Integer.valueOf(status))
				.and("userId").is(userId).and("tenantId").is(tenantId)));
		records = this.recordService.find(Query.query(Criteria.where("status").is(Integer.valueOf(status)).and("userId")
				.is(userId).and("tenantId").is(tenantId)).skip((int) page.getStart()).limit(page.getPageSize()));
		for (Record record : records) {
			record = convertRecord(record);
		}
		page.setTotalCount(totalCount);
		page.setResult(records);

		return page;
	}

	public long findTotalCount(String category, String tenantId, String q) {
		List propertyFilters = new ArrayList();
		if (StringUtils.isNotBlank(q)) {
			for (String text : q.split("\\|")) {
				String name = text.split("=")[0];
				String value = text.split("=")[1];
				propertyFilters.add(new PropertyFilter("LIKES_" + name, value));
			}
		}

		return findTotalCount(category, tenantId, propertyFilters);
	}

	public List<Map<String, Object>> findResult(Page page, String category, String tenantId,
			Map<String, String> headers, String q) {
		List propertyFilters = new ArrayList();

		if (StringUtils.isNotBlank(q)) {
			for (String text : q.split("\\|")) {
				String name = text.split("=")[0];
				String value = text.split("=")[1];
				propertyFilters.add(new PropertyFilter("LIKES_" + name, value));
			}
		}
		return findResult(page, category, tenantId, headers, propertyFilters);
	}

	public Record copyRecord(Record original, List<String> fields) {
		Record record = new Record();

		record.setBusinessKey(original.getBusinessKey());
		record.setName(original.getName());
		record.setFormTemplateCode(original.getFormTemplateCode());

		record.setCategory(original.getCategory());
		record.setStatus(0);

		record.setRef(null);
		record.setCreateTime(new Date());
		record.setUserId(original.getUserId());
		record.setTenantId(original.getTenantId());

		List<Prop> propList = this.propService.find(Query.query(Criteria.where("recordId").is(original.getCode())));
		for (Prop prop : propList) {
			if (!fields.contains(prop.getCode())) {
				continue;
			}
			record.getProps().put(prop.getCode(), prop);
		}
		insert(record);

		return record;
	}

	public Record convertRecord(Record record) {
		if (record == null) {
			return null;
		}
		record.setCode(String.valueOf(record.getId()));
		List<Prop> list = this.propService.find(Query.query(Criteria.where("recordId").is(Long.valueOf(record.getCode()))));
		for (Prop prop : list) {
			record.getProps().put(prop.getCode(), prop);
		}
		return record;
	}

	public void insert(Record record) {
		Long id = record.getId();
		String originalRef = record.getRef();
		String ref = originalRef;
		if (originalRef == null) {
			ref = UUID.randomUUID().toString();
		}
		record.setRef(ref);
		if ((id == null) || ("0".equals(String.valueOf(id)))) {
			record.setId(this.idGenerator.generateId());
		}
		this.recordService.save(record);
		Record resultRecord = findByRef(ref);
		String code = resultRecord.getCode();

		if (originalRef == null) {
			this.recordService.update(Query.query(Criteria.where("_id").is(Long.valueOf(code))),
					Update.update("ref", null));
		}
		record.setCode(resultRecord.getCode());
		for (Prop prop : record.getProps().values()) {
			prop.setId(this.idGenerator.generateId());
			prop.setRecordId(Long.valueOf(record.getCode()).longValue());
			this.propService.insert(prop);
		}
	}

	public void update(Record record) {
		this.recordService.update(Query.query(Criteria.where("_id").is(Long.valueOf(record.getCode()))),
				Update.update("businessKey", record.getBusinessKey()).set("name", record.getName())
						.set("formTemplateCode", record.getFormTemplateCode()).set("category", record.getCategory())
						.set("status", Integer.valueOf(record.getStatus())).set("ref", record.getRef()));
		Record resultRecord = findByCode(record.getCode());
		for (Prop prop : record.getProps().values()) {
			if (resultRecord.getProps().containsKey(prop.getCode())) {
				this.propService.update(
						Query.query(Criteria.where("recordId").is(Long.valueOf(record.getCode())).and("code")
								.is(prop.getCode())),
						Update.update("type", Integer.valueOf(prop.getType())).set("value", prop.getValue()));
			} else {
				prop.setId(this.idGenerator.generateId());
				prop.setRecordId(Long.valueOf(record.getCode()).longValue());
				this.propService.save(prop);
			}
		}
	}

	public long findTotalCount(String category, String tenantId, List<PropertyFilter> propertyFilters) {
		List criteriaList = new ArrayList();
		Criteria criteria = new Criteria();
		if (propertyFilters.isEmpty()) {
			criteriaList.add(Criteria.where("category").is(category).and("tenantId").is(tenantId));
		} else {
			for (PropertyFilter propertyFilter : propertyFilters) {
				criteriaList.add(Criteria.where("props.code").is(propertyFilter.getPropertyName()).and("props.value")
						.regex(propertyFilter.getMatchValue().toString()));
			}
			criteriaList.add(Criteria.where("category").is(category).and("tenantId").is(tenantId));
		}
		Query query = new Query();
		query.addCriteria(criteria.andOperator((Criteria[]) criteriaList.toArray(new Criteria[criteriaList.size()])));
		long totalCount = this.recordService.count(query);
		return totalCount;
	}

	public List<Map<String, Object>> findResult(Page page, String category, String tenantId,
			Map<String, String> headers, List<PropertyFilter> propertyFilters) {
		logger.info("执行 findResult方法");
		String sqlPrefix = null;
		List params = new ArrayList();
		Map usedFieldMap = new HashMap();

		Query query = new Query();
		List criteriaList = new ArrayList();
		int index;
		if (propertyFilters.isEmpty()) {
			sqlPrefix = "select r.ID from KV_RECORD r";
		} else {
			sqlPrefix = "select r.ID from KV_RECORD r";

			index = 0;

			for (PropertyFilter propertyFilter : propertyFilters) {
				String propName = "p" + index;
				usedFieldMap.put(propertyFilter.getPropertyName(), propName);
				criteriaList.add(Criteria.where("props.code").is(propertyFilter.getPropertyName()).and("props.value")
						.regex(propertyFilter.getMatchValue().toString()));
			}
		}

		String sqlOrder = null;
		page.setOrderBy(tenantId);
		if (page.isOrderEnabled()) {
			String orderBy = page.getOrderBy();
			String order = page.getOrder();

			if (usedFieldMap.containsKey(orderBy)) {
				String propName = (String) usedFieldMap.get(orderBy);
				sqlOrder = " order by " + propName + ".VALUE " + order;
				query.with(new Sort(
						new Sort.Order[] { new Sort.Order(MongodbQueryUtil.order(order), propName + ".VALUE") }));
			} else {
				String propName = "p";
				sqlPrefix = sqlPrefix + " join KV_PROP " + propName + " on r.ID=" + propName + ".RECORD_ID and "
						+ propName + ".CODE='" + orderBy + "'";

				sqlOrder = " order by " + propName + ".VALUE " + order;
				criteriaList.add(Criteria.where(propName + ".code").is(orderBy));
			}
		}

		sqlPrefix = sqlPrefix + " where r.CATEGORY=? and r.TENANT_ID=?";
		params.add(category);
		params.add(tenantId);

		if (sqlOrder != null) {
			sqlPrefix = sqlPrefix + sqlOrder;
		}

		String sql = sqlPrefix + " limit " + page.getStart() + "," + page.getPageSize();
		logger.debug("sql : {}", sql);
		query.skip((int) page.getStart()).limit(page.getPageSize());
		List<Record> records = this.recordService.find(query);

		List list = new ArrayList();
/*		for (Record record : records) {
			map = new HashMap();
			list.add(map);

			Long recordId = record.getId();
			List<Prop> props = this.propService.find(Query.query(Criteria.where("recordId").is(recordId)));
			for (Prop prop : props)
				if (headers.containsKey(prop.getCode()))
					map.put(prop.getCode(), prop.getValue());
		}*/
		Map map;
		return list;
	}
}