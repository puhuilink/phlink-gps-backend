package com.xd.pre;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 系统入口
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.xd.pre.flowable", "com.xd.pre.flowable.common", "com.xd.pre.modules", "com.xd.pre.common"})
@MapperScan(basePackages = {"com.xd.pre.flowable.mapper"})
public class PreSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(PreSystemApplication.class, args);
    }

}
