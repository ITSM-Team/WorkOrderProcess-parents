package com.citsh.humantask;

import java.util.List;

public abstract interface TaskDefinitionConnector
{
  public abstract String findTaskAssignStrategy(String paramString1, String paramString2);

  public abstract CounterSignDTO findCounterSign(String paramString1, String paramString2);

  public abstract FormDTO findForm(String paramString1, String paramString2);

  public abstract List<String> findOperations(String paramString1, String paramString2);

  public abstract List<TaskUserDTO> findTaskUsers(String paramString1, String paramString2);

  public abstract List<DeadlineDTO> findDeadlines(String paramString1, String paramString2);

  public abstract String findTaskConfUser(String paramString1, String paramString2);

  public abstract List<TaskNotificationDTO> findTaskNotifications(String paramString1, String paramString2, String paramString3);

  public abstract void create(TaskDefinitionDTO paramTaskDefinitionDTO);

  public abstract void saveAssignStrategy(String paramString1, String paramString2, String paramString3);

  public abstract void saveCounterSign(String paramString1, String paramString2, CounterSignDTO paramCounterSignDTO);

  public abstract void saveForm(String paramString1, String paramString2, FormDTO paramFormDTO);

  public abstract void addOperation(String paramString1, String paramString2, String paramString3);

  public abstract void removeOperation(String paramString1, String paramString2, String paramString3);

  public abstract void addTaskUser(String paramString1, String paramString2, TaskUserDTO paramTaskUserDTO);

  public abstract void removeTaskUser(String paramString1, String paramString2, TaskUserDTO paramTaskUserDTO);

  public abstract void updateTaskUser(String paramString1, String paramString2, TaskUserDTO paramTaskUserDTO, String paramString3);

  public abstract void addTaskNotification(String paramString1, String paramString2, TaskNotificationDTO paramTaskNotificationDTO);

  public abstract void removeTaskNotification(String paramString1, String paramString2, TaskNotificationDTO paramTaskNotificationDTO);

  public abstract void addDeadline(String paramString1, String paramString2, DeadlineDTO paramDeadlineDTO);

  public abstract void removeDeadline(String paramString1, String paramString2, DeadlineDTO paramDeadlineDTO);
}
