package com.cdpma.system.user.service;

import com.cdpma.common.pojo.pojo.SysOperator;

import java.util.List;

public interface ISysOpratorService {
    /**
     * 根据ID查询操作员信息
     *
     * @param operatorId 操作员ID
     * @return 操作员对象
     */
    SysOperator selectOperatorById(Long operatorId);

    /**
     * 查询操作员列表
     *
     * @param sysOperator 查询条件封装对象
     * @return 操作员列表
     */
    List<SysOperator> selectOperatorList(SysOperator sysOperator);

    /**
     * 新增操作员
     *
     * @param sysOperator 操作员对象
     * @return 插入结果，通常是影响行数
     */
    Long insertOperator(SysOperator sysOperator);

    /**
     * 修改操作员信息
     *
     * @param sysOperator 操作员对象
     * @return 修改结果，通常是影响行数
     */
    int updateOperator(SysOperator sysOperator);

    /**
     * 根据ID数组批量删除操作员
     *
     * @param operatorIds 操作员ID数组
     * @return 删除结果，通常是影响行数
     */
    int deleteOperatorByIds(Long[] operatorIds);

    /**
     * 根据操作员name 查询操作员信息
     */
    List<SysOperator> selectOperatorByOperatorName(String operatorName);

    /**
     * 根据操作员邮箱查看操作员信息
     */
    List<SysOperator> selectOperatorByOperatorEmail(String operatorEmail);

    /**
     * 根据操作员手机号查看操作员信息
     */
    List<SysOperator> selectOperatorByOperatorPhone(String operatorPhone);

    List<SysOperator> selectOperatorByOperatorIdCard(String operatorIdCard);
}
