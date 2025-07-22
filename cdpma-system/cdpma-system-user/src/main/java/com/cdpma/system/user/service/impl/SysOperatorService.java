package com.cdpma.system.user.service.impl;

import com.cdpma.common.pojo.pojo.SysOperator;
import com.cdpma.system.user.mapper.SysOperatorMapper;
import com.cdpma.system.user.service.ISysOpratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return sysOperatorMapper.selectOperatorList(sysOperator);
    }

    @Override
    public int insertOperator(SysOperator sysOperator) {
        return sysOperatorMapper.insertOperator(sysOperator);
    }

    @Override
    public int updateOperator(SysOperator sysOperator) {
        return sysOperatorMapper.updateOperator(sysOperator);
    }

    @Override
    public int deleteOperatorByIds(Long[] operatorIds) {
        return sysOperatorMapper.deleteOperatorByIds(operatorIds);
    }
}
