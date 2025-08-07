package com.cdpma.system.admin.controller;

import com.cdpma.common.core.web.controller.BaseController;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.core.web.page.TableDataInfo;
import com.cdpma.common.pojo.pojo.SysNotification;
import com.cdpma.system.admin.service.ISysNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class SysNotificationController extends BaseController {
    @Autowired
    private ISysNotificationService notificationService;

    @GetMapping("/{notificationId}")
    public AjaxResult getNotification(@PathVariable Long notificationId) {
        return AjaxResult.success(notificationService.selectNotificationById(notificationId));
    }

    @PostMapping("/list")
    public TableDataInfo list(@RequestBody SysNotification notification) {
        startPage();
        List<SysNotification> list = notificationService.selectNotificationList(notification);
        return getDataTable(list);
    }

    @PostMapping
    public AjaxResult add(@RequestBody SysNotification notification) {
        notificationService.insertNotification(notification);
        return AjaxResult.success("新增提示成功");
    }

    @PutMapping
    public AjaxResult edit(@RequestBody SysNotification notification) {
        notificationService.updateNotification(notification);
        return AjaxResult.success("修改提示成功");
    }

    @DeleteMapping("/{notificationIds}")
    public AjaxResult remove(@PathVariable Long[] notificationIds) {
        notificationService.deleteNotificationByIds(notificationIds);
        return AjaxResult.success("删除提示成功");
    }
}
