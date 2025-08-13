package com.cdpma.api.systemuser.factory;

import com.cdpma.api.systemuser.RemoteOperatorService;
import com.cdpma.common.core.domain.R;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.pojo.pojo.SysOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteOperatorFallbackFactory implements FallbackFactory<RemoteOperatorService> {

    @Override
    public RemoteOperatorService create(Throwable throwable)
    {
        return new RemoteOperatorService()
        {
            @Override
            public AjaxResult insertOperator(SysOperator sysUser, String source)
            {
                return AjaxResult.error("注册operator失败:" + throwable.getMessage());
            }

            @Override
            public AjaxResult getOperatorByName(String operatorName, String source) {
                return AjaxResult.error("获取用户:" + throwable.getMessage());
            }

            @Override
            public AjaxResult getOperatorByEmail(String operatorEmail, String source) {
                return AjaxResult.error("获取用户:" + throwable.getMessage());
            }

            @Override
            public AjaxResult getOperatorByPhone(String operatorPhone, String source) {
                return AjaxResult.error("获取用户:" + throwable.getMessage());
            }

            @Override
            public AjaxResult getOperatorStatData(String source) {
                return AjaxResult.error("获取操作员统计数据失败:" + throwable.getMessage());
            }

            @Override
            public AjaxResult getAllUserId(String source) {
                return AjaxResult.error("获取所有用户ID失败:" + throwable.getMessage());
            }

        };
    }
}
