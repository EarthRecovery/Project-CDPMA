package com.cdpma.system.user.controller;

import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.pojo.pojo.SysShoppingCartRecord;
import com.cdpma.system.user.service.ISysShoppingCartRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart-record")
public class SysShopingCartRecordController {

    @Autowired
    private ISysShoppingCartRecordService recordService;

    @GetMapping("/{recordId}")
    public AjaxResult getInfo(@PathVariable Long recordId) {
        return AjaxResult.success(recordService.selectRecordById(recordId));
    }

    @GetMapping("/list")
    public AjaxResult list(SysShoppingCartRecord record) {
        List<SysShoppingCartRecord> list = recordService.selectRecordList(record);
        return AjaxResult.success(list);
    }

    @PostMapping
    public AjaxResult add(@RequestBody SysShoppingCartRecord record) {
        recordService.insertRecord(record);
        return AjaxResult.success("新增购物车记录成功");
    }

    @PutMapping
    public AjaxResult edit(@RequestBody SysShoppingCartRecord record) {
        recordService.updateRecord(record);
        return AjaxResult.success("修改购物车记录成功");
    }

    @DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds) {
        recordService.deleteRecordByIds(recordIds);
        return AjaxResult.success("删除购物车记录成功");
    }
}
