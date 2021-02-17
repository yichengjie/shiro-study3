1. 编写核心类PointcutAdvisor
    ```java
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
    ```
2. 编写注解公用日志类
    ```java
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface SystemLogger {
        String description() default "";
    }
    @Data
    public class Log {
        private String descp ;
        private String method ;
        private String url ;
    }
    ```
3. 编写业务处理代码
    ```java
    @RestController
    @RequestMapping("/student")
    public class StudentController {
        @GetMapping(path = "/ok")
        @SystemLogger(description = "查询学生")    //注解方法处
        public String select() {
            return "ok";
        }
    }
    ```
4. 源码学习实例
    ```text
    4.1 AuthorizationAttributeSourceAdvisor
    4.2 AopAllianceAnnotationsAuthorizingMethodInterceptor
    ```