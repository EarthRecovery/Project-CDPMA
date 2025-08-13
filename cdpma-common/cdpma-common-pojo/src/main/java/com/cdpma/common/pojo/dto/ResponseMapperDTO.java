package com.cdpma.common.pojo.dto;

public class ResponseMapperDTO {

    String responseName;
    String param;

    public String getResponseName() {
        return responseName;
    }

    public void setResponseName(String responseName) {
        this.responseName = responseName;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "ResponseMapperDTO{" +
                "responseName='" + responseName + '\'' +
                ", param='" + param + '\'' +
                '}';
    }
}
