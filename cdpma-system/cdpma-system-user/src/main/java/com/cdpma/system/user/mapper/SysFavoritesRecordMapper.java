package com.cdpma.system.user.mapper;

import com.cdpma.common.pojo.pojo.SysFavoritesRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysFavoritesRecordMapper {
    SysFavoritesRecord selectFavoritesRecordById(Long recordId);

    List<SysFavoritesRecord> selectFavoritesRecordList(SysFavoritesRecord record);

    int insertFavoritesRecord(SysFavoritesRecord record);

    int updateFavoritesRecord(SysFavoritesRecord record);

    int deleteFavoritesRecordByIds(Long[] recordIds);

    List<SysFavoritesRecord> selectFavoritesRecordListByOperatorId(Long operatorId);

    void deleteFavoritesRecordByGoodIdAndOperatorId(@Param("goodId") Long goodId,@Param("operatorId") Long operatorId);
}
