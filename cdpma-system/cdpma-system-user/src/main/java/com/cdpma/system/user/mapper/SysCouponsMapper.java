package com.cdpma.system.user.mapper;

import com.cdpma.common.pojo.pojo.SysCoupons;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysCouponsMapper {

    SysCoupons selectCouponsById(Long couponId);

    List<SysCoupons> selectCouponsList(SysCoupons coupons);

    int insertCoupons(SysCoupons coupons);

    int updateCoupons(SysCoupons coupons);

    int deleteCouponsByIds(Long[] couponIds);

    List<SysCoupons> userSearchCoupons(@Param("isExpired") boolean isExpired,@Param("operatorId") Long operatorId);
}
