package com.cdpma.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.cdpma")
@EnableFeignClients(basePackages = "com.cdpma.api")
public class CdpmaAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(CdpmaAuthApplication.class, args);
    }

}
