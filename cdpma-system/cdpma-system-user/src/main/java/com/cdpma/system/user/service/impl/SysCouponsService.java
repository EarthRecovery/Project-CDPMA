package com.cdpma.system.user.service.impl;

import com.cdpma.common.core.exception.ServiceException;
import com.cdpma.common.core.utils.TimeUtils;
import com.cdpma.common.core.utils.uuid.UUID;
import com.cdpma.common.pojo.pojo.SysCoupons;
import com.cdpma.common.security.utils.SecurityUtils;
import com.cdpma.system.user.mapper.SysCouponsMapper;
import com.cdpma.system.user.service.ISysCouponsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysCouponsService implements ISysCouponsService {

    @Autowired
    private SysCouponsMapper couponsMapper;

    @Override
    public SysCoupons selectCouponsById(Long couponId) {
        return couponsMapper.selectCouponsById(couponId);
    }

    @Override
    public List<SysCoupons> selectCouponsList(SysCoupons coupons) {
        return couponsMapper.selectCouponsList(coupons);
    }

    @Override
    public int insertCoupons(SysCoupons coupons) {
        coupons.setCouponName(UUID.fastUUID().toString());
        coupons.setCreatedAt(new Date());
        coupons.setCreatedBy(SecurityUtils.getOperatorId());
        coupons.setDisabled(false);
        if(coupons.getIssued() == null || coupons.getIssuanceTarget() == null){
            coupons.setIssued(false);
        }
        return couponsMapper.insertCoupons(coupons);
    }

    @Override
    public int updateCoupons(SysCoupons coupons) {
        return couponsMapper.updateCoupons(coupons);
    }

    @Override
    public int deleteCouponsByIds(Long[] couponIds) {
        return couponsMapper.deleteCouponsByIds(couponIds);
    }

    @Override
    public int distributeCouponsById(Long couponId, Long userId) {
        SysCoupons coupons = couponsMapper.selectCouponsById(couponId);
        if(coupons.getIssued() == true || coupons.getDisabled() == true) {
            throw new ServiceException("优惠券已发放或已禁用");
        }
        coupons.setIssued(true);
        coupons.setIssuanceTarget(userId);
        coupons.setUpdatedAt(new Date());
        coupons.setUpdatedBy(SecurityUtils.getOperatorId());
        coupons.setIssuanceTime(new Date());
        updateCoupons(coupons);
        return 1; // Successfully distributed coupon
    }

    @Override
    public int useCouponsById(Long couponId) {
        SysCoupons coupons = couponsMapper.selectCouponsById(couponId);
        if(coupons.getIssued() == false || coupons.getDisabled() == true) {
            throw new ServiceException("优惠券未发放或已使用");
        }
        Date expireDate = coupons.getIssuanceTime();
        int expireHours = coupons.getExpirationDuration();
        expireDate = TimeUtils.addHours(expireDate, expireHours);
        if(expireDate.before(new Date())) {
            coupons.setDisabled(true);
            coupons.setUpdatedAt(new Date());
            coupons.setUpdatedBy(SecurityUtils.getOperatorId());
            updateCoupons(coupons);
            throw new ServiceException("优惠券已过期");
        }
        coupons.setDisabled(true);
        coupons.setUpdatedAt(new Date());
        coupons.setUpdatedAt(new Date());
        coupons.setUpdatedBy(SecurityUtils.getOperatorId());
        updateCoupons(coupons);
        return 1; // Successfully used coupon
    }
}
