package com.vivachek.zookeeper_lock.config;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @Description TODO
 * @Author CJB
 * @Date 2020/4/7 22:47
 */
@Component
public class ApplicationCloseListener implements ApplicationListener<ContextClosedEvent> {
    @Autowired
    private CuratorFramework curatorFramework;
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.err.println("----------------------------");
        curatorFramework.close();
    }
}