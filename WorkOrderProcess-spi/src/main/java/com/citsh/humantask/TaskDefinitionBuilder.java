package com.citsh.humantask;

import org.activiti.bpmn.model.UserTask;

public class TaskDefinitionBuilder {

	private TaskDefinitionDTO taskDefinitionDTO = new TaskDefinitionDTO();
	private UserTask userTask;
	private String processDefinitionId;

	public TaskDefinitionBuilder setUserTask(UserTask userTask) {
		this.userTask = userTask;
		return this;
	}

	public TaskDefinitionBuilder setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
		return this;
	}

	public TaskDefinitionDTO build() {
		this.initInfo();
		this.initParticipants();
		this.initForm();
		this.initCounterSign();
		return taskDefinitionDTO;
	}

	/**
	 * 会签
	 */
	private void initCounterSign() {
		if (userTask.getLoopCharacteristics() == null) {
			return;
		}
		CounterSignDTO counterSignDTO = new CounterSignDTO();
		// 会签策略百分比
		counterSignDTO.setStrategy("percent");
		// 100%
		counterSignDTO.setRate(100);

		if (userTask.getLoopCharacteristics().isSequential()) {
			// 串行
			counterSignDTO.setType("sequential");
		} else {
			// 并行
			counterSignDTO.setType("parallel");
		}
		taskDefinitionDTO.setCounterSign(counterSignDTO);
	}

	/**
	 * 初始表单
	 */
	private void initForm() {
		String formKey = userTask.getFormKey();
		if (formKey == null) {
			return;
		}
		FormDTO formDTO = new FormDTO();
		if (formKey.startsWith("external:")) {
			formDTO.setType("external");
		} else {
			formDTO.setType("internal");
		}
		formDTO.setKey(formKey);
		taskDefinitionDTO.setForm(formDTO);
	}

	/**
	 * 初始参与者
	 */
	private void initParticipants() {
		taskDefinitionDTO.setAssignee(userTask.getAssignee());

		for (String candidateUser : userTask.getCandidateUsers()) {
			taskDefinitionDTO.addCandidateUser(candidateUser);
		}

		for (String candidateGroup : userTask.getCandidateGroups()) {
			taskDefinitionDTO.addCandidateGroup(candidateGroup);
		}

	}

	private void initInfo() {
		taskDefinitionDTO.setCode(userTask.getId());
		taskDefinitionDTO.setName(userTask.getName());
		taskDefinitionDTO.setProcessDefinitionId(processDefinitionId);

	}

}
