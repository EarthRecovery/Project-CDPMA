package com.cdpma.system.user.service;

import com.cdpma.common.pojo.pojo.SysCoupons;

import java.util.List;

public interface ISysCouponsService {
    SysCoupons selectCouponsById(Long couponId);

    List<SysCoupons> selectCouponsList(SysCoupons coupons);

    int insertCoupons(SysCoupons coupons);

    int updateCoupons(SysCoupons coupons);

    int deleteCouponsByIds(Long[] couponIds);

    int distributeCouponsById(Long couponId, Long userId);

    int useCouponsById(Long couponId);
}
