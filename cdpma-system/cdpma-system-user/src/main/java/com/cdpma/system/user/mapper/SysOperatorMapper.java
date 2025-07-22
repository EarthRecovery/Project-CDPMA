package com.cdpma.system.user.mapper;

import com.cdpma.common.pojo.pojo.SysOperator;

import java.util.List;

public interface SysOperatorMapper {
    /**
     * 根据ID查询单个操作员
     */
    SysOperator selectOperatorById(Long operatorId);

    /**
     * 查询操作员列表（支持多条件）
     */
    List<SysOperator> selectOperatorList(SysOperator sysOperator);

    /**
     * 新增操作员
     */
    int insertOperator(SysOperator sysOperator);

    /**
     * 更新操作员
     */
    int updateOperator(SysOperator sysOperator);

    /**
     * 根据多个ID批量删除操作员
     */
    int deleteOperatorByIds(Long[] operatorIds);
}
