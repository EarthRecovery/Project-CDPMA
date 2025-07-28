package com.cdpma.common.pojo.pojo;

import com.cdpma.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

public class SysGoodFullName extends BaseEntity {
    private Long goodId;
    private String goodName;
    private Long categoryId;
    private Long createdBy;
    private Date createdAt;
    private Long updatedBy;
    private Date updatedAt;
    private Boolean isDisabled;
    private BigDecimal price;
    private String categoryName;
    private String fatherCategoryName;

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getFatherCategoryName() {
        return fatherCategoryName;
    }

    public void setFatherCategoryName(String fatherCategoryName) {
        this.fatherCategoryName = fatherCategoryName;
    }

    @Override
    public String toString() {
        return "SysGoodFullName{" +
                "goodId=" + goodId +
                ", goodName='" + goodName + '\'' +
                ", categoryId=" + categoryId +
                ", createdBy=" + createdBy +
                ", createdAt=" + createdAt +
                ", updatedBy=" + updatedBy +
                ", updatedAt=" + updatedAt +
                ", isDisabled=" + isDisabled +
                ", price=" + price +
                ", categoryName='" + categoryName + '\'' +
                ", fatherCategoryName='" + fatherCategoryName + '\'' +
                '}';
    }
}
