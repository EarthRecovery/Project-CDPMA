package com.cdpma.common.pojo.pojo;

import com.cdpma.common.core.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SysOperationResponse extends BaseEntity {
    private Long responseId;       // 响应编号
    private String responseName;   // 触发响应名称

    @JsonProperty("isUrgent")
    private Boolean isUrgent;      // 及时/非及时

    private String description;    // 解释

    public Long getResponseId() {
        return responseId;
    }

    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }

    public String getResponseName() {
        return responseName;
    }

    public void setResponseName(String responseName) {
        this.responseName = responseName;
    }

    @JsonProperty("isUrgent")
    public Boolean getUrgent() {
        return isUrgent;
    }

    @JsonProperty("isUrgent")
    public void setUrgent(Boolean urgent) {
        isUrgent = urgent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SysOprationResponse{" +
                "responseId=" + responseId +
                ", responseName='" + responseName + '\'' +
                ", isUrgent=" + isUrgent +
                ", description='" + description + '\'' +
                '}';
    }
}
