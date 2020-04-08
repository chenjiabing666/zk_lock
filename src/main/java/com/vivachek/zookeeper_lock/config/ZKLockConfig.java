package com.vivachek.zookeeper_lock.config;

import com.vivachek.zookeeper_lock.lock.ZKLockMutex;
import com.vivachek.zookeeper_lock.lock.ZKLockRW;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description TODO
 * @Author CJB
 * @Date 2020/4/8 14:36
 */
@Configuration
public class ZKLockConfig {
    @Bean
    public ZKLockMutex zkLockMutex(CuratorFramework curatorFramework){
       return new ZKLockMutex("/test1",curatorFramework);
    }

    @Bean
    public ZKLockRW zkLockRW(CuratorFramework curatorFramework){
        return new ZKLockRW("/test2",curatorFramework);
    }
}