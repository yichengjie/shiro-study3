package com.yicj.study.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

//创建Advice或Advisor
@Slf4j
@Component("myMethodInterceptor")
public class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("调用方法前");
        Object ret=invocation.proceed();
        log.info("调用方法后");
        return ret;
    }
}
