package com.cdpma.system.user.mapper;

import com.cdpma.common.pojo.pojo.SysLoginLogoutLog;
import java.util.List;

public interface SysLoginLogoutLogMapper {
    SysLoginLogoutLog selectLogById(Long logId);

    List<SysLoginLogoutLog> selectLogList(SysLoginLogoutLog log);

    int insertLog(SysLoginLogoutLog log);

    int deleteLogByIds(Long[] logIds);
}
