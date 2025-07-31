package com.cdpma.common.pojo.pojo;

import com.cdpma.common.core.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class SysOperation extends BaseEntity {
    private Long operationId;           // 操作ID
    private Long triggerConditionId;    // 操作触发条件编号
    private Long operationResponse;     // 操作响应编号
    private Date createTime;            // 创建时间
    private Long createdBy;             // 创建者
    private Date updateTime;            // 更新时间
    private Long updatedBy;             // 更新者
    private Integer operationPermission;// 操作权限（1管理，2导购）

    private String operationDescription;// 操作说明

    private Boolean isEnabled;

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    public Long getTriggerConditionId() {
        return triggerConditionId;
    }

    public void setTriggerConditionId(Long triggerConditionId) {
        this.triggerConditionId = triggerConditionId;
    }

    public Long getOperationResponse() {
        return operationResponse;
    }

    public void setOperationResponse(Long operationResponse) {
        this.operationResponse = operationResponse;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getOperationPermission() {
        return operationPermission;
    }

    public void setOperationPermission(Integer operationPermission) {
        this.operationPermission = operationPermission;
    }

    public String getOperationDescription() {
        return operationDescription;
    }

    public void setOperationDescription(String operationDescription) {
        this.operationDescription = operationDescription;
    }

    @JsonProperty("isEnabled")
    public Boolean getEnabled() {
        return isEnabled;
    }

    @JsonProperty("isEnabled")
    public void setEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Override
    public String toString() {
        return "SysOperation{" +
                "operationId=" + operationId +
                ", triggerConditionId=" + triggerConditionId +
                ", operationResponse=" + operationResponse +
                ", createTime=" + createTime +
                ", createdBy=" + createdBy +
                ", updateTime=" + updateTime +
                ", updatedBy=" + updatedBy +
                ", operationPermission=" + operationPermission +
                ", operationDescription='" + operationDescription + '\'' +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
