package com.cdpma.system.user.service;

import com.cdpma.common.pojo.pojo.SysUserAction;

import java.util.List;

public interface ISysUserActionService {
    SysUserAction selectUserActionById(Long actionId);

    List<SysUserAction> selectUserActionList(SysUserAction userAction);

    int insertUserAction(SysUserAction userAction);

    int updateUserAction(SysUserAction userAction);

    int deleteUserActionByIds(Long[] actionIds);
}
