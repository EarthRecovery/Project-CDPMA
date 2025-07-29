package com.cdpma.system.user.controller;

import com.cdpma.common.core.web.controller.BaseController;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.pojo.pojo.SysOperator;
import com.cdpma.common.pojo.pojo.SysUser;
import com.cdpma.system.user.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class SysUserController extends BaseController {

    @Autowired
    private ISysUserService userService;

    /**
     * 根据ID获取用户信息
     */
    @GetMapping("/{userId}")
    public AjaxResult getUser(@PathVariable Long userId) {
        return AjaxResult.success(userService.selectUserById(userId));
    }

    /**
     * 查询用户列表
     */
    @GetMapping("/list")
    public AjaxResult list(SysUser user) {
        List<SysUser> list = userService.selectUserList(user);
        return AjaxResult.success(list);
    }

    /**
     * 新增用户
     */
    @PostMapping
    public AjaxResult add(@RequestBody SysUser user) {
        userService.insertUser(user);
        return AjaxResult.success("用户添加成功");
    }

    /**
     * 更新用户信息
     */
    @PutMapping
    public AjaxResult update(@RequestBody SysUser user) {
        userService.updateUser(user);
        return AjaxResult.success("用户信息更新成功");
    }

    /**
     * 批量删除用户
     */
    @DeleteMapping("/{userIds}")
    public AjaxResult delete(@PathVariable Long[] userIds) {
        userService.deleteUserByIds(userIds);
        return AjaxResult.success("用户删除成功");
    }


}