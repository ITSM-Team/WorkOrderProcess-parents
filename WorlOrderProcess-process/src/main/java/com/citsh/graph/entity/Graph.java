package com.citsh.graph.entity;

import java.util.ArrayList;
import java.util.List;

public class Graph
{
  private Node initial;

  public Node getInitial()
  {
    return this.initial;
  }

  public void setInitial(Node initial) {
    this.initial = initial;
  }

  public List<Node> getNodes() {
    List nodes = new ArrayList();
    visitNode(this.initial, nodes);

    return nodes;
  }

  public void visitNode(Node node, List<Node> nodes) {
    nodes.add(node);

    for (Edge edge : node.getOutgoingEdges()) {
      Node nextNode = edge.getDest();
      visitNode(nextNode, nodes);
    }
  }

  public List<Edge> getEdges() {
    List edges = new ArrayList();
    visitEdge(this.initial, edges);

    return edges;
  }

  public void visitEdge(Node node, List<Edge> edges) {
    for (Edge edge : node.getOutgoingEdges()) {
      edges.add(edge);

      Node nextNode = edge.getDest();
      visitEdge(nextNode, edges);
    }
  }

  public Node findById(String id) {
    for (Node node : getNodes()) {
      if (id.equals(node.getId())) {
        return node;
      }
    }

    return null;
  }
}