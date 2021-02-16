package com.yicj.study.shiro.methodinvoke;

import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.MethodInvoker;

@Configuration
@ComponentScan("com.yicj.study.shiro.methodinvoke")
public class AConfig {
    @Bean
    public MethodInvoker aMethod(){
        MethodInvoker invoker = new MethodInvoker() ;
        invoker.setTargetClass(AMethodClass.class);
        invoker.setTargetMethod("execute");
        return invoker ;
    }
    @Bean
    public MethodInvoker aMethod2(AMethodClass aMethod2Class){
        MethodInvoker invoker = new MethodInvoker() ;
        invoker.setTargetObject(aMethod2Class);
        invoker.setTargetMethod("execute2");
        return invoker ;
    }
    @Bean
    public MethodInvokingFactoryBean aMethod3(){
        MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean() ;
        factoryBean.setTargetClass(AMethodClass.class);
        factoryBean.setTargetMethod("execute");
        factoryBean.setArguments("test3");
        return factoryBean ;
    }
}
