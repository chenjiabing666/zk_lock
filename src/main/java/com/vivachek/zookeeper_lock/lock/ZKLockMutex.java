package com.vivachek.zookeeper_lock.lock;

import lombok.Data;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.listen.ListenerContainer;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * @Description 排他锁的实现类，继承模板类 AbstractZKLockMutex
 * @Author 陈某
 * @Date 2020/4/7 23:23
 */
@Data
public class ZKLockMutex extends AbstractZKLockMutex {

    /**
     * 用于实现线程阻塞
     */
    private CountDownLatch countDownLatch;

    public ZKLockMutex(String lockPath,CuratorFramework zkClient){
        super(lockPath,zkClient);
    }

    /**
     * 尝试获取锁：直接创建一个临时节点，如果这个节点存在创建失败抛出异常，表示已经互斥了，
     * 反之创建成功
     * @throws Exception
     */
    @Override
    protected boolean tryLock()  {
        try {
            zkClient.create()
                    //临时节点
                    .withMode(CreateMode.EPHEMERAL)
                    //权限列表 world:anyone:crdwa
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                    .forPath(lockPath,"lock".getBytes());
            return true;
        }catch (Exception ex){
            return false;
        }
    }


    /**
     * 等待锁，一直阻塞监听
     * @return  成功获取锁返回true，反之返回false
     */
    @Override
    protected void waitLock() throws Exception {
        //监听节点的新增、更新、删除
        final NodeCache nodeCache = new NodeCache(zkClient, lockPath);
        //启动监听
        nodeCache.start();
        ListenerContainer<NodeCacheListener> listenable = nodeCache.getListenable();

        //监听器
        NodeCacheListener listener=()-> {
            //节点被删除，此时获取锁
            if (nodeCache.getCurrentData() == null) {
                //countDownLatch不为null，表示节点存在，此时监听到节点删除了，因此-1
                if (countDownLatch != null)
                    countDownLatch.countDown();
            }
        };
        //添加监听器
        listenable.addListener(listener);

        //判断节点是否存在
        Stat stat = zkClient.checkExists().forPath(lockPath);
        //节点存在
        if (stat!=null){
            countDownLatch=new CountDownLatch(1);
            //阻塞主线程，监听
            countDownLatch.await();
        }
        //移除监听器
        listenable.removeListener(listener);
    }

    /**
     * 解锁，直接删除节点
     * @throws Exception
     */
    @Override
    public void unlock() throws Exception {
        zkClient.delete().forPath(lockPath);
    }
}