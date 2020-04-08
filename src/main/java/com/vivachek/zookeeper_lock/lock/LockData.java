package com.vivachek.zookeeper_lock.lock;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description TODO
 * @Author CJB
 * @Date 2020/4/7 22:52
 */
@Data
public class LockData {
    final Thread owningThread;
    final String lockPath;
    final AtomicInteger lockCount = new AtomicInteger(1);

    private LockData(Thread owningThread, String lockPath)
    {
        this.owningThread = owningThread;
        this.lockPath = lockPath;
    }
}