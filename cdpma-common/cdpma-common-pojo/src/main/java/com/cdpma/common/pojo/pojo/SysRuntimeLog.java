package com.cdpma.common.pojo.pojo;

import java.util.Date;

public class SysRuntimeLog {
    /** 运行日志编号 */
    private Long logId;

    /** 运行 ID */
    private Long runtimeId;

    /** 运行时间 */
    private Date runtimeTime;

    /** 运行方法 */
    private String method;

    /** 是否成功 */
    private int isSuccessful;

    /** 错误代码 */
    private String errorCode;

    /** 返回参数 */
    private String responseParameters;

    /** 运行参数 */
    private String runtimeParameters;

    /** 操作者编号 */
    private Long operatorId;

    /** 操作者ip */
    private String operatorIp;

    /** 运行耗时（毫秒） */
    private Long runtimeDuration;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getRuntimeId() {
        return runtimeId;
    }

    public void setRuntimeId(Long runtimeId) {
        this.runtimeId = runtimeId;
    }

    public Date getRuntimeTime() {
        return runtimeTime;
    }

    public void setRuntimeTime(Date runtimeTime) {
        this.runtimeTime = runtimeTime;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getResponseParameters() {
        return responseParameters;
    }

    public void setResponseParameters(String responseParameters) {
        this.responseParameters = responseParameters;
    }

    public String getRuntimeParameters() {
        return runtimeParameters;
    }

    public void setRuntimeParameters(String runtimeParameters) {
        this.runtimeParameters = runtimeParameters;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Long getRuntimeDuration() {
        return runtimeDuration;
    }

    public void setRuntimeDuration(Long runtimeDuration) {
        this.runtimeDuration = runtimeDuration;
    }

    public String getOperatorIp() {
        return operatorIp;
    }

    public void setOperatorIp(String operatorIp) {
        this.operatorIp = operatorIp;
    }

    public int getIsSuccessful() {
        return isSuccessful;
    }

    public void setIsSuccessful(int isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    @Override
    public String toString() {
        return "SysRuntimeLog{" +
                "logId=" + logId +
                ", runtimeId=" + runtimeId +
                ", runtimeTime=" + runtimeTime +
                ", method='" + method + '\'' +
                ", isSuccessful=" + isSuccessful +
                ", errorCode='" + errorCode + '\'' +
                ", responseParameters='" + responseParameters + '\'' +
                ", runtimeParameters='" + runtimeParameters + '\'' +
                ", operatorId=" + operatorId +
                ", operatorIp='" + operatorIp + '\'' +
                ", runtimeDuration=" + runtimeDuration +
                '}';
    }
}
