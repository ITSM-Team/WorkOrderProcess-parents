package com.citsh.query;

import java.util.List;

import org.springframework.util.Assert;

public class QueuePage {
	 /**
     * 按属性条件列表创建sql,辅助函数.
     * 
     * @param filters
     *            List
     * @return StringBuffer
     */
    public static StringBuffer buildCriterion(List<PropertyFilter> filters) {
        StringBuffer sb=new StringBuffer();
        //计数
        int i=0;
        for (PropertyFilter filter : filters) {
            // 只有一个属性需要比较的情况.
            if (!filter.hasMultiProperties()) {
                String criterion = buildCriterion(filter.getPropertyName(),
                        filter.getMatchValue(), filter.getMatchType());
                if(i<filters.size()-1){
                	criterion+=" and ";
                }
                sb.append(criterion);	                
            } 
/*	            else {
                // 包含多个属性需要比较的情况,进行or处理.
                for (String param : filter.getPropertyNames()) {
                    String criterion = buildCriterion(param,
                            filter.getMatchValue(), filter.getMatchType());
                }

                criterionList.add(disjunction);
            }*/
            i++;
        }
        return sb;
    }
	
	 public static String buildCriterion(String propertyName,
	            Object propertyValue, MatchType matchType) {
	        Assert.hasText(propertyName, "propertyName不能为空");
	        String string=null;
	        // 根据MatchType构造criterion
	        switch (matchType) {
	        case EQ:
	            string=" "+propertyName+"='"+propertyValue+"'";
	            break;

	        case NOT:
	            string=" "+propertyName+"!='"+propertyValue+"'";
	            break;

	        case LIKE:
	            string=" "+propertyName+" like '%"+propertyValue+"%' ";
	            break;

	        case LE:
	        	string=" "+propertyName+"<="+propertyValue;
	            break;

	        case LT:
	        	string=" "+propertyName+"<"+propertyValue;
	            break;

	        case GE:
	          	string=" "+propertyName+">="+propertyValue;

	            break;

	        case GT:
	        	string=" "+propertyName+">"+propertyValue;

	            break;

	        case IN:
	        	string=" "+propertyName+" in ("+propertyValue+") ";
	            break;

	        case INL:
	            string=" "+propertyName+" is null ";
	            break;

	        case NNL:
	            string=" "+propertyName+" is not null ";
	            break;

	        default:
	        	 string=" "+propertyName+"='"+propertyValue+"'";
	            break;
	        }

	        return string;
	    }
}
