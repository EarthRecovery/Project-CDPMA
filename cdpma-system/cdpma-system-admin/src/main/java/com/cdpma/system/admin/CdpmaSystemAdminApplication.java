package com.cdpma.system.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = "com.cdpma")
@MapperScan("com.cdpma.**.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableFeignClients(basePackages = "com.cdpma.api")
@EnableDiscoveryClient
public class CdpmaSystemAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(CdpmaSystemAdminApplication.class, args);
    }

}
