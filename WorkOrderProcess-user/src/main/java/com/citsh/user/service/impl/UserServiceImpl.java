package com.citsh.user.service.impl;

import com.citsh.page.Page;
import com.citsh.user.UserDTO;
import com.citsh.user.service.UserService;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl
  implements UserService
{
  public UserDTO findById(String id)
  {
    return null;
  }

  public UserDTO findByUsername(String username, String userRepoRef)
  {
    return null;
  }

  public UserDTO findByRef(String ref, String userRepoRef)
  {
    return null;
  }

  public Page pagedQuery(String userRepoRef, Page page, Map<String, Object> parameters)
  {
    return null;
  }

  public UserDTO findByNickName(String nickName, String userRepoRef)
  {
    return null;
  }
}