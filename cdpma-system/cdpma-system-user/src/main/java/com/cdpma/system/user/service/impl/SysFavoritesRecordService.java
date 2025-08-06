package com.cdpma.system.user.service.impl;

import com.cdpma.common.pojo.pojo.SysFavoritesRecord;
import com.cdpma.common.pojo.pojo.SysGood;
import com.cdpma.system.user.mapper.SysFavoritesRecordMapper;
import com.cdpma.system.user.mapper.SysGoodMapper;
import com.cdpma.system.user.service.ISysFavoritesRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysFavoritesRecordService implements ISysFavoritesRecordService {

    @Autowired
    private SysFavoritesRecordMapper favoritesRecordMapper;

    @Autowired
    private SysGoodService sysGoodService;

    @Override
    public SysFavoritesRecord selectFavoritesRecordById(Long recordId) {
        return favoritesRecordMapper.selectFavoritesRecordById(recordId);
    }

    @Override
    public List<SysFavoritesRecord> selectFavoritesRecordList(SysFavoritesRecord record) {
        return favoritesRecordMapper.selectFavoritesRecordList(record);
    }

    @Override
    public int insertFavoritesRecord(SysFavoritesRecord record) {
        return favoritesRecordMapper.insertFavoritesRecord(record);
    }

    @Override
    public int updateFavoritesRecord(SysFavoritesRecord record) {
        return favoritesRecordMapper.updateFavoritesRecord(record);
    }

    @Override
    public int deleteFavoritesRecordByIds(Long[] recordIds) {
        return favoritesRecordMapper.deleteFavoritesRecordByIds(recordIds);
    }

    @Override
    public List<SysGood> selectGoodListByOperatorId(Long operatorId) {
        List<SysFavoritesRecord> favoriteRecordList =
                favoritesRecordMapper.selectFavoritesRecordListByOperatorId(operatorId);
        List<SysGood> goodList = new ArrayList<SysGood>();
        for(SysFavoritesRecord favoritesRecord : favoriteRecordList){
            goodList.add(sysGoodService.selectGoodById(favoritesRecord.getGoodId()));
        }
        return goodList;
    }
}
