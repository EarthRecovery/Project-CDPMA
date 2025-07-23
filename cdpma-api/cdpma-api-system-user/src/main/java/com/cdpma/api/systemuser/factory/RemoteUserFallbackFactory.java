package com.cdpma.api.systemuser.factory;

import com.cdpma.api.systemuser.RemoteUserService;
import com.cdpma.common.core.domain.R;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.pojo.pojo.SysUser;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserService> {

    @Override
    public RemoteUserService create(Throwable throwable)
    {
        return new RemoteUserService()
        {
            @Override
            public AjaxResult insertUser(SysUser sysUser, String source)
            {
                return AjaxResult.error("远程调用失败，无法添加用户信息。错误信息：" + throwable.getMessage());
            }
        };
    }
}
