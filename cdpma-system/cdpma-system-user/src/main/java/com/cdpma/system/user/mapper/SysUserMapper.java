package com.cdpma.system.user.mapper;

import com.cdpma.common.pojo.pojo.SysUser;

import java.util.List;

public interface SysUserMapper {
    SysUser selectUserById(Long userId);

    List<SysUser> selectUserList(SysUser user);

    int insertUser(SysUser user);

    int updateUser(SysUser user);

    int deleteUserByIds(Long[] userIds);

    SysUser selectUserByOperatorId(Long operatorId);
}