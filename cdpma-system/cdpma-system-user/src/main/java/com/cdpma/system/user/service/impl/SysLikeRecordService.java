package com.cdpma.system.user.service.impl;

import com.cdpma.common.pojo.pojo.SysLikeRecord;
import com.cdpma.common.security.utils.SecurityUtils;
import com.cdpma.system.user.mapper.SysGoodMapper;
import com.cdpma.system.user.mapper.SysLikeRecordMapper;
import com.cdpma.system.user.service.ISysLikeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysLikeRecordService implements ISysLikeRecordService {

    @Autowired
    private SysLikeRecordMapper likeRecordMapper;

    @Autowired
    private SysGoodService goodService;

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
        goodService.editGoodLike(record.getGoodId(), 1);
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

    @Override
    public boolean checkHasRecord(SysLikeRecord record){
        SysLikeRecord sysLikeRecord = new SysLikeRecord();
        sysLikeRecord.setOperatorId(SecurityUtils.getOperatorId());
        sysLikeRecord.setGoodId(record.getGoodId());
        List<SysLikeRecord> list = selectLikeRecordList(sysLikeRecord);
        if(list!= null && !list.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void deleteLikeRecord(SysLikeRecord record) {
        goodService.editGoodLike(record.getGoodId(), -1);
        likeRecordMapper.deleteLikeRecordByGoodIdAndOperatorId(record.getGoodId(), SecurityUtils.getOperatorId());
    }

}
