package com.yicj.study.oauth.config;

import com.yicj.study.oauth.common.SpringCacheManagerWrapper;
import com.yicj.study.oauth.shiro.RetryLimitHashedCredentialsMatcher;
import com.yicj.study.oauth.shiro.UserRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.quartz.QuartzSessionValidationScheduler;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //<!-- 缓存管理器 -->
    @Bean
    public SpringCacheManagerWrapper cacheManager(EhCacheCacheManager springCacheManager){
        SpringCacheManagerWrapper cacheManagerWrapper = new SpringCacheManagerWrapper();
        cacheManagerWrapper.setCacheManager(springCacheManager);
        return cacheManagerWrapper ;
    }
    //<!-- 凭证匹配器 -->
    @Bean
    public RetryLimitHashedCredentialsMatcher credentialsMatcher(SpringCacheManagerWrapper cacheManager){
        RetryLimitHashedCredentialsMatcher credentialsMatcher = new RetryLimitHashedCredentialsMatcher(cacheManager) ;
        credentialsMatcher.setHashAlgorithmName("md5");
        credentialsMatcher.setHashIterations(2);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher ;
    }

    // <!-- Realm实现 -->
    @Bean
    public UserRealm userRealm(RetryLimitHashedCredentialsMatcher credentialsMatcher){
        UserRealm userRealm = new UserRealm() ;
        userRealm.setCredentialsMatcher(credentialsMatcher);
        userRealm.setCachingEnabled(false);
        return userRealm ;
    }

    @Bean
    public JavaUuidSessionIdGenerator sessionIdGenerator(){
        return new JavaUuidSessionIdGenerator() ;
    }

    @Bean
    public SimpleCookie sessionIdCookie(){
        SimpleCookie simpleCookie = new SimpleCookie("sid") ;
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(-1);
        return simpleCookie ;
    }

    @Bean
    public SimpleCookie rememberMeCookie(){
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe") ;
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(2592000); //30 天
        return simpleCookie ;
    }

    //<!-- rememberMe管理器 -->
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager() ;
        byte[] decode = Base64.decode("4AvVhmFLUs0KTA3Kprsdag==");
        rememberMeManager.setCipherKey(decode);
        rememberMeManager.setCookie(rememberMeCookie());
        return rememberMeManager ;
    }


    @Bean
    public EnterpriseCacheSessionDAO sessionDAO(){
        EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO() ;
        sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        sessionDAO.setSessionIdGenerator(sessionIdGenerator());
        return sessionDAO ;
    }

    //<!-- 会话管理器 -->
    @Bean
    public DefaultWebSessionManager sessionManager(){
        DefaultWebSessionManager manager = new DefaultWebSessionManager() ;
        manager.setGlobalSessionTimeout(1800000);
        manager.setDeleteInvalidSessions(true);
        manager.setSessionValidationSchedulerEnabled(true);
        manager.setSessionDAO(sessionDAO());
        manager.setSessionIdCookieEnabled(true);
        manager.setSessionIdCookie(sessionIdCookie());
        return manager ;
    }

    // <!-- 会话验证调度器 -->
    @Bean
    public QuartzSessionValidationScheduler sessionValidationScheduler(){
        QuartzSessionValidationScheduler scheduler = new QuartzSessionValidationScheduler() ;
        scheduler.setSessionValidationInterval(1800000);
        scheduler.setSessionManager(sessionManager());
        return scheduler ;
    }

    //<!-- 安全管理器 -->
    @Bean
    public DefaultWebSecurityManager securityManager(UserRealm userRealm,SpringCacheManagerWrapper cacheManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager() ;
        securityManager.setRealm(userRealm);
        securityManager.setSessionManager(sessionManager());
        securityManager.setCacheManager(cacheManager);
        securityManager.setRememberMeManager(rememberMeManager());

        SecurityUtils.setSecurityManager(securityManager) ;
        return securityManager ;
    }

    //<!-- 基于Form表单的身份验证过滤器 -->
    @Bean
    public FormAuthenticationFilter formAuthenticationFilter(){
        FormAuthenticationFilter filter = new FormAuthenticationFilter() ;
        filter.setUsernameParam("username");
        filter.setPasswordParam("password");
        filter.setRememberMeParam("rememberMe");
        filter.setLoginUrl("/login");
        return filter ;
    }

    //DefaultFilter
    //<!-- Shiro的Web过滤器 -->
    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean() ;
        shiroFilter.setSecurityManager(securityManager);
        shiroFilter.setLoginUrl("/login");
        // filters
        Map<String, Filter> filters = new HashMap<>();
        filters.put("authc",formAuthenticationFilter());
        shiroFilter.setFilters(filters);
        //filterChainDefinitionsMap
        Map<String,String> map = new HashMap<>() ;
        map.put("/", "anno") ;
        map.put("/login", "authc") ;
        map.put("/logout", "logout") ;
        map.put("/authorize","anno") ;
        map.put("/accessToken", "anno") ;
        map.put("/userInfo", "anno") ;
        map.put("/**", "user") ;
        shiroFilter.setFilterChainDefinitionMap(map);
        return shiroFilter ;
    }

    // <!-- Shiro生命周期处理器-->
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor() ;
    }
}
