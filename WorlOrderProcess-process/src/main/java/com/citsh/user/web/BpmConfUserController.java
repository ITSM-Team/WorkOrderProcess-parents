package com.citsh.user.web;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.citsh.assign.entity.BpmConfAssign;
import com.citsh.assign.service.BpmConfAssignService;
import com.citsh.config.entity.BpmConfCountersign;
import com.citsh.config.entity.BpmConfNode;
import com.citsh.config.service.BpmConfCountersignService;
import com.citsh.config.service.BpmConfNodeService;
import com.citsh.humantask.TaskDefinitionConnector;
import com.citsh.humantask.TaskUserDTO;
import com.citsh.id.IdGenerator;
import com.citsh.user.entity.BpmConfUser;
import com.citsh.user.service.BpmConfUserService;

@Controller
@RequestMapping("bpm")
public class BpmConfUserController {
	private static Logger logger = LoggerFactory.getLogger(BpmConfUserController.class);
	@Autowired
	private BpmConfNodeService bpmConfNodeService;
	@Autowired
	private BpmConfUserService bpmConfUserService;
	@Autowired
	private BpmConfCountersignService bpmConfCountersignService;
	@Autowired
	private BpmConfAssignService bpmConfAssignService;
	@Autowired
	private TaskDefinitionConnector TaskDefinitionConnector;
	@Autowired
	private IdGenerator idGenerator;

	/**
	 * 查询配置用户列表
	 */
	@GetMapping("bpm-conf-user-list")
	public String list(@RequestParam("bpmConfNodeId") Long bpmConfNodeId) {
		BpmConfNode bpmConfNode = bpmConfNodeService.find(bpmConfNodeId);
		Long bpmConfBaseId = bpmConfNode.getBpmConfBase().getId();
		Long bomConfNodeId = bpmConfNode.getId();
		List<BpmConfUser> bpmConfUsers = bpmConfUserService.findBy("bpmConfNode.id", bomConfNodeId);
		BpmConfCountersign bpmConfCountersign = bpmConfCountersignService.findOneBy("bpmConfNode.id", bomConfNodeId);
		BpmConfAssign bpmConfAssign = bpmConfAssignService.findOneBy("bpmConfNode.id", bomConfNodeId);

		Model model = new RedirectAttributesModelMap();
		model.addAttribute("bpmConfBase", bpmConfNode.getBpmConfBase());
		model.addAttribute("bpmConfNode", bpmConfNode);
		model.addAttribute("bpmConfBaseId", bpmConfBaseId);
		model.addAttribute("bpmConfUsers", bpmConfUsers);
		model.addAttribute("bpmConfCountersign", bpmConfCountersign);
		model.addAttribute("bpmConfAssign", bpmConfAssign);

		return "bpm/bpm-conf-user-list";
	}

	@PostMapping("bpm-conf-user-save")
	public String save(@ModelAttribute BpmConfUser bpmConfUser, @RequestParam("bpmConfNodeId") Long bpmConfNodeId) {
		if (StringUtils.isBlank(bpmConfUser.getValue())) {
			logger.info("bpmConfUser cannot blank");
			return "redirect:/bpm/bpm-conf-user-list.do?bpmConfNodeId=" + bpmConfNodeId;
		}
		// 如果存在默认的负责人，则要设置成删除状态
		if (bpmConfUser.getType() == 0) {
			BpmConfUser targetBpmConfUser = bpmConfUserService.findByNodeIdAndStatusAndType(bpmConfNodeId, 0, 0);
			if (targetBpmConfUser != null) {
				targetBpmConfUser.setStatus(2);
				bpmConfUserService.save(targetBpmConfUser);
			}
		}

		// 如果存在添加的负责人，直接更新
		if (bpmConfUser.getType() == 0) {
			BpmConfUser targetBpmConfUser = bpmConfUserService.findByNodeIdAndStatusAndType(bpmConfNodeId, 0, 1);
			if (targetBpmConfUser != null) {
				targetBpmConfUser.setValue(bpmConfUser.getValue());
				bpmConfUser = targetBpmConfUser;
			}
		}

		bpmConfUser.setPriority(0);
		bpmConfUser.setStatus(1);
		BpmConfNode bpmConfNode = bpmConfNodeService.find(bpmConfNodeId);
		bpmConfUser.setBpmConfNode(bpmConfNode);
		Long id=bpmConfUser.getId();
		if(id==null){
			bpmConfUser.setId(idGenerator.generateId());
		}
		bpmConfUserService.save(bpmConfUser);

		BpmConfUser dest = bpmConfUser;
		// 获取节点code
		String taskDefinitionKey = dest.getBpmConfNode().getCode();
		// 获取流程实例ID
		String processDefinitionId = dest.getBpmConfNode().getBpmConfBase().getProcessDefinitionId();
		Integer type = dest.getType();
		String value = dest.getValue();
		TaskUserDTO taskUser = new TaskUserDTO();

		if (type == 0) {
			// 负责人
			taskUser.setCatalog("assignee");
		} else if ((type == 1) || (type == 2)) {
			// 候选人
			taskUser.setCatalog("candidate");
		} else if (type == 3) {
			// 抄送
			taskUser.setCatalog("notification");
		}

		if ((type == 0) || (type == 1)) {
			taskUser.setType("user");
		} else if (type == 2) {
			taskUser.setType("group");
		}

		taskUser.setValue(value);

		TaskDefinitionConnector.addTaskUser(taskDefinitionKey, processDefinitionId, taskUser);
		return "redirect:/bpm/bpm-conf-user-list.do?bpmConfNodeId=" + bpmConfNodeId;

	}

}
