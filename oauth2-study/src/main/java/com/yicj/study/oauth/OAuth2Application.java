package com.yicj.study.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;

//https://github.com/zhangkaitao/shiro-example
@SpringBootApplication(exclude = AopAutoConfiguration.class)
public class OAuth2Application {
    public static void main(String[] args) {
        SpringApplication.run(OAuth2Application.class, args) ;
    }
}
