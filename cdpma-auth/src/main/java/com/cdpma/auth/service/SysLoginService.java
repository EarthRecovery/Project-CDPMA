package com.cdpma.auth.service;

import com.cdpma.api.systemuser.RemoteOperatorService;
import com.cdpma.common.core.constant.OperatorConstants;
import com.cdpma.common.core.constant.SecurityConstants;
import com.cdpma.common.core.exception.ServiceException;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.pojo.auth.LoginRequest;
import com.cdpma.common.pojo.enums.LoginLogout;
import com.cdpma.common.pojo.pojo.SysOperator;
import com.cdpma.common.security.utils.PasswordUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysLoginService {

    private SysOperator targetOperator;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private RemoteOperatorService remoteOperatorService;

    @Autowired
    private SysRecordAuthService sysRecordAuthService;

    public SysOperator login(LoginRequest loginRequest){

        // 根据用户名称查询
        if(loginRequest.getOperator_name() != null){
            String operatorName = loginRequest.getOperator_name();
            if(operatorName.length() > OperatorConstants.MAX_OPERATOR_NAME_LENGTH){
                sysRecordAuthService.recordinfo(operatorName, null, LoginLogout.LOGIN_FAIL,
                        "登录失败，用户名长度不能超过" + OperatorConstants.MAX_OPERATOR_NAME_LENGTH + "个字符");
                throw new ServiceException("登录失败，用户名长度不能超过" + OperatorConstants.MAX_OPERATOR_NAME_LENGTH + "个字符");
            }
            AjaxResult result = remoteOperatorService.getOperatorByName(operatorName, SecurityConstants.INNER);
            if(result.isSuccess()){
                Object data = result.get(AjaxResult.DATA_TAG);
                this.targetOperator = mapper.convertValue(data, SysOperator.class);
                sysRecordAuthService.recordinfo(operatorName, this.targetOperator.getOperatorId(),
                        LoginLogout.LOGIN_SUCCESS,"登录成功");
            }
        // 根据电话号码查询
        }else if(loginRequest.getOperator_phone() != null){
            String operatorPhone = loginRequest.getOperator_phone();
            if(operatorPhone.length() != 11){
                sysRecordAuthService.recordinfo(operatorPhone, null, LoginLogout.LOGIN_FAIL,
                        "登录失败，电话号码必须为11位");
                throw new ServiceException("登录失败，电话号码必须为11位");
            }
            AjaxResult result = remoteOperatorService.getOperatorByPhone(operatorPhone, SecurityConstants.INNER);
            if(result.isSuccess()){
                Object data = result.get(AjaxResult.DATA_TAG);
                this.targetOperator = mapper.convertValue(data, SysOperator.class);
                sysRecordAuthService.recordinfo(operatorPhone, this.targetOperator.getOperatorId(),
                        LoginLogout.LOGIN_SUCCESS,"登录成功");
            }
        //  根据邮箱查询
        }else if(loginRequest.getOperator_email() != null){
            String operatorEmail = loginRequest.getOperator_email();
            if(!operatorEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){
                sysRecordAuthService.recordinfo(operatorEmail, null, LoginLogout.LOGIN_FAIL,
                        "登录失败，邮箱格式不正确");
                throw new ServiceException("登录失败，邮箱格式不正确");
            }
            AjaxResult result = remoteOperatorService.getOperatorByEmail(operatorEmail, SecurityConstants.INNER);
            if(result.isSuccess()){
                Object data = result.get(AjaxResult.DATA_TAG);
                this.targetOperator = mapper.convertValue(data, SysOperator.class);
                sysRecordAuthService.recordinfo(operatorEmail, this.targetOperator.getOperatorId(),
                        LoginLogout.LOGIN_SUCCESS,"登录成功");
            }
        //  如果以上都没有，就抛出异常
        }else{
            sysRecordAuthService.recordinfo(null, null, LoginLogout.LOGIN_FAIL,
                    "登录失败，用户名/电话号码/邮箱必须有一个");
            throw new ServiceException("登录失败，用户名/电话号码/邮箱必须有一个");
        }
        // 如果查询为空，抛出异常
        if(targetOperator == null){
            String identifier = loginRequest.getOperator_name() != null ? loginRequest.getOperator_name() :
                    (loginRequest.getOperator_phone() != null ? loginRequest.getOperator_phone() : loginRequest.getOperator_email());
            sysRecordAuthService.recordinfo(identifier, null, LoginLogout.LOGIN_FAIL,
                    "登录失败，无法找到对应用户");
            throw new ServiceException("无法找到对应用户");
        }

        // 密码验证
        if(!PasswordUtil.verifyPassword(loginRequest.getOperator_password(), targetOperator.getPasswordHash())) {
            sysRecordAuthService.recordinfo(targetOperator.getOperatorName(), targetOperator.getOperatorId(),
                    LoginLogout.LOGIN_FAIL, "登录失败，密码错误");
            throw new ServiceException("登录失败，密码错误");
        }


        return targetOperator;

    }

}
