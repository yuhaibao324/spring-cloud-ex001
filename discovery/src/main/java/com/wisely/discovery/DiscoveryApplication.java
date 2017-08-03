package com.wisely.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 服务发现：
 * 通过Netflix OSS的Eureka来实现服务发现，目的是为了让每个服务之前可以互相通信。Eureka为微服务注解中心.
 * 使用注解方式提供了Eureka服务端(@EnableEurekaServer)和客户端(@EnableEurekaClient)
 */
@SpringBootApplication
@EnableEurekaServer //1，开启EurekaServer的支持
public class DiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryApplication.class, args);
    }

}
