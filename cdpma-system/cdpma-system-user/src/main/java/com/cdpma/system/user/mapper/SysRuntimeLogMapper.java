package com.cdpma.system.user.mapper;

import com.cdpma.common.pojo.pojo.SysRuntimeLog;

import java.util.List;

public interface SysRuntimeLogMapper {
    /**
     * 根据ID查询运行日志
     */
    SysRuntimeLog selectRuntimeLogById(Long logId);

    /**
     * 查询运行日志列表（可条件筛选）
     */
    List<SysRuntimeLog> selectRuntimeLogList(SysRuntimeLog log);

    /**
     * 插入运行日志
     */
    int insertRuntimeLog(SysRuntimeLog log);

    /**
     * 删除运行日志（支持批量）
     */
    int deleteRuntimeLogByIds(Long[] logIds);
}
