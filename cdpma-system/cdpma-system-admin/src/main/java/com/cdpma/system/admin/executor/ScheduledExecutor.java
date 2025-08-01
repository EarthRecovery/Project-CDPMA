package com.cdpma.system.admin.executor;

import com.cdpma.common.job.utils.XxlJobUtils;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduledExecutor {

    public void  testExecutor(Object args){
//        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//        Runnable task = () -> System.out.println("任务在5秒后执行！");
//        scheduler.schedule(task, 5, TimeUnit.SECONDS);
        System.out.println("ScheduledExecutor testExecutor: " + args);
    }
}
