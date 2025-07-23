package com.cdpma.common.log.service;

import com.cdpma.common.pojo.pojo.SysRuntimeLog;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    public void saveSysLog(SysRuntimeLog sysRuntimeLog) throws Exception
    {
        System.out.println(sysRuntimeLog.toString());
    }
}
