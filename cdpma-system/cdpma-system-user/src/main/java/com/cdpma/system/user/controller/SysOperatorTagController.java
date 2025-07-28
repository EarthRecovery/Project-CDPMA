package com.cdpma.system.user.controller;

import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.log.annotation.Log;
import com.cdpma.common.log.enums.BusinessType;
import com.cdpma.common.pojo.enums.Tag;
import com.cdpma.common.pojo.pojo.SysOperatorTag;
import com.cdpma.common.security.annotation.Logical;
import com.cdpma.common.security.annotation.RequiresTags;
import com.cdpma.system.user.service.ISysOperatorTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operator/tag")
public class SysOperatorTagController {

    @Autowired
    private ISysOperatorTagService operatorTagService;

    /**
     * 获取指定操作员的所有标签ID
     */
    @GetMapping("/{operatorId}")
    public AjaxResult getTagsByOperatorId(@PathVariable Long operatorId) {
        List<Long> tagIds = operatorTagService.selectTagIdsByOperatorId(operatorId);
        return AjaxResult.success(tagIds);
    }

    /**
     * 为操作员分配多个标签
     */
    @PostMapping("/assign")
    @RequiresTags(value={Tag.ADMIN})
    public AjaxResult assignTagsToOperator(@RequestBody List<SysOperatorTag> tagList) {
        if (tagList == null || tagList.isEmpty()) {
            return AjaxResult.error("标签列表不能为空");
        }

        Long operatorId = tagList.get(0).getOperatorId();
        operatorTagService.batchInsertOperatorTags(tagList);

        return AjaxResult.success("标签分配成功");
    }

    /**
     * 添加单条操作员标签关系
     */
    @PostMapping
    @RequiresTags(value={Tag.ADMIN})
    @Log(title = "为操作员添加标签", businessType = BusinessType.INSERT)
    public AjaxResult addOperatorTag(@RequestBody SysOperatorTag operatorTag) {
        operatorTagService.insertOperatorTag(operatorTag);
        return AjaxResult.success("标签关联添加成功");
    }

    /**
     * 删除指定操作员的所有标签
     */
    @DeleteMapping("/{operatorId}")
    @RequiresTags(value={Tag.ADMIN})
    public AjaxResult deleteTagsByOperator(@PathVariable Long operatorId) {
        operatorTagService.deleteTagsByOperatorId(operatorId);
        return AjaxResult.success("标签关联已清除");
    }
}