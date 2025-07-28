package com.cdpma.common.log.service;

import com.cdpma.api.systemuser.RemoteLogService;
import com.cdpma.common.core.constant.SecurityConstants;
import com.cdpma.common.pojo.pojo.SysRuntimeLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    @Autowired
    private RemoteLogService remoteLogService;

    public void saveSysLog(SysRuntimeLog sysRuntimeLog) throws Exception
    {
        remoteLogService.insertOperation(sysRuntimeLog, SecurityConstants.INNER);
    }
}
