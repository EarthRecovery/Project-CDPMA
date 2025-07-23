package com.cdpma.api.systemuser.factory;

import com.cdpma.api.systemuser.RemoteLogService;
import com.cdpma.api.systemuser.RemoteOperatorService;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.pojo.pojo.SysLoginLogoutLog;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteLogFallbackFactory implements FallbackFactory<RemoteLogService> {
    @Override
    public RemoteLogService create(Throwable throwable) {
        return new RemoteLogService() {

            @Override
            public AjaxResult insertLog(SysLoginLogoutLog sysLoginLogoutLog, String source) {
                return AjaxResult.error("添加登陆登出日志:" + throwable.getMessage());
            }
        };
    }
}
