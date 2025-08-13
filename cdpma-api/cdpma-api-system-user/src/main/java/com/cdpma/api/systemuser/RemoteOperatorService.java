package com.cdpma.api.systemuser;

import com.cdpma.api.systemuser.factory.RemoteOperatorFallbackFactory;
import com.cdpma.common.core.constant.SecurityConstants;
import com.cdpma.common.core.constant.ServiceNameConstants;
import com.cdpma.common.core.domain.R;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.pojo.pojo.SysOperator;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(contextId = "remoteOperatorService",
        value = ServiceNameConstants.SYSTEM_USER_SERVICE,
        fallbackFactory = RemoteOperatorFallbackFactory.class)
public interface RemoteOperatorService {
    /**
     * 插入Operator信息
     *
     * @param sysOperator 操作员信息
     * @param source 请求来源
     * @return 结果
     */
    @PostMapping("/operator")
    public AjaxResult insertOperator(@RequestBody SysOperator sysOperator,
                                     @RequestHeader(SecurityConstants.FROM_SOURCE) String source);


    /**
     * 根据Operator名查询Operator信息
     */
    @GetMapping("/operator/name/{operatorName}")
    public AjaxResult getOperatorByName(@PathVariable("operatorName") String operatorName,
                                            @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 根据Operator邮箱查询Operator信息
     */
    @GetMapping("/operator/email/{operatorEmail}")
    public AjaxResult getOperatorByEmail(@PathVariable("operatorEmail") String operatorEmail,
                                            @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 根据Operator手机号查询Operator信息
     */
    @GetMapping("/operator/phone/{operatorPhone}")
    public AjaxResult getOperatorByPhone(@PathVariable("operatorPhone") String operatorPhone,
                                            @RequestHeader(SecurityConstants.FROM_SOURCE) String source);


    @GetMapping("/operator/statData")
    public AjaxResult getOperatorStatData(@RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @GetMapping("/operator/allUserId")
    public AjaxResult getAllUserId(@RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
