package com.cdpma.api.systemuser;

import com.cdpma.api.systemuser.factory.RemoteLogFallbackFactory;
import com.cdpma.common.core.constant.SecurityConstants;
import com.cdpma.common.core.constant.ServiceNameConstants;
import com.cdpma.common.pojo.pojo.SysCoupons;
import com.cdpma.common.pojo.pojo.SysLoginLogoutLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(contextId = "remoteCouponsService",
        value = ServiceNameConstants.SYSTEM_USER_SERVICE,
        fallbackFactory = RemoteLogFallbackFactory.class)
public interface RemoteCouponsService {

    @PostMapping("/coupons")
    void setCoupons(@RequestBody SysCoupons sysCoupons,
                    @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
