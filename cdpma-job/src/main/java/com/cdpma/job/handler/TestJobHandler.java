package com.cdpma.job.handler;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

@Component
public class TestJobHandler {
    @XxlJob("testJobHandler")
    public void testJobHandler(String param) throws Exception {
        System.out.println("执行 XXL-JOB 任务，接收参数：" + param);
    }
}
