package com.cdpma.system.admin.service.impl;

import com.cdpma.common.pojo.enums.Tag;
import com.cdpma.common.pojo.pojo.SysOperation;
import com.cdpma.common.security.utils.SecurityUtils;
import com.cdpma.system.admin.mapper.SysOperationMapper;
import com.cdpma.system.admin.service.ISysOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SysOperationService implements ISysOperationService {

    @Autowired
    private SysOperationMapper operationMapper;

    @Override
    public SysOperation selectOperationById(Long operationId) {
        return operationMapper.selectOperationById(operationId);
    }

    @Override
    public List<SysOperation> selectOperationList(SysOperation operation) {
        return operationMapper.selectOperationList(operation);
    }

    @Override
    public int insertOperation(SysOperation operation) {
        operation.setCreatedBy(SecurityUtils.getOperatorId());
        operation.setCreateTime(new Date());
        operation.setOperationPermission(Tag.ASSISTANT);
        return operationMapper.insertOperation(operation);
    }

    @Override
    public int updateOperation(SysOperation operation) {
        operation.setUpdatedBy(SecurityUtils.getOperatorId());
        operation.setUpdateTime(new Date());
        return operationMapper.updateOperation(operation);
    }

    @Override
    public int deleteOperationByIds(Long[] operationIds) {
        return operationMapper.deleteOperationByIds(operationIds);
    }

    @Override
    public Map<String, List<String>> getOperationMap() {
        Map<String, List<String>> operationMap = new HashMap<>();
        List<HashMap<String, String>> retList = operationMapper.getOperationMap();

        for (HashMap<String, String> map : retList) {
            String key = map.get("conditionName");
            String value = map.get("responseName");

            // 如果 key 已存在，直接添加；否则新建 List
            operationMap.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
        }
        return operationMap;
    }
}
