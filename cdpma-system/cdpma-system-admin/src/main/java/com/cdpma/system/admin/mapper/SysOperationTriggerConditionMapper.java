package com.cdpma.system.admin.mapper;

import com.cdpma.common.pojo.pojo.SysOperationTriggerCondition;

import java.util.List;

public interface SysOperationTriggerConditionMapper {

    SysOperationTriggerCondition selectConditionById(Long conditionId);

    List<SysOperationTriggerCondition> selectConditionList(SysOperationTriggerCondition condition);

    int insertCondition(SysOperationTriggerCondition condition);

    int updateCondition(SysOperationTriggerCondition condition);

    int deleteConditionByIds(Long[] conditionIds);

    List<SysOperationTriggerCondition> selectAllCondition();
}
