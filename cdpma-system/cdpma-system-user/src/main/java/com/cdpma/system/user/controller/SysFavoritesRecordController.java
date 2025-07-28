package com.cdpma.system.user.controller;

import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.pojo.pojo.SysFavoritesRecord;
import com.cdpma.common.security.utils.SecurityUtils;
import com.cdpma.system.user.service.ISysFavoritesRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/favorites")
public class SysFavoritesRecordController {
    @Autowired
    private ISysFavoritesRecordService favoritesRecordService;

    @GetMapping("/{recordId}")
    public AjaxResult getInfo(@PathVariable Long recordId) {
        return AjaxResult.success(favoritesRecordService.selectFavoritesRecordById(recordId));
    }

    @GetMapping("/list")
    public AjaxResult list(SysFavoritesRecord record) {
        List<SysFavoritesRecord> list = favoritesRecordService.selectFavoritesRecordList(record);
        return AjaxResult.success(list);
    }

    @PostMapping
    public AjaxResult add(@RequestBody SysFavoritesRecord record) {
        record.setOperatorId(SecurityUtils.getOperatorId());
        record.setCreatedAt(new Date());
        record.setDeleted(false);
        favoritesRecordService.insertFavoritesRecord(record);
        return AjaxResult.success("新增收藏记录成功");
    }

    @PutMapping
    public AjaxResult edit(@RequestBody SysFavoritesRecord record) {
        favoritesRecordService.updateFavoritesRecord(record);
        return AjaxResult.success("修改收藏记录成功");
    }

    @DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds) {
        favoritesRecordService.deleteFavoritesRecordByIds(recordIds);
        return AjaxResult.success("删除收藏记录成功");
    }
}
