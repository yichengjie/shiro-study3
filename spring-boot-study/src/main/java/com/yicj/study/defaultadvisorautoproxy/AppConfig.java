package com.yicj.study.defaultadvisorautoproxy;

import com.yicj.study.common.advice.MyMethodInterceptor;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.yicj.study.common")
public class AppConfig {
    //使用Advice创建Advisor
    @Bean
    public NameMatchMethodPointcutAdvisor nameMatchMethodPointcutAdvisor(MyMethodInterceptor myMethodInterceptor){
        NameMatchMethodPointcutAdvisor nameMatchMethodPointcutAdvisor=new NameMatchMethodPointcutAdvisor();
        nameMatchMethodPointcutAdvisor.setMappedName("print");
        nameMatchMethodPointcutAdvisor.setAdvice(myMethodInterceptor);
        return nameMatchMethodPointcutAdvisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        return new DefaultAdvisorAutoProxyCreator();
    }
}
