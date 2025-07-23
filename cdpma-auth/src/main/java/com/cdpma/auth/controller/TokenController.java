package com.cdpma.auth.controller;

import com.cdpma.auth.service.SysLoginService;
import com.cdpma.auth.service.SysRegisterService;
import com.cdpma.common.core.domain.R;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.pojo.auth.LoginRequest;
import com.cdpma.common.pojo.pojo.SysOperator;
import com.cdpma.common.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private SysRegisterService sysRegisterService;

    @Autowired
    private SysLoginService sysLoginService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("register")
    public AjaxResult register(@RequestBody SysOperator sysOperator)
    {
        // 用户注册
        sysRegisterService.register(sysOperator);
        return AjaxResult.success();
    }

    @PostMapping("login")
    public AjaxResult login(@RequestBody LoginRequest loginRequest) {
        // 用户登录
        SysOperator sysOperator = sysLoginService.login(loginRequest);
        return AjaxResult.success(tokenService.createToken(sysOperator));
    }
}
