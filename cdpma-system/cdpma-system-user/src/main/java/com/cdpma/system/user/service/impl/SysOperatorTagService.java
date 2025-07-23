package com.cdpma.system.user.service.impl;

import com.cdpma.common.pojo.pojo.SysOperatorTag;
import com.cdpma.system.user.mapper.SysOperatorTagMapper;
import com.cdpma.system.user.service.ISysOperatorTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysOperatorTagService implements ISysOperatorTagService {

    @Autowired
    private SysOperatorTagMapper operatorTagMapper;

    @Override
    public List<Long> selectTagIdsByOperatorId(Long operatorId) {
        return operatorTagMapper.selectTagIdsByOperatorId(operatorId);
    }

    @Override
    public int batchInsertOperatorTags(List<SysOperatorTag> operatorTags) {
        return operatorTagMapper.batchInsertOperatorTags(operatorTags);
    }

    @Override
    public int insertOperatorTag(SysOperatorTag operatorTag) {
        return operatorTagMapper.insertOperatorTag(operatorTag);
    }

    @Override
    public int deleteTagsByOperatorId(Long operatorId) {
        return operatorTagMapper.deleteTagsByOperatorId(operatorId);
    }
}
