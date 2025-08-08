package com.cdpma.system.user.service.impl;

import com.cdpma.common.pojo.pojo.SysOperator;
import com.cdpma.common.security.utils.PasswordUtil;
import com.cdpma.system.user.mapper.SysOperatorMapper;
import com.cdpma.system.user.service.ISysOpratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysOperatorService implements ISysOpratorService {
    @Autowired
    private SysOperatorMapper sysOperatorMapper;

    @Override
    public SysOperator selectOperatorById(Long operatorId) {
        return sysOperatorMapper.selectOperatorById(operatorId);
    }

    @Override
    public List<SysOperator> selectOperatorList(SysOperator sysOperator) {
        System.out.println("查询操作员列表: " + sysOperator.toString());
        return sysOperatorMapper.selectOperatorList(sysOperator);
    }

    @Override
    public Long insertOperator(SysOperator sysOperator) {
        // 插入创建时间
        sysOperator.setCreatedAt(new Date());
        // 插入是否停用为否
        sysOperator.setDisabled(false);
        // 将密码哈希加盐
        sysOperator.setPasswordHash(PasswordUtil.hashPassword(sysOperator.getPasswordHash()));
        sysOperatorMapper.insertOperator(sysOperator);
        return sysOperator.getOperatorId();
    }

    @Override
    public int updateOperator(SysOperator sysOperator) {
        return sysOperatorMapper.updateOperator(sysOperator);
    }

    @Override
    public int deleteOperatorByIds(Long[] operatorIds) {
        return sysOperatorMapper.deleteOperatorByIds(operatorIds);
    }

    @Override
    public List<SysOperator> selectOperatorByOperatorName(String operatorName) {
        return sysOperatorMapper.selectOperatorByOperatorName(operatorName);
    }

    @Override
    public List<SysOperator> selectOperatorByOperatorEmail(String operatorEmail) {
        return sysOperatorMapper.selectOperatorByOperatorEmail(operatorEmail);
    }

    @Override
    public List<SysOperator> selectOperatorByOperatorPhone(String operatorPhone) {
        return sysOperatorMapper.selectOperatorByOperatorPhone(operatorPhone);
    }

    @Override
    public List<SysOperator> selectOperatorByOperatorIdCard(String operatorIdCard) {
        return sysOperatorMapper.selectOperatorByOperatorIdCard(operatorIdCard);
    }
}
