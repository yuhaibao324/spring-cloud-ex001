1.微服务的含义：使用定义好边界的小的独立组件来做好一件事情。微服务是相对于传统单块式架构而言的。
单块式架构：是一份代码，部署和伸缩都是基于单个单元进行的。
     优点：易于部署
     缺点：可用性低、可伸缩性差、集中发布的生命周期以及违反单一功能原则
微服务：解决了单块式架构的缺点，它以单个独立的服务来做一个功能，且要做好这个功能。
        但不可避免地将功能按照边界拆分为单个服务，体现出分布式的特征，这时每个微服务之间的通信将是我们要解决的问题。
Spring Cloud基于Spring Boot，特别适合在Docker或其他专业Paas(平台即服务)部署，
     它为我们提供了配置管理、服务发现、断路器、代理服务等我们在做分布式开发时常用问题的解决方案。

2.各种服务
1.配置服务：提供了Config Server，它有在分页式系统开发中外部配置的功能。通过Config Server，我们可以集中存储所有应用的配置文件 。
2.服务发现：通过Netflix OSS的Eureka来实现服务发现，目的是为了让每个服务之前可以互相通信。Eureka为微服务注解中心
           使用注解方式提供了Eureka服务端(@EnableEurekaServer)和客户端(@EnableEurekaClient)
3.路由网关：主要目的是为了让所有的微服务对外只有一个接口，我们只需访问一个网关地址，即可由网关将我们的请求代理到不同的服务器中。
           通过Zuul来实现的，支持自动路由器映射到在Eureka Server上注册的服务。提供了注解@EnableZuulProxy来启用路由代理。
4.负载均衡：提供了Ribbon和Feign作为客户端的负载均衡。
           在Spring Cloud下，使用Ribbon直接注入一个RestTemplate对象即可，此RestTemplate已做好负载均衡的配置；
           使用Feign只需定义个注解，有@FeignClient注解的接口，然后使用@RequestMapping注解在方法上映射远程的REST服务，此方法也是做好负载均衡配置的。
5.断路器 ：(Circuit Breaker)，主要是为了解决当某个方法调用失败的时候，调用后备方法来替代失败的方法，以达到容错、阻止级联错误等功能。
          Spring Cloud使用@EnableCircuitBreaker来启用断路器支持，使用@HystrixCommand的fallbackMethod来指定后备方法。
          通过@EnableHystrixDashboard注解开启一个控制台来监控断路器的运行情况。
          
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
启动顺序：DiscoveryApplication、ConfigApplication，后面所有的微服务不分顺序，最后启动MonitorApplication。

此时访问http://localhost:8761，查看Eureka Server

1.访问UI服务
    UI服务既是我们的页面，也是我们的网关代理。在实际生产环境中，服务器防火墙只需将此端口暴露给外网即可，访问http://localhost 
1).调用Person Service , http://localhost/#/person
2).调用Some Service , http://localhost/#/some

2.断路器
    此时停止Person Service和Some Service, 观察断路器的效果，请求URI跟上面的1,2一样。

3.断路器监控
    1).访问http://localhost:8989/hystrix.stream
    2).访问http://localhost/hystrix.stream


++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
基于Docker部署
    以Spring Cloud开发的微服务程序十分适合在Docker环境下部署
    
上面的6个微服务的Dockfile的编写几乎完全一致
1.runboot.sh
sleep 10  根据启动顺序，调整sleep的时间

2.Dockerfile编写
为不同的微服务我们只需要修改：
1)ADD ui-1.0.0-SNAPSHOT.jar /app/app.jar
2)expose 80

3.Docker的maven插件
     在开发机器编译Docker镜像到服务器，使用docker-maven-plugin即可，在所有的程序的pom.xml内增加
    <build>
        <plugins>
            <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>docker-maven-plugin</artifactId>
                <configuration>
                    <imageName>${project.name}:${project.version}</imageName>
                    <dockerDirectory>${project.basedir}/src/main/docker</dockerDirectory>
                    <skipDockerBuild>false</skipDockerBuild>
                    <resources>
                        <resource>
                            <directory>${project.build.directory}</directory>
                            <include>${project.build.finalName}.jar</include>
                        </resource>
                    </resources>
                </configuration>
            </plugin>
        </plugins>
    </build>

4.编译镜像
     使用docker-maven-plugin，默认将Docker编译到localhost。如果是远程Linux服务器，请在环境变量中配置DOCKER_HOST, 如tcp://192.168.1.68:2375
     编译完成后执行：mvn clean package docker:build -DskipTests

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
Docker-compose.yml编写
    enviroment: 给容器使用的变量，在容器中使用${}来调用。
    links: 当前容器依赖的容器，可直接使用依赖容器的已有端口。
    ports：将我们要暴露的端口映射出来，不需要暴露的端口则不做映射。
    
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
运行
    将docker-compose.yml上传至Linux服务器上，在文件当前目录执行下面命令
    docker-compose up -d

这时我们可以在本地访问http://192.168.1.68:8761和http://192.168.1.68，











