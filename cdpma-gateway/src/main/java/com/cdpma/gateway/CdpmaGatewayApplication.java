package com.cdpma.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication(scanBasePackages = {"com.cdpma.gateway", "com.cdpma.common"})
public class CdpmaGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CdpmaGatewayApplication.class, args);
    }

}
