package com.yicj.study.aop.service;

import com.yicj.study.aop.anno.SystemLogger;
import com.yicj.study.aop.entity.Log;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Slf4j
@Component
public class AopLogService extends StaticMethodMatcherPointcutAdvisor {

    public AopLogService() {
        this.setAdvice((MethodInterceptor) (methodInvocation) -> {
            Log logInfo = this.createLog(methodInvocation);
            log.info("info : {}", logInfo);
            //这里将日志Log保存或者通过事件机制通知，异步进行处理
            return methodInvocation.proceed();
        });
    }

    private Log createLog(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Object aThis = invocation.getThis();
        SystemLogger methodAnnotation = findAnnotation(method, SystemLogger.class);
        String description = methodAnnotation.description();
        Log logInfo = new Log();
        logInfo.setDescp(description);
        logInfo.setMethod(method.getName());
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestURI = request.getRequestURI();
        logInfo.setUrl(requestURI);
        return logInfo;
    }

    //matches方法来判断是否切入，true为切入，false不切入。
    @Override
    public boolean matches(Method method, Class<?> aClass) {
        SystemLogger annotation = findAnnotation(method, SystemLogger.class);
        return annotation != null;
    }

    private SystemLogger findAnnotation(Method method, Class<SystemLogger> systemLoggerClass) {
        SystemLogger annotation = method.getAnnotation(systemLoggerClass);
        return annotation ;
    }
}