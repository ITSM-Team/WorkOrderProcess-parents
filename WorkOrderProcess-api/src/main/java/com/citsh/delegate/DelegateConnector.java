package com.citsh.delegate;

public abstract interface DelegateConnector
{
  public abstract String findAttorney(String paramString1, String paramString2, String paramString3, String paramString4);

  public abstract void recordDelegate(String paramString1, String paramString2, String paramString3, String paramString4);

  public abstract void cancel(String paramString1, String paramString2, String paramString3);

  public abstract void complete(String paramString1, String paramString2, String paramString3);
}