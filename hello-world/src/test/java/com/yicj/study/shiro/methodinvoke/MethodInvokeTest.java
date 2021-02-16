package com.yicj.study.shiro.methodinvoke;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.MethodInvoker;

public class MethodInvokeTest {
    private BeanFactory beanFactory;
    @Before
    public void before(){
       beanFactory = new AnnotationConfigApplicationContext(AConfig.class);
    }
    /*
     * 注意看配置文件
     * 获取配置的MethodInvoker;这个bean配置的是targetClass,要求targetMethod必须是静态方法
     * */
    @Test
    public void staticMethodInvoke() throws Exception {
        MethodInvoker method = (MethodInvoker) beanFactory.getBean("aMethod");
        //下来可以自己手工设置方法参数
        Object[] arguments = new Object[1];
        arguments[0] = "test";
        method.setArguments(arguments);
        // 准备方法
        method.prepare();
        //执行方法
        Object result = method.invoke();
        System.out.println(result);
    }

    /*
     * 注意看配置文件
     * 获取配置的MethodInvoker;这个bean配置的是targetObject,则需要先配置一个bean,这里ref到这个bean
     * targetMethod可以不是静态的
     * */
    @Test
    public void instanceMethodInvoke() throws Exception{
        MethodInvoker method2 = (MethodInvoker) beanFactory.getBean("aMethod2");
        //下来可以自己手工设置方法参数
        Object[] arguments2 = new Object[1];
        arguments2[0] = "test2";
        method2.setArguments(arguments2);
        // 准备方法
        method2.prepare();
        //执行方法
        Object result = method2.invoke();
        System.out.println(result);
    }

    /*
     * 使用MethodInvokingFactoryBean;相当于spring帮你做了上述逻辑;
     * 可以直接获取到方法的执行结果;
     * 注意这里是方法的返回值;而不是class本身;这是由于MethodInvokingFactoryBean实现了FactoryBean接口;
     * 由接口方法getObject()来获取最终返回的对象
     */
    @Test
    public void factoryBeanInvoke(){
        Object aMethod3 = beanFactory.getBean("aMethod3");
        System.out.println(aMethod3);
    }
}
