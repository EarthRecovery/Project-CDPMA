package com.cdpma.system.user.service.impl;

import com.cdpma.common.pojo.pojo.SysUser;
import com.cdpma.system.user.mapper.SysUserMapper;
import com.cdpma.system.user.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserService implements ISysUserService {

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public SysUser selectUserById(Long userId) {
        return userMapper.selectUserById(userId);
    }

    @Override
    public List<SysUser> selectUserList(SysUser user) {
        return userMapper.selectUserList(user);
    }

    @Override
    public int insertUser(SysUser user) {
        return userMapper.insertUser(user);
    }

    @Override
    public int updateUser(SysUser user) {
        return userMapper.updateUser(user);
    }

    @Override
    public int deleteUserByIds(Long[] userIds) {
        return userMapper.deleteUserByIds(userIds);
    }
}
