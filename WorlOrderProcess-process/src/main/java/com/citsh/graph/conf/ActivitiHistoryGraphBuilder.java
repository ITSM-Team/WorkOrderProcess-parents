package com.citsh.graph.conf;

import com.citsh.graph.entity.Edge;
import com.citsh.graph.entity.Graph;
import com.citsh.graph.entity.Node;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.HistoricActivityInstanceQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.cmd.GetDeploymentProcessDefinitionCmd;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActivitiHistoryGraphBuilder
{
  private static Logger logger = LoggerFactory.getLogger(ActivitiHistoryGraphBuilder.class);
  private String processInstanceId;
  private ProcessDefinitionEntity processDefinitionEntity;
  private List<HistoricActivityInstance> historicActivityInstances;
  private List<HistoricActivityInstance> visitedHistoricActivityInstances = new ArrayList();
  private Map<String, Node> nodeMap = new HashMap();

  public ActivitiHistoryGraphBuilder(String processInstanceId) {
    this.processInstanceId = processInstanceId;
  }

  public Graph build() {
    fetchProcessDefinitionEntity();
    fetchHistoricActivityInstances();

    Graph graph = new Graph();

    for (HistoricActivityInstance historicActivityInstance : this.historicActivityInstances) {
      Node currentNode = new Node();
      currentNode.setId(historicActivityInstance.getId());
      currentNode.setName(historicActivityInstance.getActivityId());
      currentNode.setType(historicActivityInstance.getActivityType());
      currentNode.setActive(historicActivityInstance.getEndTime() == null);
      logger.debug("currentNode : {}:{}", currentNode.getName(), currentNode.getId());

      Edge previousEdge = findPreviousEdge(currentNode, historicActivityInstance.getStartTime().getTime());

      if (previousEdge == null) {
        if (graph.getInitial() != null) {
          throw new IllegalStateException("already set an initial.");
        }

        graph.setInitial(currentNode);
      } else {
        logger.debug("previousEdge : {}", previousEdge.getName());
      }

      this.nodeMap.put(currentNode.getId(), currentNode);
      this.visitedHistoricActivityInstances.add(historicActivityInstance);
    }

    if (graph.getInitial() == null) {
      throw new IllegalStateException("cannot find initial.");
    }

    return graph;
  }

  public void fetchProcessDefinitionEntity()
  {
    String processDefinitionId = Context.getCommandContext().getHistoricProcessInstanceEntityManager()
      .findHistoricProcessInstance(this.processInstanceId)
      .getProcessDefinitionId();
    GetDeploymentProcessDefinitionCmd cmd = new GetDeploymentProcessDefinitionCmd(processDefinitionId);
    this.processDefinitionEntity = cmd.execute(Context.getCommandContext());
  }

  public void fetchHistoricActivityInstances() {
    HistoricActivityInstanceQueryImpl historicActivityInstanceQueryImpl = new HistoricActivityInstanceQueryImpl();

    historicActivityInstanceQueryImpl.processInstanceId(this.processInstanceId).orderByHistoricActivityInstanceId()
      .asc();

    Page page = new Page(0, 100);
    this.historicActivityInstances = Context.getCommandContext().getHistoricActivityInstanceEntityManager()
      .findHistoricActivityInstancesByQueryCriteria(historicActivityInstanceQueryImpl, page);
  }

  public Edge findPreviousEdge(Node currentNode, long currentStartTime)
  {
    String activityId = currentNode.getName();
    ActivityImpl activityImpl = this.processDefinitionEntity.findActivity(activityId);
    HistoricActivityInstance nestestHistoricActivityInstance = null;
    String temporaryPvmTransitionId = null;

    for (PvmTransition pvmTransition : activityImpl.getIncomingTransitions()) {
      PvmActivity source = pvmTransition.getSource();

      String previousActivityId = source.getId();

      HistoricActivityInstance visitiedHistoryActivityInstance = findVisitedHistoricActivityInstance(previousActivityId);

      if ((visitiedHistoryActivityInstance == null) || 
        (visitiedHistoryActivityInstance.getEndTime() == null))
      {
        continue;
      }
      logger.debug("current activity start time : {}", new Date(currentStartTime));
      logger.debug("nestest activity end time : {}", visitiedHistoryActivityInstance.getEndTime());

      if (currentStartTime < visitiedHistoryActivityInstance.getEndTime().getTime())
      {
        continue;
      }
      if (nestestHistoricActivityInstance == null) {
        nestestHistoricActivityInstance = visitiedHistoryActivityInstance;
        temporaryPvmTransitionId = pvmTransition.getId();
      }
      else if (currentStartTime - nestestHistoricActivityInstance.getEndTime().getTime() > currentStartTime - visitiedHistoryActivityInstance
        .getEndTime().getTime())
      {
        nestestHistoricActivityInstance = visitiedHistoryActivityInstance;
        temporaryPvmTransitionId = pvmTransition.getId();
      }

    }

    if (nestestHistoricActivityInstance == null) {
      return null;
    }

    Node previousNode = (Node)this.nodeMap.get(nestestHistoricActivityInstance.getId());

    if (previousNode == null) {
      return null;
    }

    logger.debug("previousNode : {}:{}", previousNode.getName(), previousNode.getId());

    Edge edge = new Edge();
    edge.setName(temporaryPvmTransitionId);
    previousNode.getOutgoingEdges().add(edge);
    edge.setSrc(previousNode);
    edge.setDest(currentNode);

    return edge;
  }

  public HistoricActivityInstance findVisitedHistoricActivityInstance(String activityId) {
    for (int i = this.visitedHistoricActivityInstances.size() - 1; i >= 0; i--) {
      HistoricActivityInstance historicActivityInstance = (HistoricActivityInstance)this.visitedHistoricActivityInstances.get(i);

      if (activityId.equals(historicActivityInstance.getActivityId())) {
        return historicActivityInstance;
      }
    }

    return null;
  }
}