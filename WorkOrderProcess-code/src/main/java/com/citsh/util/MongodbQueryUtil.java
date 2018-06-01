package com.citsh.util;

import com.citsh.query.MatchType;
import com.citsh.query.PropertyFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.util.Assert;

public class MongodbQueryUtil {
	public static Criteria buildCriterion(String propertyName, Object propertyValue, MatchType matchType) {
		Assert.hasText(propertyName, "propertyName不能为空");

		Criteria criterion = new Criteria();

		switch (matchType) {
		case EQ:
			criterion = Criteria.where(propertyName).is(propertyValue);
			break;
		case NOT:
			criterion = Criteria.where(propertyName).ne(propertyName);
			break;
		case LIKE:
			criterion = Criteria.where(propertyName).regex(propertyValue.toString());
			break;
		case LE:
			criterion = Criteria.where(propertyName).lte(propertyValue);
			break;
		case LT:
			criterion = Criteria.where(propertyName).lt(propertyValue);
			break;
		case GE:
			criterion = Criteria.where(propertyName).gte(propertyValue);
			break;
		case GT:
			criterion = Criteria.where(propertyName).gt(propertyValue);
			break;
		case IN:
			criterion = Criteria.where(propertyName).in(new Object[] { propertyValue });
			break;
		case INL:
			criterion = Criteria.where(propertyName).not();
			break;
		case NNL:
			criterion = Criteria.where(propertyName).ne("").ne(null);
			break;
		default:
			criterion = Criteria.where(propertyName).is(propertyValue);
		}

		return criterion;
	}

	/**
	 * 按属性条件列表创建Criterion数组,辅助函数.
	 * 
	 * @param filters
	 *            List
	 * @return Criterion[]
	 */
	public static Map<String, List<Criteria>> buildCriterion(List<PropertyFilter> filters) {
		Map<String, List<Criteria>> criterionMap = new HashMap<String, List<Criteria>>();
		List<Criteria> andlist = new ArrayList<Criteria>();
		List<Criteria> orlist = new ArrayList<Criteria>();
		for (PropertyFilter filter : filters) {
			// 只有一个属性需要比较的情况.
			if (!filter.hasMultiProperties()) {
				Criteria criterion = buildCriterion(filter.getPropertyName(), filter.getMatchValue(),
						filter.getMatchType());
				andlist.add(criterion);
			} else {
				// 包含多个属性需要比较的情况,进行or处理.
				for (String param : filter.getPropertyNames()) {
					Criteria criterion = buildCriterion(param, filter.getMatchValue(), filter.getMatchType());
					orlist.add(criterion);
				}
			}
		}
		criterionMap.put("and", andlist);
		criterionMap.put("or", orlist);
		return criterionMap;
	}

	public static Direction order(String order) {
		Direction direction = null;

		switch (order) {
		case "asc":
			direction = Direction.ASC;
		case "desc":
			direction = Direction.DESC;
		default:
			direction = Direction.ASC;
		}
		return direction;

	}
}