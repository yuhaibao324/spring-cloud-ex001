package com.wisely.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients //1  开启feign客户端支持
@EnableCircuitBreaker //2 开启CircuitBreaker支持
@EnableZuulProxy //3 开启网关代理的支持
public class UiApplication {

    /*断路器 ：(Circuit Breaker)，主要是为了解决当某个方法调用失败的时候，调用后备方法来替代失败的方法，以达到容错、阻止级联错误等功能。
          Spring Cloud使用@EnableCircuitBreaker来启用断路器支持，使用@HystrixCommand的fallbackMethod来指定后备方法。
          通过@EnableHystrixDashboard注解开启一个控制台来监控断路器的运行情况。*/
    public static void main(String[] args) {
        SpringApplication.run(UiApplication.class, args);
    }
}
