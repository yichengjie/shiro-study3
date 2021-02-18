package com.yicj.study.beannameautoproxy;

import com.yicj.study.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;

@SpringBootApplication(exclude = AopAutoConfiguration.class)
public class BeanNameAutoProxyApp implements ApplicationRunner {
    @Autowired
    private UserService userService ;

    public static void main(String[] args) {
        SpringApplication.run(BeanNameAutoProxyApp.class, args) ;
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        userService.print();
    }
}
