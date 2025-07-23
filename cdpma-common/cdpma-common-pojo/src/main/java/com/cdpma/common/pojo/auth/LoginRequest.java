package com.cdpma.common.pojo.auth;

public class LoginRequest {

    private String operator_name;
    private String operator_phone;
    private String operator_email;
    private String operator_password;

    public String getOperator_name() {
        return operator_name;
    }

    public void setOperator_name(String operator_name) {
        this.operator_name = operator_name;
    }

    public String getOperator_phone() {
        return operator_phone;
    }

    public void setOperator_phone(String operator_phone) {
        this.operator_phone = operator_phone;
    }

    public String getOperator_email() {
        return operator_email;
    }

    public void setOperator_email(String operator_email) {
        this.operator_email = operator_email;
    }

    public String getOperator_password() {
        return operator_password;
    }

    public void setOperator_password(String operator_password) {
        this.operator_password = operator_password;
    }
}
