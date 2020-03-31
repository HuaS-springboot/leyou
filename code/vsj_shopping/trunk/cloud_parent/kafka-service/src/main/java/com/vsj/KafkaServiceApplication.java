package com.vsj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
public class KafkaServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(KafkaServiceApplication.class, args);
    }

}
