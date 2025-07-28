package com.cdpma.system.user.service;

import com.cdpma.common.pojo.pojo.SysLikeRecord;

import java.util.List;

public interface ISysLikeRecordService {
    SysLikeRecord selectLikeRecordById(Long recordId);

    List<SysLikeRecord> selectLikeRecordList(SysLikeRecord record);

    int insertLikeRecord(SysLikeRecord record);

    int updateLikeRecord(SysLikeRecord record);

    int deleteLikeRecordByIds(Long[] recordIds);
}
