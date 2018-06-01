package com.citsh.enumitem;

public class DelegateStatus
{
  private static final String PENDING = "pending";
  private static final String PINDINGCREATE = "pendingCreate";

  public static String getPending()
  {
    return "pending";
  }

  public static String getPindingcreate()
  {
    return "pendingCreate";
  }
}