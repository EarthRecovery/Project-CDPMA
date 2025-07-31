package com.cdpma.common.pojo.pojo;

import com.cdpma.common.core.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SysOperationTriggerCondition extends BaseEntity {
    private Long conditionId;       // 触发条件编号
    private String conditionName;   // 触发条件名称
    private Boolean isUrgent;       // 及时/非及时
    private String description;     // 解释

    public Long getConditionId() {
        return conditionId;
    }

    public void setConditionId(Long conditionId) {
        this.conditionId = conditionId;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
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
        return "SysOperationTriggerCondition{" +
                "conditionId=" + conditionId +
                ", conditionName='" + conditionName + '\'' +
                ", isUrgent=" + isUrgent +
                ", description='" + description + '\'' +
                '}';
    }
}
