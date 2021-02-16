1. 编写业务执行代码
    ```java
    @Component
    public class AMethodClass {
        public static String execute(String str) {
            return str.concat("-result");
        }
        public String execute2(String str) {
            return str.concat("-result2");
        }
    }
    ```
2. 编写配置类
    ```java
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
    ```
3. 静态方法调用（单元测试）
    ```java
    public class MethodInvokeTest {
        /*
         * 注意看配置文件
         * 获取配置的MethodInvoker;这个bean配置的是targetClass,要求targetMethod必须是静态方法
         * */
        @Test
        public void staticMethodInvoke() throws Exception {
            BeanFactory beanFactory = new AnnotationConfigApplicationContext(AConfig.class);
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
        
    }
    ```
4. 实例方法调用（单元测试）
    ```java
    public class MethodInvokeTest {
        /*
         * 注意看配置文件
         * 获取配置的MethodInvoker;这个bean配置的是targetObject,则需要先配置一个bean,这里ref到这个bean
         * targetMethod可以不是静态的
         * */
        @Test
        public void instanceMethodInvoke() throws Exception{
            BeanFactory beanFactory = new AnnotationConfigApplicationContext(AConfig.class);
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
    }
    ```
5. 但一般情况不直接使用MethodInvoker，使用MethodInvokingFactoryBean（单元测试）
    ```java
    public class MethodInvokeTest {
        /*
         * 使用MethodInvokingFactoryBean;相当于spring帮你做了上述逻辑;
         * 可以直接获取到方法的执行结果;
         * 注意这里是方法的返回值;而不是class本身;这是由于MethodInvokingFactoryBean实现了FactoryBean接口;
         * 由接口方法getObject()来获取最终返回的对象
         */
        @Test
        public void factoryBeanInvoke(){
            BeanFactory beanFactory = new AnnotationConfigApplicationContext(AConfig.class);
            Object aMethod3 = beanFactory.getBean("aMethod3");
            System.out.println(aMethod3);
        }
    }
    ```
6. 参考博客：https://blog.csdn.net/iteye_11480/article/details/82133807