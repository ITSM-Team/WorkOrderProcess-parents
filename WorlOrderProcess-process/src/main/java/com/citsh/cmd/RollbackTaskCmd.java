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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.HistoricActivityInstanceQueryImpl;
import org.activiti.engine.impl.HistoricTaskInstanceQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.cmd.GetDeploymentProcessDefinitionCmd;
import org.activiti.engine.impl.cmd.GetStartFormCmd;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.history.HistoryManager;
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
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class RollbackTaskCmd implements Command<Object> {
	private static Logger logger = LoggerFactory.getLogger(RollbackTaskCmd.class);
	private String taskId;
	private String activityId;
	private String userId;
	private boolean useLastAssignee = false;

	private Set<String> multiInstanceExecutionIds = new HashSet();

	public RollbackTaskCmd(String taskId, String activityId) {
		this.taskId = taskId;
		this.activityId = activityId;
		this.useLastAssignee = true;
	}

	public RollbackTaskCmd(String taskId, String activityId, String userId) {
		this.taskId = taskId;
		this.activityId = activityId;
		this.userId = userId;
	}

	public Integer execute(CommandContext commandContext) {
		TaskEntity taskEntity = findTask(commandContext);

		ActivityImpl targetActivity = findTargetActivity(commandContext, taskEntity);
		logger.info("rollback to {}", this.activityId);
		logger.info("{}", targetActivity.getProperties());

		String type = (String) targetActivity.getProperty("type");

		if ("userTask".equals(type)) {
			logger.info("rollback to userTask");
			rollbackUserTask(commandContext);
		} else if ("startEvent".equals(type)) {
			logger.info("rollback to startEvent");
			rollbackStartEvent(commandContext);
		} else {
			throw new IllegalStateException("cannot rollback " + type);
		}

		return Integer.valueOf(0);
	}

	public Integer rollbackUserTask(CommandContext commandContext) {
		TaskEntity taskEntity = findTask(commandContext);

		ActivityImpl targetActivity = findTargetActivity(commandContext, taskEntity);

		HistoricActivityInstanceEntity historicActivityInstanceEntity = findTargetHistoricActivity(commandContext,
				taskEntity, targetActivity);

		HistoricTaskInstanceEntity historicTaskInstanceEntity = findTargetHistoricTask(commandContext, taskEntity,
				targetActivity);

		logger.info("historic activity instance is : {}", historicActivityInstanceEntity.getId());

		Graph graph = new ActivitiHistoryGraphBuilder(historicTaskInstanceEntity.getProcessInstanceId()).build();

		Node node = graph.findById(historicActivityInstanceEntity.getId());

		if (!checkCouldRollback(node)) {
			logger.info("cannot rollback {}", this.taskId);

			return Integer.valueOf(2);
		}

		if (isSameBranch(historicTaskInstanceEntity)) {
			TaskEntity targetTaskEntity = Context.getCommandContext().getTaskEntityManager().findTaskById(this.taskId);
			deleteActiveTask(targetTaskEntity);
		} else {
			deleteActiveTasks(historicTaskInstanceEntity.getProcessInstanceId());

			List historyNodeIds = new ArrayList();
			collectNodes(node, historyNodeIds);
			deleteHistoryActivities(historyNodeIds);
		}

		processMultiInstance();

		processHistoryTask(commandContext, taskEntity, historicTaskInstanceEntity, historicActivityInstanceEntity);

		logger.info("activiti is rollback {}", historicTaskInstanceEntity.getName());

		return Integer.valueOf(0);
	}

	public Integer rollbackStartEvent(CommandContext commandContext) {
		TaskEntity taskEntity = findTask(commandContext);

		ActivityImpl targetActivity = findTargetActivity(commandContext, taskEntity);

		if (taskEntity.getExecutionId().equals(taskEntity.getProcessInstanceId())) {
			TaskEntity targetTaskEntity = Context.getCommandContext().getTaskEntityManager().findTaskById(this.taskId);
			deleteActiveTask(targetTaskEntity);
		} else {
			deleteActiveTasks(taskEntity.getProcessInstanceId());
		}

		ExecutionEntity executionEntity = Context.getCommandContext().getExecutionEntityManager()
				.findExecutionById(taskEntity.getExecutionId());
		executionEntity.setActivity(targetActivity);

		Context.getCommandContext().getHistoryManager().recordActivityStart(executionEntity);

		String processDefinitionId = taskEntity.getProcessDefinitionId();
		GetStartFormCmd getStartFormCmd = new GetStartFormCmd(processDefinitionId);
		StartFormData startFormData = getStartFormCmd.execute(commandContext);
		try {
			logger.info("{}", targetActivity.getProperties());

			HumanTaskConnector humanTaskConnector = (HumanTaskConnector) ApplicationContextHelper
					.getBean(HumanTaskConnector.class);
			HumanTaskDTO humanTaskDto = humanTaskConnector.createHumanTask();
			humanTaskDto.setName((String) targetActivity.getProperty("name"));
			humanTaskDto.setDescription((String) targetActivity.getProperty("description"));
			humanTaskDto.setCode(targetActivity.getId());
			humanTaskDto.setAssignee(this.userId);
			humanTaskDto.setOwner(null);
			humanTaskDto.setDelegateStatus("none");
			humanTaskDto.setPriority(50);
			humanTaskDto.setCreateTime(new Date());
			humanTaskDto.setDuration(null);
			humanTaskDto.setSuspendStatus("none");
			humanTaskDto.setCategory("startEvent");
			humanTaskDto.setForm(startFormData.getFormKey());
			humanTaskDto.setTaskId(null);
			humanTaskDto.setExecutionId(taskEntity.getExecutionId());
			humanTaskDto.setProcessInstanceId(taskEntity.getProcessInstanceId());
			humanTaskDto.setProcessDefinitionId(taskEntity.getProcessDefinitionId());
			humanTaskDto.setTenantId(taskEntity.getTenantId());
			humanTaskDto.setStatus("active");
			humanTaskDto = humanTaskConnector.saveHumanTask(humanTaskDto);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

		processMultiInstance();

		return Integer.valueOf(0);
	}

	public TaskEntity findTask(CommandContext commandContext) {
		TaskEntity taskEntity = commandContext.getTaskEntityManager().findTaskById(this.taskId);

		return taskEntity;
	}

	public ActivityImpl findTargetActivity(CommandContext commandContext, TaskEntity taskEntity) {
		if (this.activityId == null) {
			String historyTaskId = findNearestUserTask(commandContext);

			HistoricTaskInstanceEntity historicTaskInstanceEntity = commandContext
					.getHistoricTaskInstanceEntityManager().findHistoricTaskInstanceById(historyTaskId);
			this.activityId = historicTaskInstanceEntity.getTaskDefinitionKey();
		}

		String processDefinitionId = taskEntity.getProcessDefinitionId();

		ProcessDefinitionEntity processDefinitionEntity = new GetDeploymentProcessDefinitionCmd(processDefinitionId)
				.execute(commandContext);

		return processDefinitionEntity.findActivity(this.activityId);
	}

	public HistoricActivityInstanceEntity findTargetHistoricActivity(CommandContext commandContext,
			TaskEntity taskEntity, ActivityImpl activityImpl) {
		HistoricActivityInstanceQueryImpl historicActivityInstanceQueryImpl = new HistoricActivityInstanceQueryImpl();
		historicActivityInstanceQueryImpl.activityId(activityImpl.getId());
		historicActivityInstanceQueryImpl.processInstanceId(taskEntity.getProcessInstanceId());
		historicActivityInstanceQueryImpl.orderByHistoricActivityInstanceEndTime().desc();

		HistoricActivityInstanceEntity historicActivityInstanceEntity = (HistoricActivityInstanceEntity) commandContext
				.getHistoricActivityInstanceEntityManager()
				.findHistoricActivityInstancesByQueryCriteria(historicActivityInstanceQueryImpl, new Page(0, 1)).get(0);

		return historicActivityInstanceEntity;
	}

	public HistoricTaskInstanceEntity findTargetHistoricTask(CommandContext commandContext, TaskEntity taskEntity,
			ActivityImpl activityImpl) {
		HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
		historicTaskInstanceQueryImpl.taskDefinitionKey(activityImpl.getId());
		historicTaskInstanceQueryImpl.processInstanceId(taskEntity.getProcessInstanceId());
		historicTaskInstanceQueryImpl.setFirstResult(0);
		historicTaskInstanceQueryImpl.setMaxResults(1);
		historicTaskInstanceQueryImpl.orderByTaskCreateTime().desc();

		HistoricTaskInstanceEntity historicTaskInstanceEntity = (HistoricTaskInstanceEntity) commandContext
				.getHistoricTaskInstanceEntityManager()
				.findHistoricTaskInstancesByQueryCriteria(historicTaskInstanceQueryImpl).get(0);

		return historicTaskInstanceEntity;
	}

	public boolean isSameBranch(HistoricTaskInstanceEntity historicTaskInstanceEntity) {
		TaskEntity taskEntity = Context.getCommandContext().getTaskEntityManager().findTaskById(this.taskId);

		return taskEntity.getExecutionId().equals(historicTaskInstanceEntity.getExecutionId());
	}

	public String findNearestUserTask(CommandContext commandContext) {
		TaskEntity taskEntity = commandContext.getTaskEntityManager().findTaskById(this.taskId);

		if (taskEntity == null) {
			logger.debug("cannot find task : {}", this.taskId);

			return null;
		}

		Graph graph = new ActivitiHistoryGraphBuilder(taskEntity.getProcessInstanceId()).build();
		JdbcTemplate jdbcTemplate = (JdbcTemplate) ApplicationContextHelper.getBean(JdbcTemplate.class);

		String historicActivityInstanceId = (String) jdbcTemplate.queryForObject(
				"SELECT ID_ FROM ACT_HI_ACTINST WHERE TASK_ID_=?", String.class, new Object[] { this.taskId });

		Node node = graph.findById(historicActivityInstanceId);

		String previousHistoricActivityInstanceId = findIncomingNode(graph, node);

		if (previousHistoricActivityInstanceId == null) {
			logger.debug("cannot find previous historic activity instance : {}", taskEntity);

			return null;
		}

		return (String) jdbcTemplate.queryForObject("SELECT TASK_ID_ FROM ACT_HI_ACTINST WHERE ID_=?", String.class,
				new Object[] { previousHistoricActivityInstanceId });
	}

	public String findIncomingNode(Graph graph, Node node) {
		for (Edge edge : graph.getEdges()) {
			Node src = edge.getSrc();
			Node dest = edge.getDest();
			String srcType = src.getType();

			if (!dest.getId().equals(node.getId())) {
				continue;
			}
			if ("userTask".equals(srcType)) {
				boolean isSkip = isSkipActivity(src.getId());

				if (isSkip) {
					return findIncomingNode(graph, src);
				}
				return src.getId();
			}
			if (srcType.endsWith("Gateway")) {
				return findIncomingNode(graph, src);
			}
			logger.info("cannot rollback, previous node is not userTask : " + src.getId() + " " + srcType + "("
					+ src.getName() + ")");

			return null;
		}

		logger.info("cannot rollback, this : " + node.getId() + " " + node.getType() + "(" + node.getName() + ")");

		return null;
	}

	public HistoricActivityInstanceEntity getHistoricActivityInstanceEntity(String historyTaskId) {
		logger.info("historyTaskId : {}", historyTaskId);

		JdbcTemplate jdbcTemplate = (JdbcTemplate) ApplicationContextHelper.getBean(JdbcTemplate.class);

		String historicActivityInstanceId = (String) jdbcTemplate.queryForObject(
				"SELECT ID_ FROM ACT_HI_ACTINST WHERE TASK_ID_=?", String.class, new Object[] { historyTaskId });

		logger.info("historicActivityInstanceId : {}", historicActivityInstanceId);

		HistoricActivityInstanceQueryImpl historicActivityInstanceQueryImpl = new HistoricActivityInstanceQueryImpl();
		historicActivityInstanceQueryImpl.activityInstanceId(historicActivityInstanceId);

		HistoricActivityInstanceEntity historicActivityInstanceEntity = (HistoricActivityInstanceEntity) Context
				.getCommandContext().getHistoricActivityInstanceEntityManager()
				.findHistoricActivityInstancesByQueryCriteria(historicActivityInstanceQueryImpl, new Page(0, 1)).get(0);

		return historicActivityInstanceEntity;
	}

	public boolean checkCouldRollback(Node node) {
		for (Edge edge : node.getOutgoingEdges()) {
			Node dest = edge.getDest();
			String type = dest.getType();

			if ("userTask".equals(type)) {
				if (!dest.isActive()) {
					boolean isSkip = isSkipActivity(dest.getId());

					if (isSkip) {
						return checkCouldRollback(dest);
					}

					return true;
				}
			} else {
				if (type.endsWith("Gateway")) {
					return checkCouldRollback(dest);
				}
				logger.info("cannot rollback, " + type + "(" + dest.getName() + ") is complete.");

				return false;
			}
		}

		return true;
	}

	public void deleteActiveTasks(String processInstanceId) {
		List<TaskEntity> taskEntities = Context.getCommandContext().getTaskEntityManager()
				.findTasksByProcessInstanceId(processInstanceId);

		for (TaskEntity taskEntity : taskEntities)
			deleteActiveTask(taskEntity);
	}

	public void collectNodes(Node node, List<String> historyNodeIds) {
		logger.info("node : {}, {}, {}", new Object[] { node.getId(), node.getType(), node.getName() });

		for (Edge edge : node.getOutgoingEdges()) {
			logger.info("edge : {}", edge.getName());

			Node dest = edge.getDest();
			historyNodeIds.add(dest.getId());
			collectNodes(dest, historyNodeIds);
		}
	}

	public void deleteHistoryActivities(List<String> historyNodeIds) {
	}

	public void processHistoryTask(CommandContext commandContext, TaskEntity taskEntity,
			HistoricTaskInstanceEntity historicTaskInstanceEntity,
			HistoricActivityInstanceEntity historicActivityInstanceEntity) {
		if (this.userId == null) {
			if (this.useLastAssignee) {
				this.userId = historicTaskInstanceEntity.getAssignee();
			} else {
				String processDefinitionId = taskEntity.getProcessDefinitionId();

				ProcessDefinitionEntity processDefinitionEntity = new GetDeploymentProcessDefinitionCmd(
						processDefinitionId).execute(commandContext);

				TaskDefinition taskDefinition = (TaskDefinition) processDefinitionEntity.getTaskDefinitions()
						.get(historicTaskInstanceEntity.getTaskDefinitionKey());

				if (taskDefinition == null) {
					String message = "cannot find taskDefinition : "
							+ historicTaskInstanceEntity.getTaskDefinitionKey();
					logger.info(message);
					throw new IllegalStateException(message);
				}

				if (taskDefinition.getAssigneeExpression() != null) {
					logger.info("assignee expression is null : {}", taskDefinition.getKey());
					this.userId = ((String) taskDefinition.getAssigneeExpression().getValue(taskEntity));
				}

			}

		}

		TaskEntity task = TaskEntity.create(new Date());
		task.setProcessDefinitionId(historicTaskInstanceEntity.getProcessDefinitionId());

		task.setAssigneeWithoutCascade(this.userId);
		task.setParentTaskIdWithoutCascade(historicTaskInstanceEntity.getParentTaskId());
		task.setNameWithoutCascade(historicTaskInstanceEntity.getName());
		task.setTaskDefinitionKey(historicTaskInstanceEntity.getTaskDefinitionKey());
		task.setExecutionId(historicTaskInstanceEntity.getExecutionId());
		task.setPriority(historicTaskInstanceEntity.getPriority());
		task.setProcessInstanceId(historicTaskInstanceEntity.getProcessInstanceId());
		task.setExecutionId(historicTaskInstanceEntity.getExecutionId());
		task.setDescriptionWithoutCascade(historicTaskInstanceEntity.getDescription());
		task.setTenantId(historicTaskInstanceEntity.getTenantId());

		Context.getCommandContext().getTaskEntityManager().insert(task);

		ExecutionEntity executionEntity = Context.getCommandContext().getExecutionEntityManager()
				.findExecutionById(historicTaskInstanceEntity.getExecutionId());
		executionEntity.setActivity(getActivity(historicActivityInstanceEntity));

		Context.getCommandContext().getHistoryManager().recordActivityStart(executionEntity);

		Context.getCommandContext().getHistoryManager().recordTaskCreated(task, executionEntity);
		Context.getCommandContext().getHistoryManager().recordTaskId(task);

		Context.getCommandContext().getHistoryManager().recordTaskAssignment(task);
		try {
			createHumanTask(task, historicTaskInstanceEntity);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	public ActivityImpl getActivity(HistoricActivityInstanceEntity historicActivityInstanceEntity) {
		ProcessDefinitionEntity processDefinitionEntity = new GetDeploymentProcessDefinitionCmd(
				historicActivityInstanceEntity.getProcessDefinitionId()).execute(Context.getCommandContext());

		return processDefinitionEntity.findActivity(historicActivityInstanceEntity.getActivityId());
	}

	public void deleteActiveTask(TaskEntity taskEntity) {
		ProcessDefinitionEntity processDefinitionEntity = new GetDeploymentProcessDefinitionCmd(
				taskEntity.getProcessDefinitionId()).execute(Context.getCommandContext());

		ActivityImpl activityImpl = processDefinitionEntity.findActivity(taskEntity.getTaskDefinitionKey());

		if (isMultiInstance(activityImpl)) {
			logger.info("{} is multiInstance", taskEntity.getId());
			this.multiInstanceExecutionIds.add(taskEntity.getExecution().getParent().getId());
			logger.info("append : {}", taskEntity.getExecution().getParent().getId());
		}

		Context.getCommandContext().getTaskEntityManager().deleteTask(taskEntity, "回退", false);

		JdbcTemplate jdbcTemplate = (JdbcTemplate) ApplicationContextHelper.getBean(JdbcTemplate.class);

		List<Map<String, Object>> list = jdbcTemplate.queryForList(
				"SELECT * FROM ACT_HI_ACTINST WHERE TASK_ID_=? AND END_TIME_ IS NULL", new Object[] { this.taskId });

		Date now = new Date();

		for (Map map : list) {
			Date startTime = (Date) map.get("START_TIME_");
			long duration = now.getTime() - startTime.getTime();
			jdbcTemplate.update("UPDATE ACT_HI_ACTINST SET END_TIME_=?,DURATION_=? WHERE ID_=?",
					new Object[] { now, Long.valueOf(duration), map.get("ID_") });
		}

		HumanTaskConnector humanTaskConnector = (HumanTaskConnector) ApplicationContextHelper
				.getBean(HumanTaskConnector.class);
		HumanTaskDTO humanTaskDto = humanTaskConnector.findHumanTaskByTaskId(this.taskId);
		humanTaskDto.setCompleteTime(new Date());
		humanTaskDto.setStatus("rollback");
		humanTaskConnector.saveHumanTask(humanTaskDto);
	}

	public boolean isSkipActivity(String historyActivityId) {
		JdbcTemplate jdbcTemplate = (JdbcTemplate) ApplicationContextHelper.getBean(JdbcTemplate.class);
		String historyTaskId = (String) jdbcTemplate.queryForObject("SELECT TASK_ID_ FROM ACT_HI_ACTINST WHERE ID_=?",
				String.class, new Object[] { historyActivityId });

		HistoricTaskInstanceEntity historicTaskInstanceEntity = Context.getCommandContext()
				.getHistoricTaskInstanceEntityManager().findHistoricTaskInstanceById(historyTaskId);
		String deleteReason = historicTaskInstanceEntity.getDeleteReason();

		return "跳过".equals(deleteReason);
	}

	public HumanTaskDTO createHumanTask(DelegateTask delegateTask,
			HistoricTaskInstanceEntity historicTaskInstanceEntity) throws Exception {
		HumanTaskConnector humanTaskConnector = (HumanTaskConnector) ApplicationContextHelper
				.getBean(HumanTaskConnector.class);
		HumanTaskDTO humanTaskDto = new HumanTaskBuilder().setDelegateTask(delegateTask).build();

		if ("发起流程".equals(historicTaskInstanceEntity.getDeleteReason())) {
			humanTaskDto.setCatalog("start");
		}

		HistoricProcessInstance historicProcessInstance = Context.getCommandContext()
				.getHistoricProcessInstanceEntityManager()
				.findHistoricProcessInstance(delegateTask.getProcessInstanceId());
		humanTaskDto.setProcessStarter(historicProcessInstance.getStartUserId());
		humanTaskDto = humanTaskConnector.saveHumanTask(humanTaskDto);

		return humanTaskDto;
	}

	public boolean isMultiInstance(PvmActivity pvmActivity) {
		return pvmActivity.getProperty("multiInstance") != null;
	}

	public void processMultiInstance() {
		logger.info("multiInstanceExecutionIds : {}", this.multiInstanceExecutionIds);

		for (String executionId : this.multiInstanceExecutionIds) {
			ExecutionEntity parent = Context.getCommandContext().getExecutionEntityManager()
					.findExecutionById(executionId);

			List<ExecutionEntity> children = Context.getCommandContext().getExecutionEntityManager()
					.findChildExecutionsByParentExecutionId(parent.getId());

			for (ExecutionEntity executionEntity : children) {
				executionEntity.remove();
			}

			parent.remove();
		}
	}
}