package com.cdpma.system.user.service.impl;

import com.cdpma.common.pojo.pojo.SysRuntimeLog;
import com.cdpma.system.user.mapper.SysRuntimeLogMapper;
import com.cdpma.system.user.service.ISysRuntimeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRuntimeLogService implements ISysRuntimeLogService {
    @Autowired
    private SysRuntimeLogMapper runtimeLogMapper;

    @Override
    public SysRuntimeLog selectRuntimeLogById(Long logId) {
        return runtimeLogMapper.selectRuntimeLogById(logId);
    }

    @Override
    public List<SysRuntimeLog> selectRuntimeLogList(SysRuntimeLog log) {
        return runtimeLogMapper.selectRuntimeLogList(log);
    }

    @Override
    public int insertRuntimeLog(SysRuntimeLog log) {
        return runtimeLogMapper.insertRuntimeLog(log);
    }

    @Override
    public int deleteRuntimeLogByIds(Long[] logIds) {
        return runtimeLogMapper.deleteRuntimeLogByIds(logIds);
    }
}
