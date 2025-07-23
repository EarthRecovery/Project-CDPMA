package com.cdpma.system.user.service;

import com.cdpma.common.pojo.pojo.SysLoginLogoutLog;
import java.util.List;

public interface ISysLoginLogoutLogService {
    SysLoginLogoutLog selectLogById(Long logId);

    List<SysLoginLogoutLog> selectLogList(SysLoginLogoutLog log);

    int insertLog(SysLoginLogoutLog log);

    int deleteLogByIds(Long[] logIds);
}
