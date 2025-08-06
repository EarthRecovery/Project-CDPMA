package com.cdpma.common.pojo.dto;

import java.math.BigDecimal;

public class GoodQueryDTO {
    Integer categoryId;
    String goodName;
    BigDecimal priceHigh;
    BigDecimal priceLow;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public BigDecimal getPriceHigh() {
        return priceHigh;
    }

    public void setPriceHigh(BigDecimal priceHigh) {
        this.priceHigh = priceHigh;
    }

    public BigDecimal getPriceLow() {
        return priceLow;
    }

    public void setPriceLow(BigDecimal priceLow) {
        this.priceLow = priceLow;
    }

    @Override
    public String toString() {
        return "GoodQueryDTO{" +
                "categoryId=" + categoryId +
                ", goodName='" + goodName + '\'' +
                ", priceHigh=" + priceHigh +
                ", priceLow=" + priceLow +
                '}';
    }
}