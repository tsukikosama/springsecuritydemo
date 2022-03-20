package com.miku.springsecuritydemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.miku.springsecuritydemo.mapper")
public class SpringsecuritydemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringsecuritydemoApplication.class, args);
    }

}
