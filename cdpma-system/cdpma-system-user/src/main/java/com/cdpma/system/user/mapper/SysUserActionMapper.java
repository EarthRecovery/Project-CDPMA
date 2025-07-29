package com.cdpma.system.user.mapper;

import com.cdpma.common.pojo.pojo.SysUserAction;

import java.util.List;

public interface SysUserActionMapper {

    SysUserAction selectUserActionById(Long actionId);

    List<SysUserAction> selectUserActionList(SysUserAction userAction);

    int insertUserAction(SysUserAction userAction);

    int updateUserAction(SysUserAction userAction);

    int deleteUserActionByIds(Long[] actionIds);
}
