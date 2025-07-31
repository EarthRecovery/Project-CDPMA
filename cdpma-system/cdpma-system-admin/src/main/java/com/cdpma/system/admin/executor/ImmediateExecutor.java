package com.cdpma.system.admin.executor;

import com.cdpma.api.systemuser.RemoteNotificationService;
import com.cdpma.common.core.constant.SecurityConstants;
import com.cdpma.common.pojo.bo.SysNotificationBO;
import com.cdpma.common.pojo.enums.NotificationType;
import com.cdpma.common.pojo.pojo.SysNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class ImmediateExecutor {

    @Autowired
    private RemoteNotificationService remoteNotificationService;

    public void sendNotification(Object args) {
        List<Object> largs = (List<Object>) args;
        HashMap<String, Object> argMap = (HashMap<String, Object>) largs.get(0);
        SysNotification sysNotification = new SysNotification();
        sysNotification.setTimestamp(new Date());
        sysNotification.setSendTime(new Date());
        sysNotification.setNotificationType(NotificationType.USER);
        Long operatorId = 0L;
        if (argMap.get("operatorId") instanceof Integer) {
            operatorId = ((Integer) argMap.get("operatorId")).longValue();
        }
        sysNotification.setOperatorId(operatorId);
        sysNotification.setSender(operatorId);
        sysNotification.setContent("点赞已经记录！");
        sysNotification.setReceiver(operatorId);
        remoteNotificationService.insertNotification(sysNotification, SecurityConstants.INNER);
    }
}
