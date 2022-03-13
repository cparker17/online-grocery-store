package com.parker.managementmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ManagementMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagementMicroserviceApplication.class, args);
    }

}
