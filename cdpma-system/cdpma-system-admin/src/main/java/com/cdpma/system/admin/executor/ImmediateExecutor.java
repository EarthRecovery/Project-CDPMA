package com.cdpma.system.admin.executor;

import com.cdpma.api.systemuser.RemoteCouponsService;
import com.cdpma.api.systemuser.RemoteNotificationService;
import com.cdpma.common.core.constant.SecurityConstants;
import com.cdpma.common.pojo.bo.SysNotificationBO;
import com.cdpma.common.pojo.enums.NotificationType;
import com.cdpma.common.pojo.pojo.SysCoupons;
import com.cdpma.common.pojo.pojo.SysNotification;
import com.cdpma.common.pojo.pojo.SysUser;
import com.cdpma.common.pojo.pojo.SysUserAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class ImmediateExecutor {

    @Autowired
    private RemoteNotificationService remoteNotificationService;

    @Autowired
    private RemoteCouponsService remoteCouponsService;

    public void sendNotification(Object args, SysUserAction sysUserAction) {
        SysNotification sysNotification = new SysNotification();
        sysNotification.setTimestamp(new Date());
        sysNotification.setSendTime(new Date());
        sysNotification.setNotificationType(NotificationType.USER);
        Long operatorId = sysUserAction.getOperatorId();
        sysNotification.setOperatorId(operatorId);
        sysNotification.setSender(operatorId);
        sysNotification.setContent(sysUserAction.getActionType()  + " 已经记录！");
        sysNotification.setReceiver(operatorId);
        remoteNotificationService.insertNotification(sysNotification, SecurityConstants.INNER);
    }

    public void sendCoupons(Object args, SysUserAction sysUserAction) {
        SysCoupons sysCoupons = new SysCoupons();
        sysCoupons.setExpirationDuration(30); // 设置优惠券有效期为30天
        sysCoupons.setCouponName("特殊99折优惠券");
        sysCoupons.setIssuanceTime(new Date());
        sysCoupons.setIssued(true);
        sysCoupons.setIssuanceTarget(sysUserAction.getOperatorId());
        sysCoupons.setCreatedBy(1L);
        sysCoupons.setCreatedAt(new Date());
        sysCoupons.setDiscountEffect("0.99"); // 设置折扣效果为99%
        remoteCouponsService.setCoupons(sysCoupons, SecurityConstants.INNER);
    }
}
