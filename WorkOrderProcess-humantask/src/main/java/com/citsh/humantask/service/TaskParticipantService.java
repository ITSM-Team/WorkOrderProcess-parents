package com.citsh.humantask.service;

import com.citsh.humantask.entity.TaskParticipant;
import java.util.List;

public abstract interface TaskParticipantService
{
  public abstract void removeAll(List<TaskParticipant> paramList);

  public abstract void save(TaskParticipant paramTaskParticipant);
}