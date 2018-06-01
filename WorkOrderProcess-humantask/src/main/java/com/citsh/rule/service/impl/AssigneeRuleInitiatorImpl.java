package com.citsh.rule.service.impl;

import com.citsh.rule.service.AssigneeRule;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AssigneeRuleInitiatorImpl
  implements AssigneeRule
{
  public String process(String initiator)
  {
    return initiator;
  }

  public List<String> process(String value, String initiator)
  {
    return Collections.singletonList(initiator);
  }
}