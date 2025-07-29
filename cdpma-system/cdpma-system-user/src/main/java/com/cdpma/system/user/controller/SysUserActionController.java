package com.cdpma.system.user.controller;

import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.pojo.pojo.SysUserAction;
import com.cdpma.system.user.service.ISysUserActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-action")
public class SysUserActionController {


    @Autowired
    private ISysUserActionService userActionService;

    @GetMapping("/{actionId}")
    public AjaxResult getInfo(@PathVariable Long actionId) {
        return AjaxResult.success(userActionService.selectUserActionById(actionId));
    }

    @GetMapping("/list")
    public AjaxResult list(SysUserAction userAction) {
        List<SysUserAction> list = userActionService.selectUserActionList(userAction);
        return AjaxResult.success(list);
    }

    @PostMapping
    public AjaxResult add(@RequestBody SysUserAction userAction) {
        userActionService.insertUserAction(userAction);
        return AjaxResult.success("新增用户行为成功");
    }

    @PutMapping
    public AjaxResult edit(@RequestBody SysUserAction userAction) {
        userActionService.updateUserAction(userAction);
        return AjaxResult.success("修改用户行为成功");
    }

    @DeleteMapping("/{actionIds}")
    public AjaxResult remove(@PathVariable Long[] actionIds) {
        userActionService.deleteUserActionByIds(actionIds);
        return AjaxResult.success("删除用户行为成功");
    }
}
