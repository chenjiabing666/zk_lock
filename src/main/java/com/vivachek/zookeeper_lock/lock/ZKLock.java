package com.vivachek.zookeeper_lock.lock;

/**
 * @Description ZK分布式锁的接口
 * @Author 陈某
 * @Date 2020/4/7 22:52
 */
public interface ZKLock {
    /**
     * 获取锁
     */
    void lock() throws Exception;

    /**
     * 解锁
     */
    void unlock() throws Exception;
}