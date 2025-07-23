package com.cdpma.common.pojo.pojo;

import com.cdpma.common.core.web.domain.BaseEntity;

public class SysOperatorTag extends BaseEntity {
    private Long operatorId;
    private Long tagId;
    private String tagName;

    public Long getOperatorId() {
        return operatorId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}
