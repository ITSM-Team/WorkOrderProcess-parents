package com.citsh.config;

import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;

public class JumpCmd implements Command<Object> {
	private String activityId;
	private String executionId;
	private String jumpOrigin;

	public JumpCmd(String executionId, String activityId) {
		this(executionId, activityId, "jump");
	}

	public JumpCmd(String executionId, String activityId, String jumpOrigin) {
		this.activityId = activityId;
		this.executionId = executionId;
		this.jumpOrigin = jumpOrigin;
	}

	public Object execute(CommandContext commandContext) {
		ExecutionEntity executionEntity = commandContext.getExecutionEntityManager()
				.findExecutionById(this.executionId);
		executionEntity.destroyScope(this.jumpOrigin);

		ProcessDefinitionImpl processDefinition = executionEntity.getProcessDefinition();
		ActivityImpl activity = processDefinition.findActivity(this.activityId);

		executionEntity.executeActivity(activity);

		return null;
	}
}
