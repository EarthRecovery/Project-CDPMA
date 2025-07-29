package com.cdpma.system.user.service.impl;

import com.cdpma.common.pojo.pojo.SysShoppingCartRecord;
import com.cdpma.system.user.mapper.SysShoppingCartRecordMapper;
import com.cdpma.system.user.service.ISysShoppingCartRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysShoppingCartRecordService implements ISysShoppingCartRecordService {

    @Autowired
    private SysShoppingCartRecordMapper recordMapper;

    @Override
    public SysShoppingCartRecord selectRecordById(Long recordId) {
        return recordMapper.selectRecordById(recordId);
    }

    @Override
    public List<SysShoppingCartRecord> selectRecordList(SysShoppingCartRecord record) {
        return recordMapper.selectRecordList(record);
    }

    @Override
    public int insertRecord(SysShoppingCartRecord record) {
        record.setCreatedAt(new Date());
        record.setDeleted(false);
        return recordMapper.insertRecord(record);
    }

    @Override
    public int updateRecord(SysShoppingCartRecord record) {
        return recordMapper.updateRecord(record);
    }

    @Override
    public int deleteRecordByIds(Long[] recordIds) {
        return recordMapper.deleteRecordByIds(recordIds);
    }
}
