package com.citsh.auto;

public class MockCurrentUserHolder implements CurrentUserHolder
{
  public String getUserId()
  {
    return "1";
  }

  public String getUsername() {
    return "lingo";
  }
}