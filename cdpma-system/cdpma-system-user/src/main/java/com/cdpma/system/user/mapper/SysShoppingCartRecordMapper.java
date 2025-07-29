package com.cdpma.system.user.mapper;

import com.cdpma.common.pojo.pojo.SysShoppingCartRecord;

import java.util.List;

public interface SysShoppingCartRecordMapper {

    SysShoppingCartRecord selectRecordById(Long recordId);

    List<SysShoppingCartRecord> selectRecordList(SysShoppingCartRecord record);

    int insertRecord(SysShoppingCartRecord record);

    int updateRecord(SysShoppingCartRecord record);

    int deleteRecordByIds(Long[] recordIds);
}
