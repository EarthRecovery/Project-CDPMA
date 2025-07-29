package com.cdpma.common.pojo.pojo;

import java.util.Date;

public class SysCoupons {

    private Long couponId;
    private String couponName;
    private Long createdBy;
    private Date createdAt;
    private Long updatedBy;
    private Date updatedAt;
    private Boolean isDisabled;
    private Boolean isIssued;
    private Long issuanceTarget;
    private String discountEffect;
    private Integer expirationDuration;
    private Date issuanceTime;

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getDisabled() {
        return isDisabled;
    }

    public void setDisabled(Boolean disabled) {
        isDisabled = disabled;
    }

    public Boolean getIssued() {
        return isIssued;
    }

    public void setIssued(Boolean issued) {
        isIssued = issued;
    }

    public Long getIssuanceTarget() {
        return issuanceTarget;
    }

    public void setIssuanceTarget(Long issuanceTarget) {
        this.issuanceTarget = issuanceTarget;
    }

    public String getDiscountEffect() {
        return discountEffect;
    }

    public void setDiscountEffect(String discountEffect) {
        this.discountEffect = discountEffect;
    }

    public Integer getExpirationDuration() {
        return expirationDuration;
    }

    public void setExpirationDuration(Integer expirationDuration) {
        this.expirationDuration = expirationDuration;
    }

    public Date getIssuanceTime() {
        return issuanceTime;
    }

    public void setIssuanceTime(Date issuanceTime) {
        this.issuanceTime = issuanceTime;
    }

    @Override
    public String toString() {
        return "SysCoupons{" +
                "couponId=" + couponId +
                ", couponName='" + couponName + '\'' +
                ", createdBy=" + createdBy +
                ", createdAt=" + createdAt +
                ", updatedBy=" + updatedBy +
                ", updatedAt=" + updatedAt +
                ", isDisabled=" + isDisabled +
                ", isIssued=" + isIssued +
                ", issuanceTarget=" + issuanceTarget +
                ", discountEffect='" + discountEffect + '\'' +
                ", expirationDuration=" + expirationDuration +
                ", issuanceTime=" + issuanceTime +
                '}';
    }
}
