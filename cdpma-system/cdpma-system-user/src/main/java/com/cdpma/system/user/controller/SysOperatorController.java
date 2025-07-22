package com.cdpma.system.user.controller;

import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.pojo.pojo.SysOperator;
import com.cdpma.system.user.service.ISysOpratorService;
import com.cdpma.system.user.service.impl.SysOperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/operator")
public class SysOperatorController {

    @Autowired
    private SysOperatorService sysOperatorService;

    /**
     * 根据ID获取操作员信息
     */
    @GetMapping("/{operatorId}")
    public AjaxResult getOperator(@PathVariable Long operatorId) {
        SysOperator operator = sysOperatorService.selectOperatorById(operatorId);
        return AjaxResult.success(operator);
    }

    @PostMapping
    public AjaxResult addOperator(@Validated @RequestBody SysOperator operator) {
        sysOperatorService.insertOperator(operator);
        return AjaxResult.success("操作员添加成功");
    }

}
