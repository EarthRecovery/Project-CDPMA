package com.cdpma.system.admin.service.impl;

import com.cdpma.common.pojo.pojo.SysOperationResponse;
import com.cdpma.system.admin.mapper.SysOperationResponseMapper;
import com.cdpma.system.admin.service.ISysOperationResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysOperationResponseService implements ISysOperationResponseService {
    @Autowired
    private SysOperationResponseMapper responseMapper;

    @Override
    public SysOperationResponse selectResponseById(Long responseId) {
        return responseMapper.selectResponseById(responseId);
    }

    @Override
    public List<SysOperationResponse> selectResponseList(SysOperationResponse response) {
        return responseMapper.selectResponseList(response);
    }

    @Override
    public int insertResponse(SysOperationResponse response) {
        return responseMapper.insertResponse(response);
    }

    @Override
    public int updateResponse(SysOperationResponse response) {
        return responseMapper.updateResponse(response);
    }

    @Override
    public int deleteResponseByIds(Long[] responseIds) {
        return responseMapper.deleteResponseByIds(responseIds);
    }
}
