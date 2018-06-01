package com.citsh.rule.service;

import java.util.List;

public abstract interface AssigneeRule
{
  public abstract String process(String paramString);

  public abstract List<String> process(String paramString1, String paramString2);
}