package com.citsh.graph.entity;

import java.util.ArrayList;
import java.util.List;

public class Node extends GraphElement
{
  private String type;
  private boolean active;
  private List<Edge> incomingEdges = new ArrayList();

  private List<Edge> outgoingEdges = new ArrayList();

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean isActive() {
    return this.active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public List<Edge> getIncomingEdges() {
    return this.incomingEdges;
  }

  public void setIncomingEdges(List<Edge> incomingEdges) {
    this.incomingEdges = incomingEdges;
  }

  public List<Edge> getOutgoingEdges() {
    return this.outgoingEdges;
  }

  public void setOutgoingEdges(List<Edge> outgoingEdges) {
    this.outgoingEdges = outgoingEdges;
  }
}