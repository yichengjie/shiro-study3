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

    @Around("execution(* com.jd.plugin.dao.aop.*.*(..))")
    public Object process(ProceedingJoinPoint point) throws Throwable {
        String clazzName = point.getTarget().getClass().getSimpleName();
        String methodName = point.getSignature().getName();
        log.debug("hello BeanNameAutoProxyCreator ,i come,clazzName [{}] methodName [{}]",clazzName,methodName);
        return point.proceed();
    }
}