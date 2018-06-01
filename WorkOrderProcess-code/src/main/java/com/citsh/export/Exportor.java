package com.citsh.export;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract interface Exportor
{
  public abstract void export(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, TableModel paramTableModel);
}