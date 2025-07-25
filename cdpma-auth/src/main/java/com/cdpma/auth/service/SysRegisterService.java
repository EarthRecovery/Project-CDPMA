package com.cdpma.auth.service;

import com.alibaba.nacos.common.utils.StringUtils;
import com.cdpma.api.systemuser.RemoteOperatorService;
import com.cdpma.api.systemuser.RemoteUserService;
import com.cdpma.common.core.constant.OperatorConstants;
import com.cdpma.common.core.constant.SecurityConstants;
import com.cdpma.common.core.domain.R;
import com.cdpma.common.core.exception.ServiceException;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.pojo.enums.LoginLogout;
import com.cdpma.common.pojo.enums.OperatorRoles;
import com.cdpma.common.pojo.enums.UserLifeCycle;
import com.cdpma.common.pojo.pojo.SysOperator;
import com.cdpma.common.pojo.pojo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import static com.cdpma.common.core.constant.OperatorConstants.MAX_OPERATOR_NAME_LENGTH;

@Service
public class SysRegisterService {

    @Autowired
    private RemoteOperatorService remoteOperatorService;
    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private SysRecordAuthService sysRecordAuthService;

    /**
     * 注册
     */
    public void register(SysOperator operator)
    {
        //检查是否存在重复的name or phone or email
        AjaxResult aj_name = remoteOperatorService.getOperatorByName(operator.getOperatorName(), SecurityConstants.INNER);
        if(aj_name.get(AjaxResult.DATA_TAG) != null){
            throw new ServiceException("操作员名称已存在");
        }
        AjaxResult aj_phone = remoteOperatorService.getOperatorByPhone(operator.getOperatorPhone(), SecurityConstants.INNER);
        if(aj_phone.get(AjaxResult.DATA_TAG) != null){
            throw new ServiceException("操作员电话号码已存在");
        }
        AjaxResult aj_email = remoteOperatorService.getOperatorByEmail(operator.getOperatorEmail(), SecurityConstants.INNER);
        if(aj_email.get(AjaxResult.DATA_TAG) != null){
            throw new ServiceException("操作员邮箱已存在");
        }

        // 创建operator 表
        AjaxResult operatorResult = remoteOperatorService.insertOperator(operator, SecurityConstants.INNER);

        // 检查角色，如果是用户就创建用户表
        if(Objects.equals(operator.getOperatorRole(), OperatorRoles.USER)){
            SysUser user = new SysUser();
            Integer operatorId = (Integer) operatorResult.get("operatorId");
            user.setOperatorId(operatorId.longValue());
            user.setCreatedAt(new Date());
            // 初始化各个值
            user.setAccountBalance(BigDecimal.valueOf(0));
            user.setPoints(0);
            user.setCoupons(0);
            user.setOrders(0);
            user.setUserLevel(0);
            user.setRfm("000");
            user.setLifecycle(UserLifeCycle.NEW_USER);
            user.setIsDisabled(false);

            sysRecordAuthService.recordinfo(operator.getOperatorName(), operatorId.longValue(),
                    LoginLogout.REGISTER, "注册成功" );
            remoteUserService.insertUser(user, SecurityConstants.INNER);
        }
    }
}
