package com.cdpma.common.pojo.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class SysUserAction {

    private Long actionId;           // 用户行为ID
    private Long operatorId;         // 用户编号
    private String actionType;       // 用户操作类型
    private Date actionTime;         // 用户操作时间
    private Integer actionStatus;    // 用户操作状态
    private String webpage;          // 用户操作对象-网页
    private Long goodId;             // 用户操作对象-商品
    private BigDecimal totalPrice;   // 用户操作对象-商品-总价
    private Long orderId;            // 用户操作对象-商品-订单号
    private String categoryName;     // 用户操作对象-商品种类
    private String levelChange;      // 用户操作对象-等级变动
    private Long targetId;           // 用户操作对象-目标ID

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }

    public Integer getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(Integer actionStatus) {
        this.actionStatus = actionStatus;
    }

    public String getWebpage() {
        return webpage;
    }

    public void setWebpage(String webpage) {
        this.webpage = webpage;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getLevelChange() {
        return levelChange;
    }

    public void setLevelChange(String levelChange) {
        this.levelChange = levelChange;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    @Override
    public String toString() {
        return "SysUserAction{" +
                "actionId=" + actionId +
                ", operatorId=" + operatorId +
                ", actionType='" + actionType + '\'' +
                ", actionTime=" + actionTime +
                ", actionStatus=" + actionStatus +
                ", webpage='" + webpage + '\'' +
                ", goodId=" + goodId +
                ", totalPrice=" + totalPrice +
                ", orderId=" + orderId +
                ", categoryName='" + categoryName + '\'' +
                ", levelChange='" + levelChange + '\'' +
                ", targetId=" + targetId +
                '}';
    }
}
