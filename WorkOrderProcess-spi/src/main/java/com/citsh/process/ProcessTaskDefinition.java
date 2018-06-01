/*    */ package com.citsh.process;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ProcessTaskDefinition
/*    */ {
/*    */   private String key;
/*    */   private String name;
/*    */   private String assignee;
/* 10 */   private List<ParticipantDefinition> participantDefinitions = new ArrayList();
/*    */ 
/*    */   public String getKey() {
/* 13 */     return this.key;
/*    */   }
/*    */ 
/*    */   public void setKey(String key) {
/* 17 */     this.key = key;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 21 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 25 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public String getAssignee() {
/* 29 */     return this.assignee;
/*    */   }
/*    */ 
/*    */   public void setAssignee(String assignee) {
/* 33 */     this.assignee = assignee;
/*    */   }
/*    */ 
/*    */   public List<ParticipantDefinition> getParticipantDefinitions() {
/* 37 */     return this.participantDefinitions;
/*    */   }
/*    */ 
/*    */   public void setParticipantDefinitions(List<ParticipantDefinition> participantDefinitions) {
/* 41 */     this.participantDefinitions = participantDefinitions;
/*    */   }
/*    */ 
/*    */   public void addParticipantDefinition(String type, String value, String status) {
/* 45 */     ParticipantDefinition participantDefinition = new ParticipantDefinition();
/* 46 */     participantDefinition.setType(type);
/* 47 */     participantDefinition.setValue(value);
/* 48 */     participantDefinition.setStatus(status);
/* 49 */     this.participantDefinitions.add(participantDefinition);
/*    */   }
/*    */ }

/* Location:           E:\work\process\WorkOrderProcess-spi-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.citsh.process.ProcessTaskDefinition
 * JD-Core Version:    0.6.0
 */