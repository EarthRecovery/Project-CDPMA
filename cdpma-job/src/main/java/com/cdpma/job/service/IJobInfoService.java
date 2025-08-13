package com.cdpma.job.service;

public interface IJobInfoService {
    void addJob(String jobDesc, String scheduleConf, String executorHandler, String executorParam);
}
