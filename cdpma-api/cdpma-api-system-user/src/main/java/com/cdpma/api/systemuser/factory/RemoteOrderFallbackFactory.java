package com.cdpma.api.systemuser.factory;

import com.cdpma.api.systemuser.RemoteOrderService;
import com.cdpma.api.systemuser.RemoteTagService;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.pojo.pojo.SysOperatorTag;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteOrderFallbackFactory implements FallbackFactory<RemoteOrderService> {
    @Override
    public RemoteOrderService create(Throwable throwable) {
        return new RemoteOrderService() {

            @Override
            public AjaxResult getOrderById(Long orderId, String source) {
                return AjaxResult.error("Fallback: Unable to retrieve order with ID " + orderId + ". Please try again later.");
            }
        };
    }
}
