package com.citsh.msg.service.impl;

import com.citsh.msg.dao.MsgInfoDao;
import com.citsh.msg.entity.MsgInfo;
import com.citsh.msg.service.MsgInfoService;
import com.citsh.notification.NotificationDTO;
import java.util.Date;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MsgInfoImpl
  implements MsgInfoService
{

  @Autowired
  private MsgInfoDao msgInfoDao;
  private String defaultSender = "";

  public String getType() {
    return "msg";
  }

  public void handle(NotificationDTO notificationDto, String tenantId) {
    if (!"userid".equals(notificationDto.getReceiverType())) {
      return;
    }
    MsgInfo msgInfo = new MsgInfo();
    msgInfo.setName(notificationDto.getSubject());
    msgInfo.setContent(notificationDto.getContent());
    msgInfo.setReceiverId(notificationDto.getReceiver());
    msgInfo.setCreateTime(new Date());
    msgInfo.setStatus(Integer.valueOf(0));
    msgInfo.setTenantId(tenantId);

    String humanTaskId = notificationDto.getData().get("humanTaskId").toString();
    msgInfo.setData(humanTaskId);

    if (StringUtils.isNotBlank(notificationDto.getSender()))
      msgInfo.setSenderId(notificationDto.getSender());
    else {
      msgInfo.setSenderId(this.defaultSender);
    }
    this.msgInfoDao.save(msgInfo);
  }

  public String getDefaultSender() {
    return this.defaultSender;
  }

  public void setDefaultSender(String defaultSender) {
    this.defaultSender = defaultSender;
  }
}