package com.cdpma.system.user.service.impl;

import com.cdpma.common.pojo.pojo.SysFavoritesRecord;
import com.cdpma.system.user.mapper.SysFavoritesRecordMapper;
import com.cdpma.system.user.service.ISysFavoritesRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysFavoritesRecordService implements ISysFavoritesRecordService {

    @Autowired
    private SysFavoritesRecordMapper favoritesRecordMapper;

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
}
