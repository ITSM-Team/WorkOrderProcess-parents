package com.citsh.enumitem;

public class HumanTaskStatus
{
  private static final String PENDING = "pending";
  private static final String ACTIVE = "active";
  private static final String COMLPETE = "complete";

  public static String getPending()
  {
    return "pending";
  }

  public static String getActive()
  {
    return "active";
  }

  public static String getComlpete()
  {
    return "complete";
  }
}