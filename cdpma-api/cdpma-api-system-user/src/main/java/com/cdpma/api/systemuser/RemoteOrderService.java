package com.cdpma.api.systemuser;

import com.cdpma.api.systemuser.factory.RemoteLogFallbackFactory;
import com.cdpma.api.systemuser.factory.RemoteOrderFallbackFactory;
import com.cdpma.common.core.constant.SecurityConstants;
import com.cdpma.common.core.constant.ServiceNameConstants;
import com.cdpma.common.core.web.domain.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(contextId = "remoteOrderService",
        value = ServiceNameConstants.SYSTEM_USER_SERVICE,
        fallbackFactory = RemoteOrderFallbackFactory.class)
public interface RemoteOrderService {

    @GetMapping("/order/{orderId}")
    public AjaxResult getOrderById(@PathVariable("orderId") Long orderId,
                                   @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
