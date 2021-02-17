package com.yicj.study.oauth.config;

import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class CacheConfig {

    @Bean
    public EhCacheCacheManager springCacheManager(EhCacheManagerFactoryBean ehcacheManager){
        EhCacheCacheManager cacheCacheManager = new EhCacheCacheManager() ;
        cacheCacheManager.setCacheManager(ehcacheManager.getObject());
        return cacheCacheManager ;
    }

    @Bean
    public EhCacheManagerFactoryBean ehcacheManager(){
        EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean() ;
        Resource resource = new ClassPathResource("ehcache/ehcache.xml");
        factoryBean.setConfigLocation(resource);
        return factoryBean ;
    }
}
