package com.cdpma.system.user.service.impl;

import com.cdpma.system.user.service.ISysUserActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cdpma.common.pojo.pojo.SysUserAction;
import com.cdpma.system.user.mapper.SysUserActionMapper;

import java.util.List;

@Service
public class SysUserActionService implements ISysUserActionService {

    @Autowired
    private SysUserActionMapper userActionMapper;

    @Override
    public SysUserAction selectUserActionById(Long actionId) {
        return userActionMapper.selectUserActionById(actionId);
    }

    @Override
    public List<SysUserAction> selectUserActionList(SysUserAction userAction) {
        return userActionMapper.selectUserActionList(userAction);
    }

    @Override
    public int insertUserAction(SysUserAction userAction) {
        return userActionMapper.insertUserAction(userAction);
    }

    @Override
    public int updateUserAction(SysUserAction userAction) {
        return userActionMapper.updateUserAction(userAction);
    }

    @Override
    public int deleteUserActionByIds(Long[] actionIds) {
        return userActionMapper.deleteUserActionByIds(actionIds);
    }
}
