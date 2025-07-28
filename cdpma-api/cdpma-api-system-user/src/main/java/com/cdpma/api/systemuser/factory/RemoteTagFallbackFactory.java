package com.cdpma.api.systemuser.factory;

import com.cdpma.api.systemuser.RemoteTagService;
import com.cdpma.api.systemuser.RemoteUserService;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.pojo.pojo.SysOperatorTag;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteTagFallbackFactory implements FallbackFactory<RemoteTagService> {
    @Override
    public RemoteTagService create(Throwable throwable) {
        return new RemoteTagService() {

            @Override
            public AjaxResult getTagsByOperatorID(Long operatorId, String source) {
                return AjaxResult.error("远程调用失败，请检查系统配置或网络连接", throwable);
            }

            @Override
            public AjaxResult addTagToOperator(SysOperatorTag operatorTag, String source) {
                return AjaxResult.error("远程添加Tag失败，请检查系统配置或网络连接", throwable);
            }
        };
    }
}
