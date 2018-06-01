package com.citsh.humantask;

import java.util.Date;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

public class HumanTaskBuilder {
	private HumanTaskDTO humanTaskDto = new HumanTaskDTO();

	public HumanTaskBuilder setDelegateTask(DelegateTask delegateTask) {
		this.humanTaskDto.setBusinessKey(delegateTask.getExecution().getProcessBusinessKey());
		this.humanTaskDto.setName(delegateTask.getName());
		this.humanTaskDto.setDescription(delegateTask.getDescription());

		this.humanTaskDto.setCode(delegateTask.getTaskDefinitionKey());
		this.humanTaskDto.setAssignee(delegateTask.getAssignee());
		this.humanTaskDto.setOwner(delegateTask.getOwner());
		this.humanTaskDto.setDelegateStatus("none");
		this.humanTaskDto.setPriority(delegateTask.getPriority());
		this.humanTaskDto.setCreateTime(new Date());
		this.humanTaskDto.setDuration(delegateTask.getDueDate() + "");
		this.humanTaskDto.setSuspendStatus("none");
		this.humanTaskDto.setCategory(delegateTask.getCategory());
		this.humanTaskDto.setForm(delegateTask.getFormKey());
		this.humanTaskDto.setTaskId(delegateTask.getId());
		this.humanTaskDto.setExecutionId(delegateTask.getExecutionId());
		this.humanTaskDto.setProcessInstanceId(delegateTask.getProcessInstanceId());
		this.humanTaskDto.setProcessDefinitionId(delegateTask.getProcessDefinitionId());
		this.humanTaskDto.setTenantId(delegateTask.getTenantId());
		this.humanTaskDto.setStatus("active");
		this.humanTaskDto.setCatalog("normal");

		ExecutionEntity executionEntity = (ExecutionEntity) delegateTask.getExecution();
		ExecutionEntity processInstance = executionEntity.getProcessInstance();
		this.humanTaskDto.setPresentationSubject(processInstance.getName());

		String userId = Authentication.getAuthenticatedUserId();
		this.humanTaskDto.setProcessStarter(userId);

		return this;
	}

	public HumanTaskBuilder setVote(boolean isVote) {
		if (isVote) {
			this.humanTaskDto.setCatalog("vote");
		}

		return this;
	}

	public HumanTaskDTO build() {
		return this.humanTaskDto;
	}
}