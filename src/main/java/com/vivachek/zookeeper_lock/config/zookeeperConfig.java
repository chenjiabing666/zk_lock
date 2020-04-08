package com.vivachek.zookeeper_lock.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

/**
 * @Description TODO
 * @Author CJB
 * @Date 2020/4/7 22:30
 */
@Configuration
public class zookeeperConfig  {

    @Bean
    public CuratorFramework curatorFramework(){
        CuratorFramework  client = CuratorFrameworkFactory.builder()
                //ip地址+端口号，如果是集群，逗号分隔
                .connectString("127.0.0.1:2181")
                //会话超时时间
                .sessionTimeoutMs(5000)
                //超时重试策略,RetryOneTime：超时重连仅仅一次
                .retryPolicy(new RetryOneTime(3000))
                //命名空间，父节点，如果不指定是在根节点下
                .namespace("lock")
                .build();
        client.start();
        return client;
    }


//    @PreDestroy
//    public void destroyClient(CuratorFramework curatorFramework){
//        System.err.println("d.............................................");
//        curatorFramework.close();
//    }

}