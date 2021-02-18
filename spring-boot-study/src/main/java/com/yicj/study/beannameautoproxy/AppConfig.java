package com.yicj.study.beannameautoproxy;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.yicj.study.common")
public class AppConfig {

    //使用BeanNameAutoProxyCreator来创建代理
    @Bean
    public BeanNameAutoProxyCreator beanNameAutoProxyCreator(){
        BeanNameAutoProxyCreator beanNameAutoProxyCreator=new BeanNameAutoProxyCreator();
        //设置要创建代理的那些Bean的名字
        beanNameAutoProxyCreator.setBeanNames("userSer*");
        //设置拦截链名字(这些拦截器是有先后顺序的)
        beanNameAutoProxyCreator.setInterceptorNames("myMethodInterceptor");
        return beanNameAutoProxyCreator;
    }
}
