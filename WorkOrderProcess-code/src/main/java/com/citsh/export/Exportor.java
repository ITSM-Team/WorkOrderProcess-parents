package com.citsh.export;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Exportor {
	void export(HttpServletRequest request, HttpServletResponse response, TableModel tableModel);
}
