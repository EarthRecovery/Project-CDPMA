package com.cdpma.api.systemuser;

import com.cdpma.api.systemuser.factory.RemoteOperatorFallbackFactory;
import com.cdpma.api.systemuser.factory.RemoteUserFallbackFactory;
import com.cdpma.common.core.constant.SecurityConstants;
import com.cdpma.common.core.constant.ServiceNameConstants;
import com.cdpma.common.core.domain.R;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.pojo.pojo.SysOperator;
import com.cdpma.common.pojo.pojo.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(contextId = "remoteUserService",
        value = ServiceNameConstants.SYSTEM_USER_SERVICE,
        fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteUserService {

    /**
     * 插入User信息
     *
     * @param sysUser 操作员信息
     * @param source 请求来源
     * @return 结果
     */
    @PostMapping("/user")
    public AjaxResult insertUser(@RequestBody SysUser sysUser,
                                 @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
