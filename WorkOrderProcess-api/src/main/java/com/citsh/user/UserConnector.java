package com.citsh.user;

import com.citsh.page.Page;
import java.util.Map;

public abstract interface UserConnector
{
  public abstract UserDTO findById(String paramString);

  public abstract UserDTO findByUsername(String paramString1, String paramString2);

  public abstract UserDTO findByRef(String paramString1, String paramString2);

  public abstract Page pagedQuery(String paramString, Page paramPage, Map<String, Object> paramMap);

  public abstract UserDTO findByNickName(String paramString1, String paramString2);
}