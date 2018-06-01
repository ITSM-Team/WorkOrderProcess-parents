package com.citsh.cmd;

import org.activiti.engine.impl.interceptor.CommandContext;

import com.citsh.graph.conf.ActivitiGraphBuilder;
import com.citsh.graph.entity.Graph;
/**
 * 构建流程图
 * */
public class FindGraphCmd {
	private String processDefinitionId;

	public FindGraphCmd(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public Graph execute(CommandContext commandContext) {
		return new ActivitiGraphBuilder(processDefinitionId).build();
	}
}
