package com.citsh.cmd;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cmd.GetBpmnModelCmd;
import org.activiti.engine.impl.cmd.GetDeploymentProcessDefinitionCmd;
import org.activiti.engine.impl.cmd.GetDeploymentProcessDiagramCmd;
import org.activiti.engine.impl.cmd.GetModelCmd;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;

/**
 * 获取流程定义图形
 */
public class ProcessDefinitionDiagramCmd implements Command<InputStream> {
	private String processDefinitionId;

	public ProcessDefinitionDiagramCmd(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	@Override
	public InputStream execute(CommandContext commandContext) {
		ProcessDefinitionEntity processDefinitionEntity = new GetDeploymentProcessDefinitionCmd(processDefinitionId)
				.execute(commandContext);
		String diagramResourceName = processDefinitionEntity.getDiagramResourceName();
		String deploymentId = processDefinitionEntity.getDeploymentId();
		if (deploymentId != null) {
			byte[] bytes = commandContext.getResourceEntityManager()
					.findResourceByDeploymentIdAndResourceName(deploymentId, diagramResourceName).getBytes();
			InputStream inputStream = new ByteArrayInputStream(bytes);
			return inputStream;
		}

		BpmnModel bpmnModel = new GetBpmnModelCmd(processDefinitionId).execute(commandContext);
		ProcessEngineConfiguration processEngineConfiguration = Context.getProcessEngineConfiguration();
		InputStream inputStream = new DefaultProcessDiagramGenerator().generateDiagram(bpmnModel, "png",
				processEngineConfiguration.getActivityFontName(), processEngineConfiguration.getLabelFontName(),null);
		return inputStream;
	}

}
