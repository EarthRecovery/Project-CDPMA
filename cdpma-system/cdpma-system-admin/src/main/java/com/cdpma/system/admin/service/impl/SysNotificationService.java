package com.cdpma.system.admin.service.impl;

import com.cdpma.common.pojo.pojo.SysNotification;
import com.cdpma.system.admin.mapper.SysNotificationMapper;
import com.cdpma.system.admin.service.ISysNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysNotificationService implements ISysNotificationService {
    @Autowired
    private SysNotificationMapper notificationMapper;

    @Override
    public SysNotification selectNotificationById(Long notificationId) {
        return notificationMapper.selectNotificationById(notificationId);
    }

    @Override
    public List<SysNotification> selectNotificationList(SysNotification notification) {
        return notificationMapper.selectNotificationList(notification);
    }

    @Override
    public int insertNotification(SysNotification notification) {
        return notificationMapper.insertNotification(notification);
    }

    @Override
    public int updateNotification(SysNotification notification) {
        return notificationMapper.updateNotification(notification);
    }

    @Override
    public int deleteNotificationByIds(Long[] notificationIds) {
        return notificationMapper.deleteNotificationByIds(notificationIds);
    }
}
