package com.cdpma.common.job.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XxlJobConfig {

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        XxlJobSpringExecutor executor = new XxlJobSpringExecutor();
        executor.setAdminAddresses("http://localhost:8090/xxl-job-admin");
        executor.setAppname("cdpma-executor");
        executor.setPort(9999);
        executor.setLogPath("C:\\Users\\Yuxia\\Desktop\\CS-Project\\projectCDP-MA\\Code\\CDPMA\\log");
        executor.setLogRetentionDays(30);
        executor.setAccessToken("default_token");
        return executor;
    }
}
