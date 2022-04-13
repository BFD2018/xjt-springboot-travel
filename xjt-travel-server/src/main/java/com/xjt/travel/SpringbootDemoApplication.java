package com.xjt.travel;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@MapperScan("com.xjt.travel.mapper")
@SpringBootApplication
public class SpringbootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDemoApplication.class, args);
        log.warn("springboot后台服务URL：http://localhost:8880");
        log.warn("后台管理员系统：http://localhost:8001");
        log.warn("前台页面展示：http://localhost:8002");
    }
}
