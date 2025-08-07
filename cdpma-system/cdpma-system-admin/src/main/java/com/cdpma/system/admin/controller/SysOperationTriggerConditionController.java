package com.cdpma.system.admin.controller;

import com.cdpma.common.core.web.controller.BaseController;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.pojo.pojo.SysOperationTriggerCondition;
import com.cdpma.system.admin.service.ISysOperationTriggerConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("operation-trigger-condition")
public class SysOperationTriggerConditionController extends BaseController {
    @Autowired
    private ISysOperationTriggerConditionService conditionService;

    @GetMapping("/{conditionId}")
    public AjaxResult getCondition(@PathVariable Long conditionId) {
        return AjaxResult.success(conditionService.selectConditionById(conditionId));
    }

    @PostMapping("/list")
    public AjaxResult list(@RequestBody SysOperationTriggerCondition condition) {
        List<SysOperationTriggerCondition> list = conditionService.selectConditionList(condition);
        return AjaxResult.success(list);
    }

    @GetMapping("/all")
    public AjaxResult getAllConditions() {
        List<SysOperationTriggerCondition> list = conditionService.selectAllCondition();
        return AjaxResult.success(list);
    }

    @PostMapping
    public AjaxResult add(@RequestBody SysOperationTriggerCondition condition) {
        conditionService.insertCondition(condition);
        return AjaxResult.success("新增触发条件成功");
    }

    @PutMapping
    public AjaxResult edit(@RequestBody SysOperationTriggerCondition condition) {
        conditionService.updateCondition(condition);
        return AjaxResult.success("修改触发条件成功");
    }

    @DeleteMapping("/{conditionIds}")
    public AjaxResult remove(@PathVariable Long[] conditionIds) {
        conditionService.deleteConditionByIds(conditionIds);
        return AjaxResult.success("删除触发条件成功");
    }
}
