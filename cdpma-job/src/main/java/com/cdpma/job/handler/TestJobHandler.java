package com.cdpma.job.handler;

import com.cdpma.api.systemuser.RemoteNotificationService;
import com.cdpma.api.systemuser.RemoteOperatorService;
import com.cdpma.common.core.constant.SecurityConstants;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.pojo.enums.NotificationType;
import com.cdpma.common.pojo.pojo.SysNotification;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class TestJobHandler {

    @Autowired
    private RemoteOperatorService remoteOperatorService;

    @Autowired
    private RemoteNotificationService remoteNotificationService;

    @XxlJob("testJobHandler")
    public ReturnT<String> testJobHandler(String param){
        System.out.println("执行 XXL-JOB 任务，接收参数：" + param);
        return ReturnT.SUCCESS;
    }

    @XxlJob("SendMesssageToAllMembers")
    public void sendMessageToAllMembers(String param) throws Exception {
        // 这里可以添加发送消息的逻辑
        System.out.println("开始发送消息给所有成员，参数：" + param);
        AjaxResult result = remoteOperatorService.getAllUserId(SecurityConstants.INNER);
        List<Integer> userIds = (List<Integer>) result.get(AjaxResult.DATA_TAG);
        for(Integer userId : userIds) {
            // 构建消息内容
            String messageContent = "Hello User " + userId + ", this is a test message!";
            SysNotification sysNotification = new SysNotification();
            sysNotification.setReceiver(Long.valueOf(userId));
            sysNotification.setContent(messageContent);
            sysNotification.setSender(1L); // 假设发送者ID为1
            sysNotification.setNotificationType(NotificationType.USER); // 假设通知类型为用户通知
            sysNotification.setTimestamp(new Date());
            sysNotification.setSendTime(new Date());
            sysNotification.setOperatorId(1L);
            // 发送消息
            AjaxResult response = remoteNotificationService.insertNotification(sysNotification, SecurityConstants.INNER);
        }
    }
}
