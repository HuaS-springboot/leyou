package com.vsj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableFeignClients
public class QuartzServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuartzServiceApplication.class, args);
    }

}
