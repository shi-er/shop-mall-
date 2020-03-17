package com.li.yun.biao.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

@ImportResource("classpath:spring/applicationContext.xml")
@SpringBootApplication
public class ServerStart extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(ServerStart.class, args);
    }

    /**
     * 产生一个可部署的war包
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ServerStart.class);
    }
}
