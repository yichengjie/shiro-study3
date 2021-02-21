package com.yicj.study.aspectjautoproxy;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class MyAnnotationAwareAspect {

    @Around("execution(* com.yicj.study.common.service.*.*(..))")
    public Object process(ProceedingJoinPoint point) throws Throwable {
        String clazzName = point.getTarget().getClass().getSimpleName();
        String methodName = point.getSignature().getName();
        log.info("hello BeanNameAutoProxyCreator ,i come,clazzName [{}] methodName [{}]",clazzName,methodName);
        log.info("调用方法前");
        Object ret = point.proceed();
        log.info("调用方法后");
        return ret ;
    }
}