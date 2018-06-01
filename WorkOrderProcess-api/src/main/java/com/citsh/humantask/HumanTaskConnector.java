package com.citsh.humantask;

import com.citsh.from.FormDTO;
import com.citsh.page.Page;
import java.util.List;
import java.util.Map;

public abstract interface HumanTaskConnector
{
  public abstract HumanTaskDTO createHumanTask();

  public abstract void removeHumanTask(String paramString);

  public abstract void removeHumanTaskByTaskId(String paramString);

  public abstract void removeHumanTaskByProcessInstanceId(String paramString);

  public abstract HumanTaskDTO saveHumanTask(HumanTaskDTO paramHumanTaskDTO);

  public abstract HumanTaskDTO saveHumanTask(HumanTaskDTO paramHumanTaskDTO, boolean paramBoolean);

  public abstract HumanTaskDTO saveHumanTaskAndProcess(HumanTaskDTO paramHumanTaskDTO);

  public abstract void completeTask(String paramString1, String paramString2, String paramString3, String paramString4, Map<String, Object> paramMap);

  public abstract void claimTask(String paramString1, String paramString2);

  public abstract void releaseTask(String paramString1, String paramString2);

  public abstract void transfer(String paramString1, String paramString2, String paramString3);

  public abstract void cancel(String paramString1, String paramString2, String paramString3);

  public abstract void rollbackActivity(String paramString1, String paramString2, String paramString3);

  public abstract void rollbackActivityLast(String paramString1, String paramString2, String paramString3);

  public abstract void rollbackActivityAssignee(String paramString1, String paramString2, String paramString3, String paramString4);

  public abstract void rollbackPrevious(String paramString1, String paramString2);

  public abstract void rollbackPreviousLast(String paramString1, String paramString2);

  public abstract void rollbackPreviousAssignee(String paramString1, String paramString2, String paramString3);

  public abstract void rollbackStart(String paramString1, String paramString2);

  public abstract void rollbackInitiator(String paramString1, String paramString2);

  public abstract void withdraw(String paramString1, String paramString2);

  public abstract void delegateTask(String paramString1, String paramString2, String paramString3);

  public abstract void delegateTaskCreate(String paramString1, String paramString2, String paramString3);

  public abstract void communicate(String paramString1, String paramString2, String paramString3);

  public abstract void callback(String paramString1, String paramString2, String paramString3);

  public abstract void skip(String paramString1, String paramString2, String paramString3);

  public abstract void saveParticipant(ParticipantDTO paramParticipantDTO);

  public abstract HumanTaskDTO findHumanTaskByTaskId(String paramString);

  public abstract List<HumanTaskDTO> findHumanTasksByProcessInstanceId(String paramString);

  public abstract HumanTaskDTO findHumanTask(String paramString);

  public abstract List<HumanTaskDTO> findSubTasks(String paramString);

  public abstract FormDTO findTaskForm(String paramString);

  public abstract List<HumanTaskDefinition> findHumanTaskDefinitions(String paramString);

  public abstract void configTaskDefinitions(String paramString, List<String> paramList1, List<String> paramList2);

  public abstract Page findPersonalTasks(String paramString1, String paramString2, int paramInt1, int paramInt2);

  public abstract Page findFinishedTasks(String paramString1, String paramString2, int paramInt1, int paramInt2);

  public abstract Page findGroupTasks(String paramString1, String paramString2, int paramInt1, int paramInt2);

  public abstract Page findDelegateTasks(String paramString1, String paramString2, int paramInt1, int paramInt2);
}