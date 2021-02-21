package com.yicj.study.aspectjautoproxy;

import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.yicj.study.common.service")
//@EnableAspectJAutoProxy //注入AnnotationAwareAspectJAutoProxyCreator
public class MyAppConfig {
    @Bean
    public AbstractAutoProxyCreator autoProxyCreator(){
        AbstractAutoProxyCreator autoProxyCreator = new AnnotationAwareAspectJAutoProxyCreator();
        autoProxyCreator.setProxyTargetClass(true);
        return autoProxyCreator ;
    }
}
