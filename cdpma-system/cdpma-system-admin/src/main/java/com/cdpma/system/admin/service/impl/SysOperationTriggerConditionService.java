package com.cdpma.system.admin.service.impl;

import com.cdpma.common.pojo.pojo.SysOperationTriggerCondition;
import com.cdpma.system.admin.mapper.SysOperationTriggerConditionMapper;
import com.cdpma.system.admin.service.ISysOperationTriggerConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysOperationTriggerConditionService implements ISysOperationTriggerConditionService {

    @Autowired
    private SysOperationTriggerConditionMapper conditionMapper;

    @Override
    public SysOperationTriggerCondition selectConditionById(Long conditionId) {
        return conditionMapper.selectConditionById(conditionId);
    }

    @Override
    public List<SysOperationTriggerCondition> selectConditionList(SysOperationTriggerCondition condition) {
        return conditionMapper.selectConditionList(condition);
    }

    @Override
    public int insertCondition(SysOperationTriggerCondition condition) {
        return conditionMapper.insertCondition(condition);
    }

    @Override
    public int updateCondition(SysOperationTriggerCondition condition) {
        return conditionMapper.updateCondition(condition);
    }

    @Override
    public int deleteConditionByIds(Long[] conditionIds) {
        return conditionMapper.deleteConditionByIds(conditionIds);
    }

    @Override
    public List<SysOperationTriggerCondition> selectAllCondition() {
        return conditionMapper.selectAllCondition();
    }
}
