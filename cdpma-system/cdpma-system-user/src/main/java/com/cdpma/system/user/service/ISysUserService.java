package com.cdpma.system.user.service;

import com.cdpma.common.pojo.pojo.SysOperator;
import com.cdpma.common.pojo.pojo.SysUser;

import java.util.List;

public interface ISysUserService {
    SysUser selectUserById(Long userId);

    List<SysUser> selectUserList(SysUser user);

    int insertUser(SysUser user);

    int updateUser(SysUser user);

    int deleteUserByIds(Long[] userIds);

    SysUser selectUserByOperatorId(Long operatorId);
}
