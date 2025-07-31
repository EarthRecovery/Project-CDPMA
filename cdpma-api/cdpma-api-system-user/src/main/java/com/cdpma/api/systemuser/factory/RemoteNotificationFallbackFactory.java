package com.cdpma.api.systemuser.factory;

import com.cdpma.api.systemuser.RemoteLogService;
import com.cdpma.api.systemuser.RemoteNotificationService;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.pojo.pojo.SysNotification;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteNotificationFallbackFactory implements FallbackFactory<RemoteNotificationService> {
    @Override
    public RemoteNotificationService create(Throwable cause) {
        return new RemoteNotificationService() {

            @Override
            public AjaxResult insertNotification(SysNotification sysNotification, String source) {
                return AjaxResult.error("插入评论失败: " + cause.getMessage());
            }
        };
    }
}
