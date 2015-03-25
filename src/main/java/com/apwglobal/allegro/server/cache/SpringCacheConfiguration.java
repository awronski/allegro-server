package com.apwglobal.allegro.server.cache;

import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class SpringCacheConfiguration implements CachingConfigurer {

    private static final String LRU = "LRU";

    @Bean(destroyMethod="shutdown")
    public net.sf.ehcache.CacheManager ehCacheManager() {

        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
        config.addCache(createCacheConfiguration("journals", 1));
        config.addCache(createCacheConfiguration("auctions", 1));
        config.addCache(createCacheConfiguration("deals", 1));
        config.addCache(createCacheConfiguration("forms", 1));
        config.addCache(createCacheConfiguration("form-fields", 100, 3600));

        return net.sf.ehcache.CacheManager.newInstance(config);
    }

    private CacheConfiguration createCacheConfiguration(String cacheName, int maxEntries, int ttl) {
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setName(cacheName);
        cacheConfiguration.setMemoryStoreEvictionPolicy(LRU);
        cacheConfiguration.setMaxEntriesLocalHeap(maxEntries);
        cacheConfiguration.setEternal(false);
        cacheConfiguration.setTimeToIdleSeconds(ttl);
        cacheConfiguration.setTimeToLiveSeconds(ttl);
        return cacheConfiguration;
    }

    private CacheConfiguration createCacheConfiguration(String cacheName, int maxEntries) {
        return createCacheConfiguration(cacheName, maxEntries, 60);
    }

    @Bean
    @Override
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheManager());
    }

    @Override
    public CacheResolver cacheResolver() {
        return new SimpleCacheResolver(cacheManager());
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return new SimpleCacheErrorHandler();
    }
}
