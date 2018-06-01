package com.citsh.from;

import java.util.ArrayList;
import java.util.List;

public class FormDTO
{
  private String id;
  private String code;
  private String name;
  private String content;
  private boolean redirect;
  private String url;
  private String processDefinitionId;
  private String taskId;
  private List<String> buttons = new ArrayList();
  private String activityId;
  private boolean autoCompleteFirstTask;

  public String getId()
  {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public boolean isRedirect() {
    return this.redirect;
  }

  public void setRedirect(boolean redirect) {
    this.redirect = redirect;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getProcessDefinitionId() {
    return this.processDefinitionId;
  }

  public void setProcessDefinitionId(String processDefinitionId) {
    this.processDefinitionId = processDefinitionId;
  }

  public String getTaskId() {
    return this.taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public List<String> getButtons() {
    return this.buttons;
  }

  public void setButtons(List<String> buttons) {
    this.buttons = buttons;
  }

  public String getActivityId() {
    return this.activityId;
  }

  public void setActivityId(String activityId) {
    this.activityId = activityId;
  }

  public boolean isAutoCompleteFirstTask() {
    return this.autoCompleteFirstTask;
  }

  public void setAutoCompleteFirstTask(boolean autoCompleteFirstTask) {
    this.autoCompleteFirstTask = autoCompleteFirstTask;
  }

  public String getRelatedId() {
    if (isStartForm()) {
      return this.processDefinitionId;
    }
    return this.taskId;
  }

  public boolean isTaskForm()
  {
    return this.taskId != null;
  }

  public boolean isStartForm() {
    return this.taskId == null;
  }

  public boolean isExists() {
    return this.code != null;
  }
}