package com.cdpma.system.user.service.impl;

import com.cdpma.common.pojo.pojo.SysLoginLogoutLog;
import com.cdpma.system.user.mapper.SysLoginLogoutLogMapper;
import com.cdpma.system.user.service.ISysLoginLogoutLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysLoginLogoutLogServiceImpl implements ISysLoginLogoutLogService {

    @Autowired
    private SysLoginLogoutLogMapper logMapper;

    @Override
    public SysLoginLogoutLog selectLogById(Long logId) {
        return logMapper.selectLogById(logId);
    }

    @Override
    public List<SysLoginLogoutLog> selectLogList(SysLoginLogoutLog log) {
        return logMapper.selectLogList(log);
    }

    @Override
    public int insertLog(SysLoginLogoutLog log) {
        if(log.getTimestamp() == null) {
            log.setTimestamp(new Date());
        }
        return logMapper.insertLog(log);
    }

    @Override
    public int deleteLogByIds(Long[] logIds) {
        return logMapper.deleteLogByIds(logIds);
    }
}