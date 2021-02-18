1. 编写切面实现
    ```java
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
    ```
2. 要创建代理的目标类与接口
    ```java
    public interface UserService {
        void print();
    }
    @Slf4j
    @Service("userService")
    public class UserServiceImpl implements UserService {
        public void print(){
            log.info("hello busi");
        }
    }
    ```
3. 编写配置类
    ```java
    @Configuration
    @ComponentScan("com.yicj.study.common")
    public class AppConfig {
        //使用Advice创建Advisor
        @Bean
        public NameMatchMethodPointcutAdvisor nameMatchMethodPointcutAdvisor(MyMethodInterceptor myMethodInterceptor){
            NameMatchMethodPointcutAdvisor nameMatchMethodPointcutAdvisor=new NameMatchMethodPointcutAdvisor();
            nameMatchMethodPointcutAdvisor.setMappedName("print*");
            nameMatchMethodPointcutAdvisor.setAdvice(myMethodInterceptor);
            return nameMatchMethodPointcutAdvisor;
        }
        @Bean
        public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
            return new DefaultAdvisorAutoProxyCreator();
        }
    }
    ```
4. 测试
    ```java
    @SpringBootApplication(exclude = AopAutoConfiguration.class)
    public class BeanNameAutoProxyApp implements ApplicationRunner {
        @Autowired
        private UserService userService ;
        public static void main(String[] args) {
            SpringApplication.run(BeanNameAutoProxyApp.class, args) ;
        }
        @Override
        public void run(ApplicationArguments args) throws Exception {
            userService.print();
        }
    }
    ```
5. 结果输出
    ```text
    c.y.s.common.advice.MyMethodInterceptor  : 调用方法前
    c.y.s.c.service.impl.UserServiceImpl     : hello busi
    c.y.s.common.advice.MyMethodInterceptor  : 调用方法后
    ```
6. 备注
   > BeanNameAutoProxyCreator,DefaultAdvisorAutoProxyCreator,AbstractAdvisorAutoProxyCreator
   @EnableAspectJAutoProxy注解通过@Import将AnnotationAwareAspectJAutoProxyCreator类注册到容器
