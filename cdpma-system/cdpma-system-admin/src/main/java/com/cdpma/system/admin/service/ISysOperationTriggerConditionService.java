package com.cdpma.system.admin.service;

import com.cdpma.common.pojo.pojo.SysOperationTriggerCondition;

import java.util.List;
import java.util.Map;

public interface ISysOperationTriggerConditionService {
    SysOperationTriggerCondition selectConditionById(Long conditionId);

    List<SysOperationTriggerCondition> selectConditionList(SysOperationTriggerCondition condition);

    int insertCondition(SysOperationTriggerCondition condition);

    int updateCondition(SysOperationTriggerCondition condition);

    int deleteConditionByIds(Long[] conditionIds);

    List<SysOperationTriggerCondition> selectAllCondition();
}
