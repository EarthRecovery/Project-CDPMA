package com.cdpma.system.admin.mapper;

import com.cdpma.common.pojo.pojo.SysOperation;
import org.apache.ibatis.annotations.MapKey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SysOperationMapper {
    SysOperation selectOperationById(Long operationId);

    List<SysOperation> selectOperationList(SysOperation operation);

    int insertOperation(SysOperation operation);

    int updateOperation(SysOperation operation);

    int deleteOperationByIds(Long[] operationIds);

    @MapKey("conditionName")
    List<HashMap<String, String>> getOperationMap();
}
