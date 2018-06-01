package com.citsh.export;

import com.citsh.util.ServletUtils;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CsvExportor
  implements Exportor
{
  private String encoding = "GBK";

  public void export(HttpServletRequest request, HttpServletResponse response, TableModel tableModel)
  {
    StringBuilder buff = new StringBuilder();

    for (int i = 0; i < tableModel.getHeaderCount(); i++) {
      buff.append(tableModel.getHeader(i));

      if (i != tableModel.getHeaderCount() - 1) {
        buff.append(",");
      }
    }

    buff.append("\n");

    for (int i = 0; i < tableModel.getDataCount(); i++) {
      for (int j = 0; j < tableModel.getHeaderCount(); j++) {
        buff.append(tableModel.getValue(i, j));

        if (j != tableModel.getHeaderCount() - 1) {
          buff.append(",");
        }
      }

      buff.append("\n");
    }

    response.setContentType("application/octet-stream");
    try {
      ServletUtils.setFileDownloadHeader(request, response, new StringBuilder().append(tableModel.getName()).append(".csv").toString());
      response.getOutputStream().write(buff.toString().getBytes(this.encoding));
      response.getOutputStream().flush();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setEncoding(String encoding)
  {
    this.encoding = encoding;
  }
}