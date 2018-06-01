package com.citsh.listener.service.impl;

import com.citsh.base.service.TaskDefinitionService;
import com.citsh.humantask.DeadlineDTO;
import com.citsh.humantask.entity.TaskDeadline;
import com.citsh.humantask.entity.TaskInfo;
import com.citsh.humantask.service.TaskDeadlineService;
import com.citsh.id.IdGenerator;
import com.citsh.listener.service.ListenerService;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ListenerServiceDeadlineImpl
  implements ListenerService
{
  private Logger logger = LoggerFactory.getLogger(ListenerServiceDeadlineImpl.class);

  @Autowired
  private TaskDefinitionService taskDefinitionService;

  @Autowired
  private TaskDeadlineService taskDeadlineService;

  @Autowired
  private IdGenerator idGenerator;

  public void onCreate(TaskInfo taskInfo) { String taskDefinitionKey = taskInfo.getCode();
    String processDefinitionId = taskInfo.getProcessDefinitionId();
    List<DeadlineDTO> deadlines = this.taskDefinitionService.findDeadlines(taskDefinitionKey, processDefinitionId);
    try {
      for (DeadlineDTO deadline : deadlines) {
        String durationText = deadline.getDuration();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);

        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        Duration duration = datatypeFactory.newDuration(durationText);
        duration.addTo(calendar);
        Date deadlineTime = calendar.getTime();

        TaskDeadline taskDeadline = new TaskDeadline();
        taskDeadline.setTaskInfo(taskInfo);
        taskDeadline.setType(deadline.getType());
        taskDeadline.setDeadlineTime(deadlineTime);
        taskDeadline.setNotificationType(deadline.getNotificationType());
        taskDeadline.setNotificationTemplateCode(deadline.getNotificationTemplateCode());
        taskDeadline.setNotificationReceiver(deadline.getNotificationReceiver());
        taskDeadline.setId(this.idGenerator.generateId());

        this.taskDeadlineService.save(taskDeadline);
      }
    }
    catch (Exception e) {
      this.logger.error(e.getMessage(), e);
    }
  }

  public void onComplete(TaskInfo taskInfo)
  {
  }
}