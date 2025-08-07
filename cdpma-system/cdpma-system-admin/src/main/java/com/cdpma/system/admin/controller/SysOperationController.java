package com.cdpma.system.admin.controller;

import com.cdpma.common.core.web.controller.BaseController;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.core.web.page.TableDataInfo;
import com.cdpma.common.pojo.pojo.SysOperation;
import com.cdpma.system.admin.service.ISysOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operation")
public class SysOperationController extends BaseController {
    @Autowired
    private ISysOperationService operationService;

    @GetMapping("/{operationId}")
    public AjaxResult getOperation(@PathVariable Long operationId) {
        return AjaxResult.success(operationService.selectOperationById(operationId));
    }

    @PostMapping("/list")
    public TableDataInfo list(@RequestBody SysOperation operation) {
        startPage();
        List<SysOperation> list = operationService.selectOperationList(operation);
        return getDataTable(list);
    }

    @PostMapping
    public AjaxResult add(@RequestBody SysOperation operation) {
        operationService.insertOperation(operation);
        return AjaxResult.success("新增操作成功");
    }

    @PutMapping
    public AjaxResult edit(@RequestBody SysOperation operation) {
        operationService.updateOperation(operation);
        return AjaxResult.success("修改操作成功");
    }

    @DeleteMapping("/{operationIds}")
    public AjaxResult remove(@PathVariable Long[] operationIds) {
        operationService.deleteOperationByIds(operationIds);
        return AjaxResult.success("删除操作成功");
    }
}
