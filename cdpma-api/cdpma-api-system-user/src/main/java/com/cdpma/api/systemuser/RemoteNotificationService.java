package com.cdpma.api.systemuser;

import com.cdpma.api.systemuser.factory.RemoteLogFallbackFactory;
import com.cdpma.api.systemuser.factory.RemoteNotificationFallbackFactory;
import com.cdpma.common.core.constant.SecurityConstants;
import com.cdpma.common.core.constant.ServiceNameConstants;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.pojo.pojo.SysNotification;
import com.cdpma.common.pojo.pojo.SysOperator;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(contextId = "remoteNotificationService",
        value = ServiceNameConstants.SYSTEM_ADMIN_SERVICE,
        fallbackFactory = RemoteNotificationFallbackFactory.class)
public interface RemoteNotificationService {

    /**
     * 发送通知
     */
    @PostMapping("/notification")
    public AjaxResult insertNotification(@RequestBody SysNotification sysNotification,
                                         @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
