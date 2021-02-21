1. 编写切面实现
    ```java
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
    ```
2. 要创建代理的目标类与接口
    ```java
    public interface UserService {
        void print();
    }
    @Service("userService")
    public class UserServiceImpl implements UserService {
        public void print(){
            log.info("hello busi");
        }
    }
    ```
3. 编写配置类（也可使用@EnableAspectJAutoProxy引入AnnotationAwareAspectJAutoProxyCreator）
    ```java
    @Configuration
    @ComponentScan("com.yicj.study.common.service")
    public class MyAppConfig {
        @Bean
        public AbstractAutoProxyCreator autoProxyCreator(){
            AbstractAutoProxyCreator autoProxyCreator = new AnnotationAwareAspectJAutoProxyCreator();
            autoProxyCreator.setProxyTargetClass(true);
            return autoProxyCreator ;
        }
    }
    ```
4. 测试
    ```java
    @SpringBootApplication
    public class AspectJAwareAdvisorAutoProxyApp implements ApplicationRunner {
        @Autowired
        private UserService userService ;
        public static void main(String[] args) {
            SpringApplication.run(AspectJAwareAdvisorAutoProxyApp.class, args) ;
        }
        @Override
        public void run(ApplicationArguments args) throws Exception {
            userService.print();
        }
    }
    ```
5. 结果输出
   ```text
   c.y.s.a.MyAnnotationAwareAspect          : hello BeanNameAutoProxyCreator ,i come,clazzName [UserServiceImpl] methodName [print]
   c.y.s.a.MyAnnotationAwareAspect          : 调用方法前
   c.y.s.c.service.impl.UserServiceImpl     : hello busi
   c.y.s.a.MyAnnotationAwareAspect          : 调用方法后
   ```
6. 注意事项
    ```text
    使用@AspectJ形式得aop，需要将aspectjweaver.jar和aspectjrt.jar加入到classpath中
    ```   
