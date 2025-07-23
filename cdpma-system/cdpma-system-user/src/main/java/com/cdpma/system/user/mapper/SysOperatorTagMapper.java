package com.cdpma.system.user.mapper;

import com.cdpma.common.pojo.pojo.SysOperatorTag;

import java.util.List;

public interface SysOperatorTagMapper {
    /**
     * 根据操作员ID查询其所有标签ID
     */
    List<Long> selectTagIdsByOperatorId(Long operatorId);

    int insertOperatorTag(SysOperatorTag operatorTags);

    /**
     * 批量插入操作员标签关系
     */
    int batchInsertOperatorTags(List<SysOperatorTag> operatorTags);

    /**
     * 删除某操作员的所有标签关联
     */
    int deleteTagsByOperatorId(Long operatorId);
}
