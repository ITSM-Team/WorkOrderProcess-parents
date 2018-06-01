package com.citsh.humantask.service.impl;

import com.citsh.humantask.dao.TaskLogDao;
import com.citsh.humantask.entity.TaskLog;
import com.citsh.humantask.service.TaskLogService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class TaskLogServiceImpl
  implements TaskLogService
{

  @Autowired
  private TaskLogDao taskLogDao;

  public void removeAll(Set<TaskLog> taskLogs)
  {
    this.taskLogDao.delete(taskLogs);
  }
}