package com.cdpma.system.admin.mapper;

import com.cdpma.common.pojo.pojo.SysOperationResponse;

import java.util.List;

public interface SysOperationResponseMapper {

    SysOperationResponse selectResponseById(Long responseId);

    List<SysOperationResponse> selectResponseList(SysOperationResponse response);

    int insertResponse(SysOperationResponse response);

    int updateResponse(SysOperationResponse response);

    int deleteResponseByIds(Long[] responseIds);
}
