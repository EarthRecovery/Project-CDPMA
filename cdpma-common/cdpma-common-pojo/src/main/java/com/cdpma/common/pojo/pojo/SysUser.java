package com.cdpma.common.pojo.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class SysUser {
    private Long userId;
    private Long operatorId;
    private BigDecimal accountBalance;
    private Integer points;
    private Integer coupons;
    private Integer orders;
    private Integer userLevel;
    private String rfm;
    private String lifecycle;
    private Date createdAt;
    private Date updatedAt;
    private Boolean isDisabled;

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getOperatorId() { return operatorId; }
    public void setOperatorId(Long operatorId) { this.operatorId = operatorId; }

    public BigDecimal getAccountBalance() { return accountBalance; }
    public void setAccountBalance(BigDecimal accountBalance) { this.accountBalance = accountBalance; }

    public Integer getPoints() { return points; }
    public void setPoints(Integer points) { this.points = points; }

    public Integer getCoupons() { return coupons; }
    public void setCoupons(Integer coupons) { this.coupons = coupons; }

    public Integer getOrders() { return orders; }
    public void setOrders(Integer orders) { this.orders = orders; }

    public Integer getUserLevel() { return userLevel; }
    public void setUserLevel(Integer userLevel) { this.userLevel = userLevel; }

    public String getRfm() { return rfm; }
    public void setRfm(String rfm) { this.rfm = rfm; }

    public String getLifecycle() { return lifecycle; }
    public void setLifecycle(String lifecycle) { this.lifecycle = lifecycle; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    public Boolean getIsDisabled() { return isDisabled; }
    public void setIsDisabled(Boolean isDisabled) { this.isDisabled = isDisabled; }
}

