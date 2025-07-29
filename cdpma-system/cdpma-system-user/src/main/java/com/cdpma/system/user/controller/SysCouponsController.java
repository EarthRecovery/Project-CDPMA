package com.cdpma.system.user.controller;

import com.cdpma.common.core.utils.uuid.UUID;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.log.annotation.Log;
import com.cdpma.common.log.enums.BusinessType;
import com.cdpma.common.pojo.enums.Tag;
import com.cdpma.common.pojo.pojo.SysCoupons;
import com.cdpma.common.security.annotation.RequiresTags;
import com.cdpma.common.security.utils.SecurityUtils;
import com.cdpma.system.user.service.ISysCouponsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/coupons")
public class SysCouponsController {

    @Autowired
    private ISysCouponsService couponsService;

    @GetMapping("/{couponId}")
    public AjaxResult getInfo(@PathVariable Long couponId) {
        return AjaxResult.success(couponsService.selectCouponsById(couponId));
    }

    @GetMapping("/list")
    @RequiresTags(value = {Tag.ASSISTANT})
    public AjaxResult list(SysCoupons coupons) {
        List<SysCoupons> list = couponsService.selectCouponsList(coupons);
        return AjaxResult.success(list);
    }

    @PostMapping
    @RequiresTags(value = {Tag.ASSISTANT})
    @Log(title = "新增优惠券", businessType = BusinessType.INSERT)
    public AjaxResult add(@RequestBody SysCoupons coupons) {
        couponsService.insertCoupons(coupons);
        return AjaxResult.success("新增优惠券成功");
    }

    @PutMapping
    @RequiresTags(value = {Tag.ASSISTANT})
    @Log(title = "修改优惠券", businessType = BusinessType.UPDATE)
    public AjaxResult edit(@RequestBody SysCoupons coupons) {
        coupons.setUpdatedAt(new Date());
        coupons.setUpdatedBy(SecurityUtils.getOperatorId());
        couponsService.updateCoupons(coupons);
        return AjaxResult.success("修改优惠券成功");
    }

    @DeleteMapping("/{couponIds}")
    @RequiresTags(value = {Tag.ASSISTANT})
    @Log(title = "删除优惠券", businessType = BusinessType.DELETE)
    public AjaxResult remove(@PathVariable Long[] couponIds) {
        couponsService.deleteCouponsByIds(couponIds);
        return AjaxResult.success("删除优惠券成功");
    }

    @GetMapping("/distribute")
    @RequiresTags(value = {Tag.ASSISTANT})
    @Log(title = "分发优惠券", businessType = BusinessType.UPDATE)
    public AjaxResult distributeCoupons(@RequestParam Long couponId, @RequestParam Long userId) {
        couponsService.distributeCouponsById(couponId, userId);
        return AjaxResult.success("分发优惠券成功");
    }

    @GetMapping("/use")
    @RequiresTags(value = {Tag.USER})
    @Log(title = "使用优惠券", businessType = BusinessType.UPDATE)
    public AjaxResult useCoupons(@RequestParam Long couponId) {
        couponsService.useCouponsById(couponId);
        return AjaxResult.success("使用优惠券成功");
    }
}
