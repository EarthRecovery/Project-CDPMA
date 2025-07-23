package com.cdpma.system.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@MapperScan("com.cdpma.**.mapper")
@SpringBootApplication(scanBasePackages = {"com.cdpma"})
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableDiscoveryClient
public class CdpmaSystemUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(CdpmaSystemUserApplication.class, args);

    }
}
