package com.citsh.config;

import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.cmd.GetDeploymentProcessDefinitionCmd;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.runtime.InterpretableExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReOpenProcessCmd implements Command<Void> {
	private static Logger logger = LoggerFactory.getLogger(ReOpenProcessCmd.class);
	private String historicProcessInstanceId;

	public ReOpenProcessCmd(String historicProcessInstanceId) {
		this.historicProcessInstanceId = historicProcessInstanceId;
	}

	public Void execute(CommandContext commandContext) {
		HistoricProcessInstanceEntity historicProcessInstanceEntity = commandContext
				.getHistoricProcessInstanceEntityManager().findHistoricProcessInstance(this.historicProcessInstanceId);

		if (historicProcessInstanceEntity.getEndTime() == null) {
			logger.info("historicProcessInstanceId is running");

			return null;
		}

		historicProcessInstanceEntity.setEndActivityId(null);
		historicProcessInstanceEntity.setEndTime(null);

		String processDefinitionId = historicProcessInstanceEntity.getProcessDefinitionId();
		String initiator = historicProcessInstanceEntity.getStartUserId();
		String businessKey = historicProcessInstanceEntity.getBusinessKey();

		ProcessDefinitionEntity processDefinition = new GetDeploymentProcessDefinitionCmd(processDefinitionId)
				.execute(commandContext);

		ExecutionEntity processInstance = createProcessInstance(historicProcessInstanceEntity.getId(), businessKey,
				initiator, processDefinition);
		try {
			Authentication.setAuthenticatedUserId(initiator);

			processInstance.start();
		} finally {
			Authentication.setAuthenticatedUserId(null);
		}

		return null;
	}

	public ExecutionEntity createProcessInstance(String processInstanceId, String businessKey,
			String authenticatedUserId, ProcessDefinitionEntity processDefinition) {
		ExecutionEntity processInstance = createProcessInstance(processDefinition, processInstanceId);

		processInstance.setExecutions(new ArrayList());
		processInstance.setProcessDefinition(processDefinition);

		if (businessKey != null) {
			processInstance.setBusinessKey(businessKey);
		}

		processInstance.setProcessInstance(processInstance);

		String initiatorVariableName = (String) processDefinition.getProperty("initiatorVariableName");

		if (initiatorVariableName != null) {
			processInstance.setVariable(initiatorVariableName, authenticatedUserId);
		}

		return processInstance;
	}

	public ExecutionEntity createProcessInstance(ProcessDefinitionEntity processDefinition, String id) {
		ActivityImpl initial = processDefinition.getInitial();

		if (initial == null) {
			throw new ActivitiException(
					"Cannot start process instance, initial activity where the process instance should start is null.");
		}

		ExecutionEntity processInstance = new ExecutionEntity(initial);
		processInstance.setId(id);
		processInstance.insert();
		processInstance.setProcessDefinition(processDefinition);
		processInstance.setTenantId(processDefinition.getTenantId());
		processInstance.setProcessInstance(processInstance);
		processInstance.initialize();

		InterpretableExecution scopeInstance = processInstance;

		List<ActivityImpl> initialActivityStack = processDefinition.getInitialActivityStack(initial);

		for (ActivityImpl initialActivity : initialActivityStack) {
			if (initialActivity.isScope()) {
				scopeInstance = (InterpretableExecution) scopeInstance.createExecution();
				scopeInstance.setActivity(initialActivity);

				if (initialActivity.isScope()) {
					scopeInstance.initialize();
				}
			}
		}

		scopeInstance.setActivity(initial);

		return processInstance;
	}
}
