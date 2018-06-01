package com.citsh.cmd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.impl.cmd.GetBpmnModelCmd;
import org.activiti.engine.impl.cmd.GetDeploymentProcessDefinitionCmd;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.UserTask;

import com.citsh.config.entity.BpmConfBase;
import com.citsh.config.entity.BpmConfCountersign;
import com.citsh.config.entity.BpmConfNode;
import com.citsh.config.service.BpmConfBaseService;
import com.citsh.config.service.BpmConfCountersignService;
import com.citsh.config.service.BpmConfNodeService;
import com.citsh.form.entity.BpmConfForm;
import com.citsh.form.service.BpmConfFormService;
import com.citsh.graph.entity.Graph;
import com.citsh.graph.entity.Node;
import com.citsh.humantask.TaskDefinitionBuilder;
import com.citsh.humantask.TaskDefinitionConnector;
import com.citsh.humantask.TaskDefinitionDTO;
import com.citsh.id.IdGenerator;
import com.citsh.listener.entity.BpmConfListener;
import com.citsh.listener.service.BpmConfListenerService;
import com.citsh.spring.ApplicationContextHelper;
import com.citsh.user.entity.BpmConfUser;
import com.citsh.user.service.BpmConfUserService;

/**
 * 把xml解析的内存模型保存到数据库里
 */
public class SyncProcessCmd implements Command<Void> {

	/** 流程定义ID */
	private String processDefinitionId;

