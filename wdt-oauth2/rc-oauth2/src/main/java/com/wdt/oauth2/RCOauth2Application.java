package com.wdt.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient //打开服务发现
@SpringBootApplication
public class RCOauth2Application {
    public static void main(String[] args) {
        SpringApplication.run(RCOauth2Application.class, args);
    }
}