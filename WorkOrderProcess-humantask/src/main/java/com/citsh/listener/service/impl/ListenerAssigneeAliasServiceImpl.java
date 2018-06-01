package com.citsh.listener.service.impl;

import com.citsh.activiti.service.InternalProcessService;
import com.citsh.humantask.entity.TaskInfo;
import com.citsh.humantask.entity.TaskParticipant;
import com.citsh.humantask.service.TaskParticipantService;
import com.citsh.id.IdGenerator;
import com.citsh.listener.service.ListenerService;
import com.citsh.rule.config.AssigneeRuleType;
import com.citsh.rule.config.EqualsRuleMatcher;
import com.citsh.rule.config.PrefixRuleMatcher;
import com.citsh.rule.config.RuleMatcher;
import com.citsh.rule.service.AssigneeRule;
import com.citsh.rule.service.impl.AssigneeRuleActivitiImpl;
import com.citsh.rule.service.impl.AssigneeRuleInitiatorImpl;
import com.citsh.rule.service.impl.AssigneeRulePositionImpl;
import com.citsh.rule.service.impl.AssigneeRuleSuperiorImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ListenerAssigneeAliasServiceImpl implements ListenerService {
	private static Logger logger = LoggerFactory.getLogger(ListenerAssigneeAliasServiceImpl.class);

	@Autowired
	private InternalProcessService internalProcessService;

	@Autowired
	private TaskParticipantService taskParticipantService;

	@Autowired
	private IdGenerator idGenerator;
	private Map<RuleMatcher, AssigneeRule> assigneeRuleMap = new HashMap();

	public ListenerAssigneeAliasServiceImpl() {
		AssigneeRuleActivitiImpl assigneeRuleActivitiImpl = new AssigneeRuleActivitiImpl();
		AssigneeRuleInitiatorImpl assigneeRuleInitiatorImpl = new AssigneeRuleInitiatorImpl();
		AssigneeRulePositionImpl assigneeRulePositionImpl = new AssigneeRulePositionImpl();
		AssigneeRuleSuperiorImpl assigneeRuleSuperiorImpl = new AssigneeRuleSuperiorImpl();
		this.assigneeRuleMap.put(new EqualsRuleMatcher(AssigneeRuleType.Activiti.getValue()), assigneeRuleActivitiImpl);
		this.assigneeRuleMap.put(new EqualsRuleMatcher(AssigneeRuleType.Initiator.getValue()),
				assigneeRuleInitiatorImpl);
		this.assigneeRuleMap.put(new PrefixRuleMatcher(AssigneeRuleType.Position.getValue()), assigneeRulePositionImpl);
		this.assigneeRuleMap.put(new PrefixRuleMatcher(AssigneeRuleType.Superior.getValue()), assigneeRuleSuperiorImpl);
	}

	public void onCreate(TaskInfo taskInfo) {
		String assignee = taskInfo.getAssignee();
		if (assignee == null) {
			return;
		}
		if (assignee.startsWith("${")) {
			assignee = this.internalProcessService.executeExpression(taskInfo.getTaskId(), assignee).toString();
			taskInfo.setAssignee(assignee);
			return;
		}
		for (Map.Entry entry : this.assigneeRuleMap.entrySet()) {
			RuleMatcher ruleMatcher = (RuleMatcher) entry.getKey();
			if (!ruleMatcher.matches(assignee)) {
				continue;
			}
			String value = ruleMatcher.getValue(assignee);
			AssigneeRule assigneeRule = (AssigneeRule) entry.getValue();
			logger.debug("value:{}", value);
			logger.debug("assigneeRule:{}", assigneeRule);
			if ((assigneeRule instanceof AssigneeRuleSuperiorImpl))
				processSuperior(taskInfo, assigneeRule);
			else if ((assigneeRule instanceof AssigneeRuleInitiatorImpl))
				processInitiator(taskInfo, assigneeRule);
			else if ((assigneeRule instanceof AssigneeRuleActivitiImpl))
				processActiviti(taskInfo, assigneeRule, value);
			else if ((assigneeRule instanceof AssigneeRulePositionImpl))
				processPosition(taskInfo, assigneeRule, value);
		}
	}

	public void onComplete(TaskInfo taskInfo) {
	}

	public void processSuperior(TaskInfo taskInfo, AssigneeRule assigneeRule) {
		String processInstanceId = taskInfo.getProcessInstanceId();
		String startUserId = this.internalProcessService.findInitiator(processInstanceId);
		String superiorUserId = assigneeRule.process(startUserId);
		logger.debug("superiorUserId:{}", superiorUserId);
		taskInfo.setAssignee(superiorUserId);
	}

	public void processInitiator(TaskInfo taskInfo, AssigneeRule assigneeRule) {
		String processInstanceId = taskInfo.getProcessInstanceId();
		String startUserId = this.internalProcessService.findInitiator(processInstanceId);
		String initiatorUserId = assigneeRule.process(startUserId);
		logger.debug("initiatorUserId:{}", initiatorUserId);
		taskInfo.setAssignee(initiatorUserId);
	}

	public void processActiviti(TaskInfo taskInfo, AssigneeRule assigneeRule, String value) {
		String processInstanceId = taskInfo.getProcessInstanceId();
		List activitiUserId = assigneeRule.process(value, processInstanceId);
		logger.debug("activitiUserId:{}", activitiUserId.toString());
		if (activitiUserId.size() > 0)
			taskInfo.setAssignee((String) activitiUserId.get(0));
	}

	public void processPosition(TaskInfo taskInfo, AssigneeRule assigneeRule, String value) {
		String processInstanceId = taskInfo.getProcessInstanceId();
		String startUserId = this.internalProcessService.findInitiator(processInstanceId);
		List<String> positionUserId = assigneeRule.process(value, startUserId);
		logger.debug("positionUserId:{}", positionUserId.toString());
		if (positionUserId.isEmpty())
			logger.info("taskinfo code:{} positionUserId is empty", taskInfo.getCode());
		else if (positionUserId.size() == 1)
			taskInfo.setAssignee((String) positionUserId.get(0));
		else
			for (String userId : positionUserId) {
				TaskParticipant taskParticipant = new TaskParticipant();
				taskParticipant.setCategory("candidate");
				taskParticipant.setType("user");
				taskParticipant.setRef(userId);
				taskParticipant.setId(this.idGenerator.generateId());
				taskParticipant.setTaskInfo(taskInfo);
				this.taskParticipantService.save(taskParticipant);
			}
	}
}