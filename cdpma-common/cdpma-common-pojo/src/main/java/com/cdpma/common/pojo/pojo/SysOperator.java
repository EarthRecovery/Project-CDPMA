package com.cdpma.common.pojo.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class SysOperator {

    private Long operatorId; // 操作员ID

    private String operatorName; // 操作员名称

    private String operatorNickname; // 操作员昵称

    private String operatorIdCard; // 操作员身份证

    private String operatorPhone; // 操作员电话

    private String operatorGender; // 操作员性别（默认 'O'）

    private Integer operatorAge; // 操作员年龄

    private String operatorEmail; // 操作员邮箱

    private String operatorRole; // 操作员角色

    private String operatorRegion; // 操作员所属大区

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt; // 操作员创建时间

    private Boolean isDisabled; // 是否停用

    private String passwordHash; // 操作员密码（哈希加盐）

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorNickname() {
        return operatorNickname;
    }

    public void setOperatorNickname(String operatorNickname) {
        this.operatorNickname = operatorNickname;
    }

    public String getOperatorIdCard() {
        return operatorIdCard;
    }

    public void setOperatorIdCard(String operatorIdCard) {
        this.operatorIdCard = operatorIdCard;
    }

    public String getOperatorPhone() {
        return operatorPhone;
    }

    public void setOperatorPhone(String operatorPhone) {
        this.operatorPhone = operatorPhone;
    }

    public String getOperatorGender() {
        return operatorGender;
    }

    public void setOperatorGender(String operatorGender) {
        this.operatorGender = operatorGender;
    }

    public Integer getOperatorAge() {
        return operatorAge;
    }

    public void setOperatorAge(Integer operatorAge) {
        this.operatorAge = operatorAge;
    }

    public String getOperatorEmail() {
        return operatorEmail;
    }

    public void setOperatorEmail(String operatorEmail) {
        this.operatorEmail = operatorEmail;
    }

    public String getOperatorRole() {
        return operatorRole;
    }

    public void setOperatorRole(String operatorRole) {
        this.operatorRole = operatorRole;
    }

    public String getOperatorRegion() {
        return operatorRegion;
    }

    public void setOperatorRegion(String operatorRegion) {
        this.operatorRegion = operatorRegion;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getDisabled() {
        return isDisabled;
    }

    public void setDisabled(Boolean disabled) {
        isDisabled = disabled;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
