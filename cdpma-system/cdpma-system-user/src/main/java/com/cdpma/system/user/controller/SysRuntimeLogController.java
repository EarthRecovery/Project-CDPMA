package com.cdpma.system.user.controller;

import com.cdpma.common.core.web.controller.BaseController;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.core.web.page.TableDataInfo;
import com.cdpma.common.pojo.enums.Tag;
import com.cdpma.common.pojo.pojo.SysRuntimeLog;
import com.cdpma.common.security.annotation.RequiresTags;
import com.cdpma.system.user.service.ISysRuntimeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/log-runtime")
public class SysRuntimeLogController extends BaseController {
    @Autowired
    private ISysRuntimeLogService runtimeLogService;

    /**
     * 查询运行日志列表
     */
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody SysRuntimeLog log) {
        startPage();
        List<SysRuntimeLog> list = runtimeLogService.selectRuntimeLogList(log);
        return getDataTable(list);
    }

    /**
     * 根据ID获取运行日志
     */
    @GetMapping("/{logId}")
    public AjaxResult getById(@PathVariable Long logId) {
        SysRuntimeLog log = runtimeLogService.selectRuntimeLogById(logId);
        return AjaxResult.success(log);
    }

    /**
     * 新增运行日志
     */
    @PostMapping
    public AjaxResult add(@RequestBody SysRuntimeLog log) {
        runtimeLogService.insertRuntimeLog(log);
        return AjaxResult.success("运行日志添加成功");
    }

    /**
     * 批量删除运行日志
     */
    @DeleteMapping("/{logIds}")
    @RequiresTags(value={Tag.ADMIN})
    public AjaxResult delete(@PathVariable Long[] logIds) {
        runtimeLogService.deleteRuntimeLogByIds(logIds);
        return AjaxResult.success("运行日志删除成功");
    }

    @GetMapping("/stat")
    public AjaxResult getRuntimeLogStat() {
        return AjaxResult.success(runtimeLogService.getRuntimeLogStat());
    }
}
