
package com.citsh.config.service;
import com.citsh.config.entity.BpmConfNode;
import java.util.List;

public  interface BpmConfNodeService
{
  public  List<BpmConfNode> findByBpmConfBase(Long id);

  public  BpmConfNode find(Long paramLong);
  
  public BpmConfNode findByHSQLOne(String condition,Object...objects);
  
  public void save(BpmConfNode bpmConfNode);
}

