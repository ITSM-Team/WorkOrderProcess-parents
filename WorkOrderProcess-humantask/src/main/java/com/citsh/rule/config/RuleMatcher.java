package com.citsh.rule.config;

public abstract interface RuleMatcher
{
  public abstract boolean matches(String paramString);

  public abstract String getValue(String paramString);
}