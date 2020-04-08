package com.vivachek.zookeeper_lock.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Author CJB
 * @Date 2020/4/7 23:25
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {
    public static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtils.applicationContext=applicationContext;
    }
}