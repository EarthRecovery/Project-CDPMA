package com.cdpma.api.systemuser;

import com.cdpma.api.systemuser.factory.RemoteLogFallbackFactory;
import com.cdpma.api.systemuser.factory.RemoteOperatorFallbackFactory;
import com.cdpma.common.core.constant.SecurityConstants;
import com.cdpma.common.core.constant.ServiceNameConstants;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.pojo.pojo.SysLoginLogoutLog;
import com.cdpma.common.pojo.pojo.SysOperator;
import com.cdpma.common.pojo.pojo.SysRuntimeLog;
import com.cdpma.common.pojo.pojo.SysUserAction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(contextId = "remoteLogService",
        value = ServiceNameConstants.SYSTEM_USER_SERVICE,
        fallbackFactory = RemoteLogFallbackFactory.class)
public interface RemoteLogService {

    /**
     * 保存登陆登出操作日志
     *
     * @param sysLoginLogoutLog 操作日志实体
     * @param source 请求来源
     * @return 结果
     */
    @PostMapping("/logio")
    public AjaxResult insertLog(@RequestBody SysLoginLogoutLog sysLoginLogoutLog,
                                @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @PostMapping("/log-runtime")
    public AjaxResult insertOperation(@RequestBody SysRuntimeLog sysRuntimeLog,
                                      @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @PostMapping("/user-action")
    public AjaxResult insertUserAction(@RequestBody SysUserAction sysUserAction,
                                      @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
