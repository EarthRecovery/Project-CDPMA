package com.cdpma.auth.service;

import com.cdpma.api.systemuser.RemoteLogService;
import com.cdpma.common.core.constant.SecurityConstants;
import com.cdpma.common.core.utils.ip.IpUtils;
import com.cdpma.common.pojo.enums.LoginLogout;
import com.cdpma.common.pojo.pojo.SysLoginLogoutLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SysRecordAuthService {
    @Autowired
    RemoteLogService remoteLogService;


    /**
     * 记录登录登出信息
     * @param operatorName  登陆用户名/用户手机号/用户邮箱
     * @param operatorId    登陆用户ID（可以没有）
     * @param status        登陆状态
     * @param message       登陆消息以及报错消息
     */
    public void recordinfo(String operatorName, Long operatorId, int status, String message)
    {
        SysLoginLogoutLog loginLogoutLog = new SysLoginLogoutLog();
        loginLogoutLog.setOperatorName(operatorName);
        loginLogoutLog.setIpAddress(IpUtils.getIpAddr());
        loginLogoutLog.setTimestamp(new Date());
        loginLogoutLog.setMessage(message);
        switch (status){
            case LoginLogout.LOGIN_SUCCESS:
                loginLogoutLog.setOperatorId(operatorId);
                loginLogoutLog.setAction(LoginLogout.LOGIN_SUCCESS);
                remoteLogService.insertLog(loginLogoutLog, SecurityConstants.INNER);
                break;
            case LoginLogout.LOGIN_FAIL:
                loginLogoutLog.setOperatorId(-1L);
                loginLogoutLog.setAction(LoginLogout.LOGIN_FAIL);
                remoteLogService.insertLog(loginLogoutLog, SecurityConstants.INNER);
                break;
            case LoginLogout.LOGOUT:
                loginLogoutLog.setOperatorId(operatorId);
                loginLogoutLog.setAction(LoginLogout.LOGOUT);
                remoteLogService.insertLog(loginLogoutLog, SecurityConstants.INNER);
                break;
            case LoginLogout.REGISTER:
                loginLogoutLog.setOperatorId(operatorId);
                loginLogoutLog.setAction(LoginLogout.REGISTER);
                remoteLogService.insertLog(loginLogoutLog, SecurityConstants.INNER);
                break;
            default:
                throw new RuntimeException("Unknown login/logout status: " + status);
        }
    }
}
