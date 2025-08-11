package com.cdpma.stat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = {"com.cdpma"})
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableFeignClients(basePackages = "com.cdpma.api")
@EnableDiscoveryClient
public class CdpmaStatApplication {

    public static void main(String[] args) {
        SpringApplication.run(CdpmaStatApplication.class, args);
    }

}
