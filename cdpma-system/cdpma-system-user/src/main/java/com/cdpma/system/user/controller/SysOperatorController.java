package com.cdpma.system.user.controller;

import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.log.annotation.Log;
import com.cdpma.common.pojo.pojo.SysOperator;
import com.cdpma.system.user.service.impl.SysOperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cdpma.common.log.enums.BusinessType;

import javax.validation.Valid;

@RestController
@RequestMapping("/operator")
public class SysOperatorController {

    @Autowired
    private SysOperatorService sysOperatorService;

    /**
     * 根据ID获取操作员信息
     */
    @GetMapping("/{operatorId}")
    @Log(title = "获取操作员", businessType = BusinessType.QUERY)
    public AjaxResult getOperator(@PathVariable Long operatorId) {
        SysOperator operator = sysOperatorService.selectOperatorById(operatorId);
        return AjaxResult.success(operator);
    }

    @PostMapping
    @Log(title = "添加操作员", businessType = BusinessType.INSERT)
    public AjaxResult addOperator(@Valid @RequestBody SysOperator operator) {
        Long OperatorId = sysOperatorService.insertOperator(operator);
        return AjaxResult.success("操作员添加成功").put("operatorId", OperatorId);
    }

    @GetMapping("/name/{operatorName}")
    public AjaxResult getOperatorByName(@PathVariable String operatorName) {
        SysOperator operator = sysOperatorService.selectOperatorByOperatorName(operatorName);
        return AjaxResult.success(operator);
    }

    @GetMapping("/email/{operatorEmail}")
    public AjaxResult getOperatorByEmail(@PathVariable String operatorEmail) {
        SysOperator operator = sysOperatorService.selectOperatorByOperatorEmail(operatorEmail);
        return AjaxResult.success(operator);
    }

    @GetMapping("/phone/{operatorPhone}")
    public AjaxResult getOperatorByPhone(@PathVariable String operatorPhone) {
        SysOperator operator = sysOperatorService.selectOperatorByOperatorPhone(operatorPhone);
        return AjaxResult.success(operator);
    }


}
