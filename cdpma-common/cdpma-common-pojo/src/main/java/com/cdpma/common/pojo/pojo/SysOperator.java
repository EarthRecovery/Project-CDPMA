package com.cdpma.common.pojo.pojo;

import com.cdpma.common.core.constant.OperatorConstants;
import com.cdpma.common.core.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.*;
import java.util.Date;

public class SysOperator extends BaseEntity {

    private Long operatorId;

    @NotBlank(message = "操作员姓名不能为空")
    @Size(max = OperatorConstants.MAX_OPERATOR_NAME_LENGTH, message = "姓名长度不能超过60个字符")
    private String operatorName;

    @Size(max = OperatorConstants.MAX_OPERATOR_NAME_LENGTH, message = "昵称长度不能超过60个字符")
    private String operatorNickname;

    @NotBlank(message = "身份证号不能为空")
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|20)?\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[\\dXx]$",
            message = "身份证号格式不正确")
    private String operatorIdCard;

    @NotBlank(message = "电话不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "电话格式不正确")
    private String operatorPhone;

    @NotBlank(message = "性别不能为空")
    @Pattern(regexp = "^[MFO]$", message = "性别必须为 M（男）, F（女）或 O（其他）")
    private String operatorGender;

    @NotNull(message = "年龄不能为空")
    @Max(value = OperatorConstants.MAX_AGE, message = "年龄不能大于200岁")
    private Integer operatorAge;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String operatorEmail;

    @NotBlank(message = "角色不能为空")
    private String operatorRole;

    @NotBlank(message = "所属大区不能为空")
    private String operatorRegion;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    private Boolean isDisabled;

    @NotBlank(message = "密码不能为空")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\\$%\\^&\\*]).{8,}$",
            message = "密码必须包含大小写字母、数字和特殊字符，且长度不少于8位"
    )
    private String passwordHash;

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
