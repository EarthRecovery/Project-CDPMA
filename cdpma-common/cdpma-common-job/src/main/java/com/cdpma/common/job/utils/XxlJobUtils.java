//package com.cdpma.common.job.utils;
//
//import com.cdpma.common.job.constant.XxlJobConstant;
//import com.cdpma.common.job.domain.XxlJobRegister;
//import java.util.*;
//import java.util.HashMap;
//import java.util.Map;
//
//public class XxlJobUtils {
//
//    public static void createCronTypeTask(String jobDesc, String scheduleConf, String executorHandler) {
//        XxlJobRegister xxlJobRegister = new XxlJobRegister();
//        xxlJobRegister.setJobGroup(1);
//        xxlJobRegister.setAuthor("cdpma");
//        xxlJobRegister.setGlueType("BEAN");
//        xxlJobRegister.setJobDesc(jobDesc);
//        xxlJobRegister.setScheduleType("CRON");
//        xxlJobRegister.setScheduleConf(scheduleConf);
//        xxlJobRegister.setExecutorHandler(executorHandler);
//        xxlJobRegister.setExecutorRouteStrategy("ROUND");
//        xxlJobRegister.setMisfireStrategy("DO_NOTHING");
//        xxlJobRegister.setExecutorTimeout(0);
//        xxlJobRegister.setExecutorFailRetryCount(0);
//
//        register(xxlJobRegister);
//
//    }
//}
