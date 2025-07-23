package com.cdpma.system.user.service;

import com.cdpma.common.pojo.pojo.SysRuntimeLog;

import java.util.List;

public interface ISysRuntimeLogService {
    SysRuntimeLog selectRuntimeLogById(Long logId);

    List<SysRuntimeLog> selectRuntimeLogList(SysRuntimeLog log);

    int insertRuntimeLog(SysRuntimeLog log);

    int deleteRuntimeLogByIds(Long[] logIds);
}
