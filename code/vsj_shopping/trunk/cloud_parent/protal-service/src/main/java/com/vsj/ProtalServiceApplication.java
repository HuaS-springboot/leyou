package com.vsj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProtalServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProtalServiceApplication.class, args);
    }

}
