package com.cdpma.system.user.service.impl;

import com.cdpma.common.pojo.pojo.SysLikeRecord;
import com.cdpma.system.user.mapper.SysLikeRecordMapper;
import com.cdpma.system.user.service.ISysLikeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysLikeRecordService implements ISysLikeRecordService {

    @Autowired
    private SysLikeRecordMapper likeRecordMapper;

    @Override
    public SysLikeRecord selectLikeRecordById(Long recordId) {
        return likeRecordMapper.selectLikeRecordById(recordId);
    }

    @Override
    public List<SysLikeRecord> selectLikeRecordList(SysLikeRecord record) {
        return likeRecordMapper.selectLikeRecordList(record);
    }

    @Override
    public int insertLikeRecord(SysLikeRecord record) {
        return likeRecordMapper.insertLikeRecord(record);
    }

    @Override
    public int updateLikeRecord(SysLikeRecord record) {
        return likeRecordMapper.updateLikeRecord(record);
    }

    @Override
    public int deleteLikeRecordByIds(Long[] recordIds) {
        return likeRecordMapper.deleteLikeRecordByIds(recordIds);
    }

}
