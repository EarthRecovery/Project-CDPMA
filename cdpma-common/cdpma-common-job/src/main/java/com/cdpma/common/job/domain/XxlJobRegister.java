package com.cdpma.common.job.domain;

import groovy.transform.builder.Builder;

import java.util.HashMap;
import java.util.Map;

public class XxlJobRegister {
    /**
     * 执行器 ID
     */
    private Integer jobGroup;

    /**
     * 任务描述
     */
    private String jobDesc;

    /**
     * 作者
     */
    private String author;

    /**
     * 调度类型（CRON / FIX_RATE / FIX_DELAY）
     */
    private String scheduleType;

    /**
     * 调度配置（如 Cron 表达式）
     */
    private String scheduleConf;

    /**
     * Glue 类型（BEAN / GLUE_GROOVY / GLUE_SHELL 等）
     */
    private String glueType;

    /**
     * 执行器 Handler 名称（@XxlJob 注解的值）
     */
    private String executorHandler;

    /**
     * 路由策略（ROUND / RANDOM / FIRST / FAILOVER / BUSYOVER / SHARDING_BROADCAST）
     */
    private String executorRouteStrategy;

    /**
     * Misfire 策略（DO_NOTHING / FIRE_ONCE_NOW）
     */
    private String misfireStrategy;

    /**
     * 阻塞处理策略（SERIAL_EXECUTION / DISCARD_LATER / COVER_EARLY）
     */
    private String executorBlockStrategy;

    /**
     * 任务超时时间（秒）
     */
    private Integer executorTimeout;

    /**
     * 失败重试次数
     */
    private Integer executorFailRetryCount;

    public Integer getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(Integer jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public String getScheduleConf() {
        return scheduleConf;
    }

    public void setScheduleConf(String scheduleConf) {
        this.scheduleConf = scheduleConf;
    }

    public String getGlueType() {
        return glueType;
    }

    public void setGlueType(String glueType) {
        this.glueType = glueType;
    }

    public String getExecutorHandler() {
        return executorHandler;
    }

    public void setExecutorHandler(String executorHandler) {
        this.executorHandler = executorHandler;
    }

    public String getExecutorRouteStrategy() {
        return executorRouteStrategy;
    }

    public void setExecutorRouteStrategy(String executorRouteStrategy) {
        this.executorRouteStrategy = executorRouteStrategy;
    }

    public String getMisfireStrategy() {
        return misfireStrategy;
    }

    public void setMisfireStrategy(String misfireStrategy) {
        this.misfireStrategy = misfireStrategy;
    }

    public String getExecutorBlockStrategy() {
        return executorBlockStrategy;
    }

    public void setExecutorBlockStrategy(String executorBlockStrategy) {
        this.executorBlockStrategy = executorBlockStrategy;
    }

    public Integer getExecutorTimeout() {
        return executorTimeout;
    }

    public void setExecutorTimeout(Integer executorTimeout) {
        this.executorTimeout = executorTimeout;
    }

    public Integer getExecutorFailRetryCount() {
        return executorFailRetryCount;
    }

    public void setExecutorFailRetryCount(Integer executorFailRetryCount) {
        this.executorFailRetryCount = executorFailRetryCount;
    }

    @Override
    public String toString() {
        return "XxlJobRegister{" +
                "jobGroup=" + jobGroup +
                ", jobDesc='" + jobDesc + '\'' +
                ", author='" + author + '\'' +
                ", scheduleType='" + scheduleType + '\'' +
                ", scheduleConf='" + scheduleConf + '\'' +
                ", glueType='" + glueType + '\'' +
                ", executorHandler='" + executorHandler + '\'' +
                ", executorRouteStrategy='" + executorRouteStrategy + '\'' +
                ", misfireStrategy='" + misfireStrategy + '\'' +
                ", executorBlockStrategy='" + executorBlockStrategy + '\'' +
                ", executorTimeout=" + executorTimeout +
                ", executorFailRetryCount=" + executorFailRetryCount +
                '}';
    }
    public Map<String, Object> toParamsMap() {
        Map<String, Object> params = new HashMap<>();
        params.put("jobGroup", jobGroup);
        params.put("jobDesc", jobDesc);
        params.put("author", author);
        params.put("scheduleType", scheduleType);
        params.put("scheduleConf", scheduleConf);
        params.put("glueType", glueType);
        params.put("executorHandler", executorHandler);
        params.put("executorRouteStrategy", executorRouteStrategy);
        params.put("misfireStrategy", misfireStrategy);
        params.put("executorBlockStrategy", executorBlockStrategy);
        params.put("executorTimeout", executorTimeout);
        params.put("executorFailRetryCount", executorFailRetryCount);
        return params;
    }

}
