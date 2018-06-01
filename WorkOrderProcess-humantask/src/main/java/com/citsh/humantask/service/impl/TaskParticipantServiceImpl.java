package com.citsh.humantask.service.impl;

import com.citsh.humantask.dao.TaskParticipantDao;
import com.citsh.humantask.entity.TaskParticipant;
import com.citsh.humantask.service.TaskParticipantService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class TaskParticipantServiceImpl
  implements TaskParticipantService
{

  @Autowired
  private TaskParticipantDao taskParticipantDao;

  public void removeAll(List<TaskParticipant> taskParticipants)
  {
    this.taskParticipantDao.delete(taskParticipants);
  }

  public void save(TaskParticipant taskParticipant)
  {
    this.taskParticipantDao.save(taskParticipant);
  }
}