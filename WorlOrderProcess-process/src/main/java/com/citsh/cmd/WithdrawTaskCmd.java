package com.citsh.cmd;

import com.citsh.graph.conf.ActivitiHistoryGraphBuilder;
import com.citsh.graph.entity.Edge;
import com.citsh.graph.entity.Graph;
import com.citsh.graph.entity.Node;
import com.citsh.humantask.HumanTaskBuilder;
import com.citsh.humantask.HumanTaskConnector;
import com.citsh.humantask.HumanTaskDTO;
import com.citsh.spring.ApplicationContextHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.HistoricActivityInstanceQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.cmd.GetDeploymentProcessDefinitionCmd;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntityManager;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class WithdrawTaskCmd
  implements Command<Integer>
{
  private static Logger logger = LoggerFactory.getLogger(WithdrawTaskCmd.class);
  private String historyTaskId;

  public WithdrawTaskCmd(String historyTaskId)
  {
    this.historyTaskId = historyTaskId;
  }

  public Integer execute(CommandContext commandContext)
  {
    HistoricTaskInstanceEntity historicTaskInstanceEntity = Context.getCommandContext()
      .getHistoricTaskInstanceEntityManager().findHistoricTaskInstanceById(this.historyTaskId);

    HistoricActivityInstanceEntity historicActivityInstanceEntity = getHistoricActivityInstanceEntity(this.historyTaskId);

    Graph graph = new ActivitiHistoryGraphBuilder(historicTaskInstanceEntity.getProcessInstanceId()).build();

    Node node = graph.findById(historicActivityInstanceEntity.getId());

    if (!checkCouldWithdraw(node)) {
      logger.info("cannot withdraw {}", this.historyTaskId);

      return Integer.valueOf(2);
    }

    deleteActiveTasks(historicTaskInstanceEntity.getProcessInstanceId());

    List historyNodeIds = new ArrayList();
    collectNodes(node, historyNodeIds);
    deleteHistoryActivities(historyNodeIds);

    processHistoryTask(historicTaskInstanceEntity, historicActivityInstanceEntity);

    logger.info("activiti is withdraw {}", historicTaskInstanceEntity.getName());

    return Integer.valueOf(0);
  }

  public HistoricActivityInstanceEntity getHistoricActivityInstanceEntity(String historyTaskId) {
    logger.info("historyTaskId : {}", historyTaskId);

    JdbcTemplate jdbcTemplate = (JdbcTemplate)ApplicationContextHelper.getBean(JdbcTemplate.class);

    String historicActivityInstanceId = (String)jdbcTemplate
      .queryForObject("select id_ from ACT_HI_ACTINST where task_id_=?", String.class, new Object[] { historyTaskId });

    logger.info("historicActivityInstanceId : {}", historicActivityInstanceId);

    HistoricActivityInstanceQueryImpl historicActivityInstanceQueryImpl = new HistoricActivityInstanceQueryImpl();
    historicActivityInstanceQueryImpl.activityInstanceId(historicActivityInstanceId);

    HistoricActivityInstanceEntity historicActivityInstanceEntity = (HistoricActivityInstanceEntity)Context.getCommandContext().getHistoricActivityInstanceEntityManager()
      .findHistoricActivityInstancesByQueryCriteria(historicActivityInstanceQueryImpl, new Page(0, 1))
      .get(0);

    return historicActivityInstanceEntity;
  }

  public boolean checkCouldWithdraw(Node node)
  {
    for (Edge edge : node.getOutgoingEdges()) {
      Node dest = edge.getDest();
      String type = dest.getType();

      if ("userTask".equals(type)) {
        if (!dest.isActive()) {
          boolean isSkip = isSkipActivity(dest.getId());

          if (isSkip) {
            return checkCouldWithdraw(dest);
          }
          logger.info("cannot withdraw, " + type + "(" + dest.getName() + ") is complete.");

          return false;
        }
      } else {
        if (type.endsWith("Gateway")) {
          return checkCouldWithdraw(dest);
        }
        logger.info("cannot withdraw, " + type + "(" + dest.getName() + ") is complete.");

        return false;
      }
    }

    return true;
  }

  public void deleteActiveTasks(String processInstanceId)
  {
    HumanTaskConnector humanTaskConnector = (HumanTaskConnector)ApplicationContextHelper.getBean(HumanTaskConnector.class);
    humanTaskConnector.removeHumanTaskByProcessInstanceId(processInstanceId);

    Context.getCommandContext().getTaskEntityManager().deleteTasksByProcessInstanceId(processInstanceId, null, true);
  }

  public void collectNodes(Node node, List<String> historyNodeIds)
  {
    logger.info("node : {}, {}, {}", new Object[] { node.getId(), node.getType(), node.getName() });

    for (Edge edge : node.getOutgoingEdges()) {
      logger.info("edge : {}", edge.getName());

      Node dest = edge.getDest();
      historyNodeIds.add(dest.getId());
      collectNodes(dest, historyNodeIds);
    }
  }

  public void deleteHistoryActivities(List<String> historyNodeIds)
  {
    JdbcTemplate jdbcTemplate = (JdbcTemplate)ApplicationContextHelper.getBean(JdbcTemplate.class);
    logger.info("historyNodeIds : {}", historyNodeIds);

    for (String id : historyNodeIds) {
      String taskId = (String)jdbcTemplate.queryForObject("select task_id_ from ACT_HI_ACTINST where id_=?", String.class, new Object[] { id });

      if (taskId != null) {
        Context.getCommandContext().getHistoricTaskInstanceEntityManager()
          .deleteHistoricTaskInstanceById(taskId);
      }

      jdbcTemplate.update("delete from ACT_HI_ACTINST where id_=?", new Object[] { id });
    }
  }

  public void processHistoryTask(HistoricTaskInstanceEntity historicTaskInstanceEntity, HistoricActivityInstanceEntity historicActivityInstanceEntity)
  {
    historicTaskInstanceEntity.setEndTime(null);
    historicTaskInstanceEntity.setDurationInMillis(null);
    historicActivityInstanceEntity.setEndTime(null);
    historicActivityInstanceEntity.setDurationInMillis(null);

    TaskEntity task = TaskEntity.create(new Date());
    task.setProcessDefinitionId(historicTaskInstanceEntity.getProcessDefinitionId());
    task.setId(historicTaskInstanceEntity.getId());
    task.setAssigneeWithoutCascade(historicTaskInstanceEntity.getAssignee());
    task.setParentTaskIdWithoutCascade(historicTaskInstanceEntity.getParentTaskId());
    task.setNameWithoutCascade(historicTaskInstanceEntity.getName());
    task.setTaskDefinitionKey(historicTaskInstanceEntity.getTaskDefinitionKey());
    task.setExecutionId(historicTaskInstanceEntity.getExecutionId());
    task.setPriority(historicTaskInstanceEntity.getPriority());
    task.setProcessInstanceId(historicTaskInstanceEntity.getProcessInstanceId());
    task.setDescriptionWithoutCascade(historicTaskInstanceEntity.getDescription());
    task.setTenantId(historicTaskInstanceEntity.getTenantId());

    Context.getCommandContext().getTaskEntityManager().insert(task);
    try
    {
      HumanTaskConnector humanTaskConnector = (HumanTaskConnector)ApplicationContextHelper.getBean(HumanTaskConnector.class);

      humanTaskConnector.removeHumanTaskByTaskId(historicTaskInstanceEntity.getId());
      createHumanTask(task, historicTaskInstanceEntity);
    } catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
    }

    ExecutionEntity executionEntity = Context.getCommandContext().getExecutionEntityManager()
      .findExecutionById(historicTaskInstanceEntity
      .getExecutionId());
    executionEntity.setActivity(getActivity(historicActivityInstanceEntity));
  }

  public ActivityImpl getActivity(HistoricActivityInstanceEntity historicActivityInstanceEntity)
  {
    ProcessDefinitionEntity processDefinitionEntity = new GetDeploymentProcessDefinitionCmd(historicActivityInstanceEntity
      .getProcessDefinitionId()).execute(Context.getCommandContext());

    return processDefinitionEntity.findActivity(historicActivityInstanceEntity.getActivityId());
  }

  public boolean isSkipActivity(String historyActivityId) {
    JdbcTemplate jdbcTemplate = (JdbcTemplate)ApplicationContextHelper.getBean(JdbcTemplate.class);
    String historyTaskId = (String)jdbcTemplate.queryForObject("select task_id_ from ACT_HI_ACTINST where id_=?", String.class, new Object[] { historyActivityId });

    HistoricTaskInstanceEntity historicTaskInstanceEntity = Context.getCommandContext()
      .getHistoricTaskInstanceEntityManager().findHistoricTaskInstanceById(historyTaskId);
    String deleteReason = historicTaskInstanceEntity.getDeleteReason();

    return "跳过".equals(deleteReason);
  }

  public HumanTaskDTO createHumanTask(DelegateTask delegateTask, HistoricTaskInstanceEntity historicTaskInstanceEntity) throws Exception
  {
    HumanTaskConnector humanTaskConnector = (HumanTaskConnector)ApplicationContextHelper.getBean(HumanTaskConnector.class);
    HumanTaskDTO humanTaskDto = new HumanTaskBuilder().setDelegateTask(delegateTask).build();

    if ("发起流程".equals(historicTaskInstanceEntity.getDeleteReason())) {
      humanTaskDto.setCatalog("start");
    }

    HistoricProcessInstance historicProcessInstance = Context.getCommandContext()
      .getHistoricProcessInstanceEntityManager()
      .findHistoricProcessInstance(delegateTask
      .getProcessInstanceId());
    humanTaskDto.setProcessStarter(historicProcessInstance.getStartUserId());
    humanTaskDto = humanTaskConnector.saveHumanTask(humanTaskDto);

    return humanTaskDto;
  }
}