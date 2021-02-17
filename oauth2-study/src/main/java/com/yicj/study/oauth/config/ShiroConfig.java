package com.yicj.study.oauth.config;

import com.yicj.study.oauth.common.SpringCacheManagerWrapper;
import com.yicj.study.oauth.shiro.RetryLimitHashedCredentialsMatcher;
import com.yicj.study.oauth.shiro.UserRealm;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

}
