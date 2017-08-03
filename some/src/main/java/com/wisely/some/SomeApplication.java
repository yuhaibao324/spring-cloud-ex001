package com.wisely.some;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class SomeApplication {
    @Value("${my.message}") //1 开发环境下，这个值来自Config Server下的some.yml；在Docker生产环境下，来自Config Server下的some-docker.yml
    private String message;

    @RequestMapping(value = "/getsome")
    public String getsome() {
        return message;
    }

    public static void main(String[] args) {
        SpringApplication.run(SomeApplication.class, args);
    }

}
