package com.cdpma.system.user.service;

import com.cdpma.common.pojo.dto.GoodQueryDTO;
import com.cdpma.common.pojo.pojo.SysGood;
import com.cdpma.common.pojo.pojo.SysGoodFullName;

import java.util.List;

public interface ISysGoodService {
    SysGood selectGoodById(Long goodId);

    List<SysGood> selectGoodList(SysGood good);

    int insertGood(SysGood good);

    int updateGood(SysGood good);

    int deleteGoodByIds(Long[] goodIds);

    SysGoodFullName getFullNameGood(Long goodId);

    List<SysGood> searchGoods(GoodQueryDTO queryDTO);
}