	public SyncProcessCmd(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	@Override
	public Void execute(CommandContext commandContext) {
		// 获取流程定义实体
		ProcessDefinitionEntity processDefinitionEntity = new GetDeploymentProcessDefinitionCmd(processDefinitionId)
				.execute(commandContext);
		String processDefinitionKey = processDefinitionEntity.getKey();
		int processDefinitionVersion = processDefinitionEntity.getVersion();

		BpmConfBaseService bpmConfBaseService = getBpmConfBaseService();
		// 根据key和verson查询唯一
		BpmConfBase bpmConfBase = bpmConfBaseService.findByHSQLOne(
				" processDefinitionKey=? and processDefinitionVersion=?", processDefinitionKey,
				processDefinitionVersion);
		if (bpmConfBase == null) {
			bpmConfBase = new BpmConfBase();
			Long id = getIdGenerator().generateId();
			bpmConfBase.setId(id);
			bpmConfBase.setProcessDefinitionId(processDefinitionId);
			bpmConfBase.setProcessDefinitionKey(processDefinitionKey);
			bpmConfBase.setProcessDefinitionVersion(processDefinitionVersion);
			bpmConfBaseService.save(bpmConfBase);
		} else if (bpmConfBase.getProcessDefinitionId() == null) {
			bpmConfBase.setProcessDefinitionId(processDefinitionId);
			bpmConfBaseService.save(bpmConfBase);
		}
		BpmnModel bpmnModel = new GetBpmnModelCmd(processDefinitionId).execute(commandContext);
		Graph graph = new FindGraphCmd(processDefinitionId).execute(commandContext);
		this.processGlobal(bpmnModel, 1, bpmConfBase);

		int priority = 2;

		for (Node node : graph.getNodes()) {
			if ("exclusiveGateway".equals(node.getType())) {
				// 如果是网关
				continue;
			} else if ("userTask".equals(node.getType())) {
				// 如果是用户任务
				this.processUserTask(node, bpmnModel, priority++, bpmConfBase);
			} else if ("startEvent".equals(node.getType())) {
				// 如果是开始节点
				this.processStartEvent(node, bpmnModel, priority++, bpmConfBase);
			} else if ("endEvent".equals(node.getType())) {
				// 结束节点
				this.processEndEvent(node, bpmnModel, priority++, bpmConfBase);
			}

		}
		return null;
	}

	/**
	 * 配置结束事件
	 */
	private void processEndEvent(Node node, BpmnModel bpmnModel, int priority, BpmConfBase bpmConfBase) {
		BpmConfNodeService bpmConfNodeService = getBpmConfNodeService();
		BpmConfNode bpmConfNode = bpmConfNodeService.findByHSQLOne(" code=? and bpmConfBase.id=?", node.getId(),
				bpmConfBase.getId());
		if (bpmConfNode == null) {
			bpmConfNode = new BpmConfNode();
			Long id = getIdGenerator().generateId();
			bpmConfNode.setId(id);
			bpmConfNode.setCode(node.getId());
			bpmConfNode.setName(node.getName());
			bpmConfNode.setType(node.getType());
			bpmConfNode.setConfUser(2);
			bpmConfNode.setConfListener(0);
			bpmConfNode.setConfRule(2);
			bpmConfNode.setConfForm(2);
			bpmConfNode.setConfOperation(2);
			bpmConfNode.setConfNotice(0);
			bpmConfNode.setPriority(priority);
			bpmConfNode.setBpmConfBase(bpmConfBase);
			bpmConfNodeService.save(bpmConfNode);
		}
		FlowElement flowElement = bpmnModel.getFlowElement(node.getId());
		// 配置监听器
		this.processListener(flowElement.getExecutionListeners(), bpmConfNode);

	}

	/**
	 * 配置开始事件
	 */
	private void processStartEvent(Node node, BpmnModel bpmnModel, int priority, BpmConfBase bpmConfBase) {
		BpmConfNodeService bpmConfNodeService = getBpmConfNodeService();
		BpmConfNode bpmConfNode = bpmConfNodeService.findByHSQLOne(" code=? and bpmConfBase.id=?", node.getId(),
				bpmConfBase.getId());
		if (bpmConfNode == null) {
			bpmConfNode = new BpmConfNode();
			Long id = getIdGenerator().generateId();
			bpmConfNode.setId(id);
			bpmConfNode.setCode(node.getId());
			bpmConfNode.setName(node.getName());
			bpmConfNode.setType(node.getType());
			bpmConfNode.setConfUser(2);
			bpmConfNode.setConfListener(0);
			bpmConfNode.setConfRule(2);
			bpmConfNode.setConfForm(0);
			bpmConfNode.setConfOperation(2);
			bpmConfNode.setConfNotice(0);
			bpmConfNode.setPriority(priority);
			bpmConfNode.setBpmConfBase(bpmConfBase);
			bpmConfNodeService.save(bpmConfNode);
		}

		FlowElement flowElement = bpmnModel.getFlowElement(node.getId());

		// 配置监听器
		this.processListener(flowElement.getExecutionListeners(), bpmConfNode);

		StartEvent startEvent = (StartEvent) flowElement;
		// 配置表单
		this.processForm(startEvent, bpmConfNode);

	}

	/**
	 * 配置 startEvent表单
	 */
	private void processForm(StartEvent startEvent, BpmConfNode bpmConfNode) {
		if (startEvent.getFormKey() == null) {
			return;
		}
		BpmConfFormService bpmConfFormService = getBpmConfFormService();
		BpmConfForm bpmConfForm = bpmConfFormService.listBySQLOne("bpmConfNode.id=?", bpmConfNode.getId());
		if (bpmConfForm == null) {
			bpmConfForm = new BpmConfForm();
			Long id = getIdGenerator().generateId();
			bpmConfForm.setId(id);
			bpmConfForm.setValue(startEvent.getFormKey());
			bpmConfForm.setType(0);
			bpmConfForm.setOriginValue(startEvent.getFormKey());
			bpmConfForm.setOriginType(0);
			bpmConfForm.setStatus(0);
			bpmConfForm.setBpmConfNode(bpmConfNode);
			bpmConfFormService.save(bpmConfForm);
		}
	}

	/**
	 * 配置用户任务
	 */
	private void processUserTask(Node node, BpmnModel bpmnModel, int priority, BpmConfBase bpmConfBase) {
		BpmConfNodeService bpmConfNodeService = getBpmConfNodeService();
		BpmConfNode bpmConfNode = bpmConfNodeService.findByHSQLOne(" code=? and bpmConfBase.id=?", node.getId(),
				bpmConfBase.getId());
		if (bpmConfNode == null) {
			bpmConfNode = new BpmConfNode();
			Long id = getIdGenerator().generateId();
			bpmConfNode.setId(id);
			bpmConfNode.setCode(node.getId());
			bpmConfNode.setName(node.getName());
			bpmConfNode.setType(node.getType());
			bpmConfNode.setConfUser(0);
			bpmConfNode.setConfListener(0);
			bpmConfNode.setConfRule(0);
			bpmConfNode.setConfForm(0);
			bpmConfNode.setConfOperation(0);
			bpmConfNode.setConfNotice(0);
			bpmConfNode.setPriority(priority);
			bpmConfNode.setBpmConfBase(bpmConfBase);
			bpmConfNodeService.save(bpmConfNode);
		}

		// 配置参与者
		UserTask userTask = (UserTask) bpmnModel.getFlowElement(node.getId());
		int index = 1;
		// 配置负责人
		index = this.processUserTaskConf(bpmConfNode, userTask.getAssignee(), 0, index);

		// 配置候选人
		for (String candidateUser : userTask.getCandidateUsers()) {
			index = this.processUserTaskConf(bpmConfNode, candidateUser, 1, index);
		}

		// 配置候选组
		for (String candidateGroup : userTask.getCandidateGroups()) {
			index = this.processUserTaskConf(bpmConfNode, candidateGroup, 2, index);
		}

		// 配置监听器
		this.processListener(userTask.getExecutionListeners(), bpmConfNode);
		this.processListener(userTask.getTaskListeners(), bpmConfNode);

		// 配置form表单
		this.processForm(userTask, bpmConfNode);

		// 会签
		if (userTask.getLoopCharacteristics() != null) {
			BpmConfCountersign bpmConfCountersign = new BpmConfCountersign();
			Long cid = getIdGenerator().generateId();
			bpmConfCountersign.setId(cid);
			bpmConfCountersign.setType(0);
			bpmConfCountersign.setRate(100);
			bpmConfCountersign.setBpmConfNode(bpmConfNode);
			// true 串行，false 并行
			bpmConfCountersign.setSequential(userTask.getLoopCharacteristics().isSequential() ? 1 : 0);
			getBpmConfCountersignService().save(bpmConfCountersign);
		}

		// 更新TaskDefinition
		TaskDefinitionConnector taskDefinitionConnector = getTaskDefinitionConnector();
		// 初始化 TaskDefinitionDTO
		TaskDefinitionDTO definitionDTO = new TaskDefinitionBuilder().setUserTask(userTask).setUserTask(userTask)
				.setProcessDefinitionId(processDefinitionId).build();
		taskDefinitionConnector.create(definitionDTO);
	}

	/**
	 * 配置userTask 表单
	 */
	private void processForm(UserTask userTask, BpmConfNode bpmConfNode) {
		if (userTask.getFormKey() == null) {
			return;
		}
		BpmConfFormService bpmConfFormService = getBpmConfFormService();
		BpmConfForm bpmConfForm = bpmConfFormService.listBySQLOne("bpmConfNode.id=?", bpmConfNode.getId());
		if (bpmConfForm == null) {
			bpmConfForm = new BpmConfForm();
			Long id = getIdGenerator().generateId();
			bpmConfForm.setId(id);
			bpmConfForm.setValue(userTask.getFormKey());
			bpmConfForm.setType(0);
			bpmConfForm.setOriginValue(userTask.getFormKey());
			bpmConfForm.setOriginType(0);
			bpmConfForm.setStatus(0);
			bpmConfForm.setBpmConfNode(bpmConfNode);
			bpmConfFormService.save(bpmConfForm);
		}

	}

	/**
	 * 配置参与者
	 */
	private int processUserTaskConf(BpmConfNode bpmConfNode, String assignee, int type, int priority) {
		BpmConfUserService bpmConfUserService = getBpmConfUserService();
		BpmConfUser bpmConfUser = bpmConfUserService.findByHSQLOne(
				" value=? and type=? and priority=? and bpmConfNode.id=?", assignee, type, priority,
				bpmConfNode.getId());

		if (bpmConfUser == null) {
			bpmConfUser = new BpmConfUser();
			Long id = getIdGenerator().generateId();
			bpmConfUser.setId(id);
			bpmConfUser.setValue(assignee);
			bpmConfUser.setType(type);
			bpmConfUser.setStatus(0);
			bpmConfUser.setPriority(priority);
			bpmConfUser.setBpmConfNode(bpmConfNode);
			bpmConfUserService.save(bpmConfUser);
		}
		return priority + 1;
	}

	/**
	 * 全局配置
	 */
	private void processGlobal(BpmnModel bpmnModel, int priority, BpmConfBase bpmConfBase) {
		// 获取pool 里的第一个
		Process process = bpmnModel.getMainProcess();
		BpmConfNodeService bpmConfNodeService = getBpmConfNodeService();
		BpmConfNode bpmConfNode = bpmConfNodeService.findByHSQLOne(" code=? and bpmConfBase.id=?", process.getId(),
				bpmConfBase.getId());
		if (bpmConfNode == null) {
			bpmConfNode = new BpmConfNode();
			Long id = getIdGenerator().generateId();
			bpmConfNode.setId(id);
			bpmConfNode.setCode(process.getId());
			bpmConfNode.setName("全局");
			bpmConfNode.setType("process");
			bpmConfNode.setConfUser(2);
			bpmConfNode.setConfListener(0);
			bpmConfNode.setConfRule(2);
			bpmConfNode.setConfForm(0);
			bpmConfNode.setConfOperation(2);
			bpmConfNode.setConfNotice(2);
			bpmConfNode.setPriority(priority);
			bpmConfNode.setBpmConfBase(bpmConfBase);
			bpmConfNodeService.save(bpmConfNode);
		}
		// 配置监听器
		processListener(process.getExecutionListeners(), bpmConfNode);
	}

	/**
	 * 配置监听器
	 */
	private void processListener(List<ActivitiListener> executionListeners, BpmConfNode bpmConfNode) {
		Map<String, Integer> eventTypeMap = new HashMap<String, Integer>();
		eventTypeMap.put("start", 0);
		eventTypeMap.put("end", 1);
		eventTypeMap.put("take", 2);
		eventTypeMap.put("create", 3);
		eventTypeMap.put("assignment", 4);
		eventTypeMap.put("complete", 5);
		eventTypeMap.put("delete", 6);
		BpmConfListenerService bpmConfListenerService = getBpmConfListenerService();
		for (ActivitiListener activitiListener : executionListeners) {
			String value = activitiListener.getImplementation();
			int type = eventTypeMap.get(activitiListener.getEvent());
			BpmConfListener bpmConfListener = bpmConfListenerService.findByHSQLOne(
					" value=? and type=? and status=0 and bpmConfNode.id=?", value, type, bpmConfNode.getId());
			if (bpmConfListener == null) {
				bpmConfListener = new BpmConfListener();
				Long id = getIdGenerator().generateId();
				bpmConfListener.setId(id);
				bpmConfListener.setValue(value);
				bpmConfListener.setType(type);
				bpmConfListener.setBpmConfNode(bpmConfNode);
				bpmConfListenerService.save(bpmConfListener);
			}
		}

	}

	// =================================
	private BpmConfBaseService getBpmConfBaseService() {
		return ApplicationContextHelper.getBean(BpmConfBaseService.class);
	}

	private BpmConfNodeService getBpmConfNodeService() {
		return ApplicationContextHelper.getBean(BpmConfNodeService.class);
	}

	private BpmConfListenerService getBpmConfListenerService() {
		return ApplicationContextHelper.getBean(BpmConfListenerService.class);
	}

	private BpmConfUserService getBpmConfUserService() {
		return ApplicationContextHelper.getBean(BpmConfUserService.class);
	}

	private BpmConfFormService getBpmConfFormService() {
		return ApplicationContextHelper.getBean(BpmConfFormService.class);
	}

	private BpmConfCountersignService getBpmConfCountersignService() {
		return ApplicationContextHelper.getBean(BpmConfCountersignService.class);
	}

	private TaskDefinitionConnector getTaskDefinitionConnector() {
		return ApplicationContextHelper.getBean(TaskDefinitionConnector.class);
	}

	private IdGenerator getIdGenerator() {
		return ApplicationContextHelper.getBean(IdGenerator.class);
	}
}
