package com.cdpma.system.user.controller;

import com.cdpma.common.core.constant.SecurityConstants;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.pojo.pojo.SysLikeRecord;
import com.cdpma.common.security.utils.SecurityUtils;
import com.cdpma.system.user.service.ISysLikeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/like")
public class SysLikeRecordController {

    @Autowired
    private ISysLikeRecordService likeRecordService;

    @GetMapping("/{recordId}")
    public AjaxResult getInfo(@PathVariable Long recordId) {
        return AjaxResult.success(likeRecordService.selectLikeRecordById(recordId));
    }

    @GetMapping("/list")
    public AjaxResult list(SysLikeRecord record) {
        List<SysLikeRecord> list = likeRecordService.selectLikeRecordList(record);
        return AjaxResult.success(list);
    }

    @PostMapping
    public AjaxResult add(@RequestBody SysLikeRecord record) {
        record.setOperatorId(SecurityUtils.getOperatorId());
        record.setCreatedAt(new Date());
        record.setDeleted(false);
        likeRecordService.insertLikeRecord(record);
        return AjaxResult.success("新增点赞记录成功");
    }

    @PutMapping
    public AjaxResult edit(@RequestBody SysLikeRecord record) {
        likeRecordService.updateLikeRecord(record);
        return AjaxResult.success("修改点赞记录成功");
    }

    @DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds) {
        likeRecordService.deleteLikeRecordByIds(recordIds);
        return AjaxResult.success("删除点赞记录成功");
    }
}
