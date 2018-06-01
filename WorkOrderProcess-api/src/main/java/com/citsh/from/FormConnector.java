package com.citsh.from;

import java.util.List;

import org.springframework.stereotype.Repository;
@Repository
public abstract interface FormConnector
{
  public abstract List<FormDTO> getAll(String paramString);

  public abstract FormDTO findForm(String code, String tenantId);
}