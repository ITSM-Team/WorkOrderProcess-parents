package com.citsh.rule.service.impl;

import com.citsh.activiti.service.InternalProcessService;
import com.citsh.rule.service.AssigneeRule;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssigneeRuleActivitiImpl
  implements AssigneeRule
{
  private static Logger logger = LoggerFactory.getLogger(AssigneeRuleActivitiImpl.class);

  @Autowired
  private InternalProcessService internalProcessService;

  public String process(String initiator) { return null;
  }

  public List<String> process(String value, String initiator)
  {
    if (StringUtils.isEmpty(value)) {
      logger.info("value is blank");
      return Collections.emptyList();
    }
    String[] array = value.split(":");
    if (array.length < 2) {
      logger.info("value is invalid : {}", value);
      return Collections.emptyList();
    }
    String processInstanceId = initiator;
    String activityId = array[1];
    String userId = this.internalProcessService.findAssigneeByActivityId(processInstanceId, activityId);
    return Collections.singletonList(userId);
  }
}