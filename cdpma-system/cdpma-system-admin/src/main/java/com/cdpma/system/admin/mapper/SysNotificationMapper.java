package com.cdpma.system.admin.mapper;

import com.cdpma.common.pojo.pojo.SysNotification;

import java.util.List;

public interface SysNotificationMapper {

    SysNotification selectNotificationById(Long notificationId);

    List<SysNotification> selectNotificationList(SysNotification notification);

    int insertNotification(SysNotification notification);

    int updateNotification(SysNotification notification);

    int deleteNotificationByIds(Long[] notificationIds);
}
