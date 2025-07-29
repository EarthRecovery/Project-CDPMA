package com.cdpma.api.systemuser.factory;

import com.cdpma.api.systemuser.RemoteLogService;
import com.cdpma.api.systemuser.RemoteOperatorService;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.pojo.pojo.SysLoginLogoutLog;
import com.cdpma.common.pojo.pojo.SysOperator;
import com.cdpma.common.pojo.pojo.SysRuntimeLog;
import com.cdpma.common.pojo.pojo.SysUserAction;
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

            @Override
            public AjaxResult insertOperation(SysRuntimeLog sysRuntimeLog, String source) {
                return AjaxResult.error("添加运行时日志:" + throwable.getMessage());
            }

            @Override
            public AjaxResult insertUserAction(SysUserAction sysUserAction, String source) {
                return AjaxResult.error("添加用户行为日志: " + throwable.getMessage());
            }

        };
    }
}
