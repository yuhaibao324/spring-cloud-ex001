package com.wisely.person;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PersonApplication {

    //# 在开发环境下使用hsqldb(Config Server下的person.yml), 在Docker生产环境下使用PostgreSQL（Config Server下的person-docker.yml）
    public static void main(String[] args) {
        SpringApplication.run(PersonApplication.class, args);
    }

}
