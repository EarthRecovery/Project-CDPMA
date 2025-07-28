package com.cdpma.system.user.controller;

import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.log.annotation.Log;
import com.cdpma.common.log.enums.BusinessType;
import com.cdpma.common.pojo.enums.Tag;
import com.cdpma.common.pojo.pojo.SysGood;
import com.cdpma.common.pojo.pojo.SysGoodFullName;
import com.cdpma.common.security.annotation.RequiresTags;
import com.cdpma.system.user.service.ISysGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class SysGoodController {
    @Autowired
    private ISysGoodService goodService;

    @GetMapping("/{goodId}")
    public AjaxResult getGood(@PathVariable Long goodId) {
        return AjaxResult.success(goodService.selectGoodById(goodId));
    }

    @GetMapping("/list")
    public AjaxResult list(SysGood good) {
        List<SysGood> list = goodService.selectGoodList(good);
        return AjaxResult.success(list);
    }

    @GetMapping("/fullname/{goodId}")
    public AjaxResult getFullNameGood(@PathVariable Long goodId) {
        SysGoodFullName Fgood = goodService.getFullNameGood(goodId);
        return AjaxResult.success(Fgood);
    }

    @PostMapping
    @RequiresTags(value={Tag.ASSISTANT})
    @Log(title = "增加商品", businessType = BusinessType.INSERT)
    public AjaxResult add(@RequestBody SysGood good) {
        goodService.insertGood(good);
        return AjaxResult.success("新增商品成功");
    }

    @PutMapping
    @RequiresTags(value={Tag.ASSISTANT})
    @Log(title = "修改商品", businessType = BusinessType.UPDATE)
    public AjaxResult edit(@RequestBody SysGood good) {
        goodService.updateGood(good);
        return AjaxResult.success("修改商品成功");
    }

    @DeleteMapping("/{goodIds}")
    @RequiresTags(value={Tag.ASSISTANT})
    @Log(title = "删除商品", businessType = BusinessType.DELETE)
    public AjaxResult remove(@PathVariable Long[] goodIds) {
        goodService.deleteGoodByIds(goodIds);
        return AjaxResult.success("删除商品成功");
    }
}
