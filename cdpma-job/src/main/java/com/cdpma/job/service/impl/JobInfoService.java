package com.cdpma.job.service.impl;

import com.cdpma.common.job.domain.XxlJobInfo;
import com.cdpma.common.job.utils.XxlJobUtils;
import com.cdpma.common.security.utils.SecurityUtils;
import com.cdpma.job.service.IJobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobInfoService implements IJobInfoService {
    @Autowired
    private XxlJobUtils xxlJobUtils;

    @Override
    public void addJob(String jobDesc, String scheduleConf, String executorHandler, String executorParam) {
        XxlJobInfo jobInfo = new XxlJobInfo();
        jobInfo.setJobDesc(jobDesc);
        jobInfo.setAuthor(SecurityUtils.getOperatorName());
        jobInfo.setAlarmEmail("default@ddd.com");
        jobInfo.setScheduleType("CRON");
        jobInfo.setScheduleConf(scheduleConf);
        jobInfo.setMisfireStrategy("DO_NOTHING");
        jobInfo.setExecutorRouteStrategy("FIRST");
        jobInfo.setExecutorHandler(executorHandler);
        jobInfo.setExecutorParam(executorParam);
        jobInfo.setExecutorBlockStrategy("SERIAL_EXECUTION");
        jobInfo.setExecutorTimeout(60);
        jobInfo.setExecutorFailRetryCount(3);
        jobInfo.setGlueType("BEAN");
        jobInfo.setGlueSource("");
        jobInfo.setGlueRemark("首次创建");

        xxlJobUtils.add(jobInfo);
    }
}
