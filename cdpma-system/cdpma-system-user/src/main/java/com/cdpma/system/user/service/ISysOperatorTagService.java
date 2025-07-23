package com.cdpma.system.user.service;

import com.cdpma.common.pojo.pojo.SysOperatorTag;

import java.util.List;

public interface ISysOperatorTagService {
    List<Long> selectTagIdsByOperatorId(Long operatorId);

    int batchInsertOperatorTags(List<SysOperatorTag> operatorTags);

    int insertOperatorTag(SysOperatorTag operatorTag);

    int deleteTagsByOperatorId(Long operatorId);
}
