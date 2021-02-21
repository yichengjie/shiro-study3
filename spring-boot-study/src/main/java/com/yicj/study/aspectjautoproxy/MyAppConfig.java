package com.yicj.study.aspectjautoproxy;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy //注入AnnotationAwareAspectJAutoProxyCreator
@ComponentScan("com.yicj.study.common.service")
public class MyAppConfig {

}
