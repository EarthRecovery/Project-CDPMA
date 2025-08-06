package com.cdpma.system.user.mapper;

import com.cdpma.common.pojo.pojo.SysLikeRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysLikeRecordMapper {
    SysLikeRecord selectLikeRecordById(Long recordId);

    List<SysLikeRecord> selectLikeRecordList(SysLikeRecord record);

    int insertLikeRecord(SysLikeRecord record);

    int updateLikeRecord(SysLikeRecord record);

    int deleteLikeRecordByIds(Long[] recordIds);

    void deleteLikeRecordByGoodIdAndOperatorId(@Param("goodId") Long goodId, @Param("operatorId") Long operatorId);
}
