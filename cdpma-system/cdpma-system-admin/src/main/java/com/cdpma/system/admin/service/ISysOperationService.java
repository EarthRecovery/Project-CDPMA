package com.cdpma.system.admin.service;

import com.cdpma.common.pojo.dto.OperationMapperDTO;
import com.cdpma.common.pojo.pojo.SysOperation;

import java.util.List;
import java.util.Map;

public interface ISysOperationService {

    SysOperation selectOperationById(Long operationId);

    List<SysOperation> selectOperationList(SysOperation operation);

    int insertOperation(SysOperation operation);

    int updateOperation(SysOperation operation);

    int deleteOperationByIds(Long[] operationIds);

    Map<OperationMapperDTO, List<String>> getOperationMap();
}
