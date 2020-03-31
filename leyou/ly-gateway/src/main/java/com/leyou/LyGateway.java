package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import sun.management.Agent;

@SpringCloudApplication
@EnableZuulProxy
public interface LyGateway {

    public static void main(String[] args) {
        SpringApplication.run(LyGateway.class, args);
    }

}
