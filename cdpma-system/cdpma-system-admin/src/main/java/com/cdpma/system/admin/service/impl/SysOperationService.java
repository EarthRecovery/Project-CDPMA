package com.cdpma.system.admin.service.impl;

import com.cdpma.common.pojo.dto.OperationMapperDTO;
import com.cdpma.common.pojo.dto.ResponseMapperDTO;
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
        System.out.println("查询操作列表: " + operation.toString());
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
    public Map<OperationMapperDTO, List<ResponseMapperDTO>> getOperationMap() {
        Map<OperationMapperDTO, List<ResponseMapperDTO>> operationMap = new HashMap<>();
        List<HashMap<String, String>> retList = operationMapper.getOperationMap();

        for (HashMap<String, String> map : retList) {
            String conditionName = map.get("conditionName");
            String responseName = map.get("responseName");
            String rule = map.get("rule");
            String arg = map.get("arg");

            OperationMapperDTO operationMapperDTO = new OperationMapperDTO();
            operationMapperDTO.setConditionName(conditionName);
            operationMapperDTO.setRule(rule);
            if(!operationMap.containsKey(operationMapperDTO)) {
                operationMap.put(operationMapperDTO, new ArrayList<>());
                ResponseMapperDTO responseMapperDTO = new ResponseMapperDTO();
                responseMapperDTO.setResponseName(responseName);
                responseMapperDTO.setParam(arg);
                operationMap.get(operationMapperDTO).add(responseMapperDTO);
            }else{
                ResponseMapperDTO responseMapperDTO = new ResponseMapperDTO();
                responseMapperDTO.setResponseName(responseName);
                responseMapperDTO.setParam(arg);
                operationMap.get(operationMapperDTO).add(responseMapperDTO);
            }
        }
        return operationMap;
    }
}
