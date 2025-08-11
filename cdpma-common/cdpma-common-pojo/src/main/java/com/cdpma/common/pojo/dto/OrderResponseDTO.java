package com.cdpma.common.pojo.dto;

import java.math.BigDecimal;
import java.util.Date;

public class OrderResponseDTO {
    /** 订单ID */
    private Long orderId;

    /** 操作员编号 */
    private Long operatorId;

    /** 商品ID */
    private Long goodId;

    /** 商品数量 */
    private Integer quantity;

    /** 单价 */
    private BigDecimal unitPrice;

    /** 下单时间 */
    private Date orderTime;

    /** 是否付款 */
    private Boolean isPaid;

    /** 是否取消 */
    private Boolean isCancelled;

    /** 订单反馈 */
    private String feedback;

    /** 订单反馈评分 */
    private Integer feedbackScore;

    private String goodName;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public Boolean getCancelled() {
        return isCancelled;
    }

    public void setCancelled(Boolean cancelled) {
        isCancelled = cancelled;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Integer getFeedbackScore() {
        return feedbackScore;
    }

    public void setFeedbackScore(Integer feedbackScore) {
        this.feedbackScore = feedbackScore;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    @Override
    public String toString() {
        return "OrderResponseDTO{" +
                "orderId=" + orderId +
                ", operatorId=" + operatorId +
                ", goodId=" + goodId +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", orderTime=" + orderTime +
                ", isPaid=" + isPaid +
                ", isCancelled=" + isCancelled +
                ", feedback='" + feedback + '\'' +
                ", feedbackScore=" + feedbackScore +
                ", goodName='" + goodName + '\'' +
                '}';
    }
}
