package com.li.yun.biao.goods;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;

@ImportResource("classpath:spring/applicationContext.xml")
@EnableCaching
@EnableAsync
@SpringBootApplication
@MapperScan(basePackages = "com.li.yun.biao.goods.mapper")
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
