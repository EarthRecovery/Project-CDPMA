package com.cdpma.system.user.controller;

import com.cdpma.common.core.web.controller.BaseController;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.core.web.page.TableDataInfo;
import com.cdpma.common.pojo.pojo.SysLoginLogoutLog;
import com.cdpma.system.user.service.ISysLoginLogoutLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logio")
public class SysLoginLogoutLogController extends BaseController {

    @Autowired
    private ISysLoginLogoutLogService logService;

    /**
     * 查询日志详情
     */
    @GetMapping("/{logId}")
    public AjaxResult getLog(@PathVariable Long logId) {
        return AjaxResult.success(logService.selectLogById(logId));
    }

    /**
     * 查询日志列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysLoginLogoutLog log) {
        startPage();
        List<SysLoginLogoutLog> list = logService.selectLogList(log);
        return getDataTable(list);
    }

    /**
     * 添加日志（登录或登出行为）
     */
    @PostMapping
    public AjaxResult add(@RequestBody SysLoginLogoutLog log) {
        logService.insertLog(log);
        return AjaxResult.success("日志添加成功");
    }

    /**
     * 批量删除日志
     */
    @DeleteMapping("/{logIds}")
    public AjaxResult delete(@PathVariable Long[] logIds) {
        logService.deleteLogByIds(logIds);
        return AjaxResult.success("日志删除成功");
    }
}