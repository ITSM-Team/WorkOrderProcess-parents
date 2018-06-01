package com.citsh.button.dao;

import com.citsh.button.entity.ButtonCategory;
import com.citsh.dao.jpa.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface ButtonCategoryDao extends BaseDao<ButtonCategory, Long>
{
}