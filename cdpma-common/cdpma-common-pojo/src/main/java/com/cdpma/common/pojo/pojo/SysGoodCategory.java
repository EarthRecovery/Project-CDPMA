package com.cdpma.common.pojo.pojo;

import com.cdpma.common.core.web.domain.BaseEntity;

public class SysGoodCategory extends BaseEntity {
    private Long categoryId;
    private String categoryName;
    private Long parentCategory;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Long parentCategory) {
        this.parentCategory = parentCategory;
    }

    @Override
    public String toString() {
        return "SysGoodCategory{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", parentCategory=" + parentCategory +
                '}';
    }
}
