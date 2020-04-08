package com.vivachek.zookeeper_lock.lock;

import com.google.common.collect.Maps;
import org.apache.curator.framework.CuratorFramework;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Description 排他锁，可重入，模板类
 * @Author 陈某
 * @Date 2020/4/7 22:55
 */
public abstract class AbstractZKReLockMutex implements ZKLock {

    /**
     * 节点路径
     */
    protected String lockPath;

    /**
     * zk客户端
     */
    protected CuratorFramework zkClient;

    ConcurrentMap<Thread,LockData> threadMap= Maps.newConcurrentMap();

    private AbstractZKReLockMutex(){}

    public AbstractZKReLockMutex(String lockPath, CuratorFramework client){
        this.lockPath=lockPath;
        this.zkClient=client;
    }

    /**
     * 模板方法，搭建的获取锁的框架，具体逻辑交于子类实现
     * @throws Exception
     */
    @Override
    public final void lock() throws Exception {
        //获取锁成功
        if (tryLock()){
            System.out.println(Thread.currentThread().getName()+"获取锁成功");
        }else{  //获取锁失败
            //阻塞一直等待
            waitLock();
            //递归，再次获取锁
            lock();
        }
    }

    /**
     * 尝试获取锁，子类实现
     */
    protected abstract boolean tryLock() ;


    /**
     * 等待获取锁，子类实现
     */
    protected abstract void waitLock() throws Exception;


    /**
     * 解锁：删除节点或者直接断开连接
     */
    @Override
    public  abstract void unlock() throws Exception;
}