package com.cdpma.system.user.controller;

import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.log.annotation.Log;
import com.cdpma.common.log.enums.BusinessType;
import com.cdpma.common.pojo.enums.Tag;
import com.cdpma.common.pojo.pojo.SysGoodCategory;
import com.cdpma.common.security.annotation.RequiresTags;
import com.cdpma.system.user.service.ISysGoodCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/good-category")
public class SysGoodCategoryController {

    @Autowired
    private ISysGoodCategoryService categoryService;

    @GetMapping("/{categoryId}")
    public AjaxResult getCategory(@PathVariable Long categoryId) {
        return AjaxResult.success(categoryService.selectCategoryById(categoryId));
    }

    @GetMapping("/list")
    public AjaxResult list(SysGoodCategory category) {
        List<SysGoodCategory> list = categoryService.selectCategoryList(category);
        return AjaxResult.success(list);
    }

    @PostMapping
    @RequiresTags(value={Tag.ASSISTANT})
    @Log(title = "增加商品种类", businessType = BusinessType.INSERT)
    public AjaxResult add(@RequestBody SysGoodCategory category) {
        categoryService.insertCategory(category);
        return AjaxResult.success("新增商品种类成功");
    }

    @PutMapping
    @RequiresTags(value={Tag.ASSISTANT})
    @Log(title = "修改商品种类", businessType = BusinessType.UPDATE)
    public AjaxResult edit(@RequestBody SysGoodCategory category) {
        categoryService.updateCategory(category);
        return AjaxResult.success("修改商品种类成功");
    }

    @DeleteMapping("/{categoryIds}")
    @RequiresTags(value={Tag.ASSISTANT})
    @Log(title = "删除商品种类", businessType = BusinessType.DELETE)
    public AjaxResult remove(@PathVariable Long[] categoryIds) {
        categoryService.deleteCategoryByIds(categoryIds);
        return AjaxResult.success("删除商品种类成功");
    }
}
