package com.cdpma.system.user.service;

import com.cdpma.common.pojo.pojo.SysFavoritesRecord;
import com.cdpma.common.pojo.pojo.SysGood;

import java.util.List;

public interface ISysFavoritesRecordService {

    SysFavoritesRecord selectFavoritesRecordById(Long recordId);

    List<SysFavoritesRecord> selectFavoritesRecordList(SysFavoritesRecord record);

    int insertFavoritesRecord(SysFavoritesRecord record);

    int updateFavoritesRecord(SysFavoritesRecord record);

    int deleteFavoritesRecordByIds(Long[] recordIds);

    List<SysGood> selectGoodListByOperatorId(Long operatorId);

    boolean hasFavoriteRecord(SysFavoritesRecord record);

    void deleteFavoritesRecordByGoods(List<SysGood> goodList, Long operatorId);
}
