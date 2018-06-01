package com.citsh.listener.service.impl;

import com.citsh.base.service.TaskDefinitionService;
import com.citsh.humantask.entity.TaskInfo;
import com.citsh.listener.service.ListenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ListenerServiceTaskConfUserImpl
  implements ListenerService
{

  @Autowired
  private TaskDefinitionService taskDefinitionService;

  public void onCreate(TaskInfo taskInfo)
  {
    if ("copy".equals(taskInfo.getCatalog())) {
      return;
    }
    String taskDefinitionKey = taskInfo.getCode();
    String businessKey = taskInfo.getBusinessKey();
    String assignee = this.taskDefinitionService.findTaskConfUser(taskDefinitionKey, businessKey);
    if (assignee != null)
      taskInfo.setAssignee(assignee);
  }

  public void onComplete(TaskInfo taskInfo)
  {
  }
}