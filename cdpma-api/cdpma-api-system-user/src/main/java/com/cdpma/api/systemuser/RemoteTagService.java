package com.cdpma.api.systemuser;

import com.cdpma.api.systemuser.factory.RemoteTagFallbackFactory;
import com.cdpma.api.systemuser.factory.RemoteUserFallbackFactory;
import com.cdpma.common.core.constant.SecurityConstants;
import com.cdpma.common.core.constant.ServiceNameConstants;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.pojo.pojo.SysOperator;
import com.cdpma.common.pojo.pojo.SysOperatorTag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(contextId = "remoteTagService",
        value = ServiceNameConstants.SYSTEM_USER_SERVICE,
        fallbackFactory = RemoteTagFallbackFactory.class)
public interface RemoteTagService {
    /**
     * 根据operatorId查询tags
     */
    @GetMapping("/operator-tag/{operatorId}")
    public AjaxResult getTagsByOperatorID(@PathVariable("operatorId") Long operatorId,
                                     @RequestHeader(SecurityConstants.FROM_SOURCE) String source);


    /**
     * 为特定的operatorId添加tag
     */
    @PostMapping("/operator-tag")
    public AjaxResult addTagToOperator(@RequestBody SysOperatorTag operatorTag,
                                   @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

}
