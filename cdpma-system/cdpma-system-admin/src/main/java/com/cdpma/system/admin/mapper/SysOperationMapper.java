package com.cdpma.system.admin.mapper;

import com.cdpma.common.pojo.pojo.SysOperation;

import java.util.List;

public interface SysOperationMapper {
    SysOperation selectOperationById(Long operationId);

    List<SysOperation> selectOperationList(SysOperation operation);

    int insertOperation(SysOperation operation);

    int updateOperation(SysOperation operation);

    int deleteOperationByIds(Long[] operationIds);
}
