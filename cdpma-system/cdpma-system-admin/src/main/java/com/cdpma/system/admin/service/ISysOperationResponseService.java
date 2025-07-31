package com.cdpma.system.admin.service;

import com.cdpma.common.pojo.pojo.SysOperationResponse;

import java.util.List;

public interface ISysOperationResponseService {
    SysOperationResponse selectResponseById(Long responseId);

    List<SysOperationResponse> selectResponseList(SysOperationResponse response);

    int insertResponse(SysOperationResponse response);

    int updateResponse(SysOperationResponse response);

    int deleteResponseByIds(Long[] responseIds);
}
