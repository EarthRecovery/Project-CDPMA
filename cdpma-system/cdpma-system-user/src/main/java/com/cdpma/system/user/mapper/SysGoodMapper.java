package com.cdpma.system.user.mapper;

import com.cdpma.common.pojo.dto.GoodQueryDTO;
import com.cdpma.common.pojo.pojo.SysGood;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysGoodMapper {
    SysGood selectGoodById(Long goodId);

    List<SysGood> selectGoodList(SysGood good);

    int insertGood(SysGood good);

    int updateGood(SysGood good);

    int deleteGoodByIds(Long[] goodIds);

    List<SysGood> searchGoods(GoodQueryDTO queryDTO);

    int editGoodLike(@Param("goodId") Long goodId,@Param("likeDelta") int likeDelta);
}
