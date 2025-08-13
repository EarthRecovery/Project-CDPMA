package com.cdpma.system.admin.executor;

import com.cdpma.api.systemuser.RemoteOrderService;
import com.cdpma.api.systemuser.RemoteUserService;
import com.cdpma.common.core.constant.SecurityConstants;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.pojo.pojo.SysOrder;
import com.cdpma.common.pojo.pojo.SysUser;
import com.cdpma.common.pojo.pojo.SysUserAction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class UserExecutor {

    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private RemoteOrderService remoteOrderService;

    public void addScore(Object args, SysUserAction sysUserAction, String param){

        SysUser sysUser;
        ObjectMapper mapper = new ObjectMapper();
        Long operatorId = sysUserAction.getOperatorId();
        AjaxResult result = remoteUserService.getUserByOperatorId(SecurityConstants.INNER, operatorId);
        if(result.get(AjaxResult.DATA_TAG) == null){
            // 如果不是用户，不增加，直接跳过
            return;
        }else{
            sysUser = mapper.convertValue(result.get(AjaxResult.DATA_TAG).toString(), SysUser.class);
        }

        //根据订单号获取订单
        SysOrder sysOrder = (SysOrder) remoteOrderService.getOrderById(
                sysUserAction.getOrderId(), SecurityConstants.INNER).get(AjaxResult.DATA_TAG);

        // 计算积分
        double score = BigDecimal
                .valueOf(sysOrder.getQuantity())
                .multiply(sysOrder.getUnitPrice())
                .doubleValue(); // 假设每消费1元积1分

        int points = (int) score;
        // 更新用户积分
        sysUser.setPoints(sysUser.getPoints() + points);
        remoteUserService.updateUser(sysUser, SecurityConstants.INNER);
    }
}
