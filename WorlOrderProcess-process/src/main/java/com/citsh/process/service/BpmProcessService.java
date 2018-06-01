package com.citsh.process.service;

import java.util.List;

import com.citsh.page.Page;
import com.citsh.process.entity.BpmProcess;
import com.citsh.query.PropertyFilter;

public interface BpmProcessService{
	/**
     * 分页查询函数，根据entityClass和page参数进行查询.
     * 
     * @param <T>
     *            实体类型
     * @param entityClass
     *            实体类型
     * @param page
     *            分页里包含的各种参数
     * @param criterions
     *            条件
     * @return 含总记录数和当前页数据的Page对象.
     */
    public Page pagedQuery(Page page,List<PropertyFilter> propertyFilters);
    
    /**
     * 查询所有
     * */
    public List<BpmProcess> findAll();
    
    /**
     * 按ID查询
     * */
    public BpmProcess findById(Long id);
    
    BpmProcess saveEntity(BpmProcess bpmProcess);
    
    public void deleteByIds(Object[] entityIds);
    
}