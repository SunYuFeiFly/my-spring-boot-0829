package com.test.springboot.juc.rwLock;


import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author syf_12138
 * @Description 读写锁的降级（写锁未释放前可以获取读锁，但读锁未释放不能获取写锁即所降级,写优先级高于读）
 * @create 2022/7/20 16:52
 */

public class LockDownGradingDemo {

    public static void main(String[] args) {
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        // test01(writeLock, readLock);

        test02(writeLock, readLock);
    }

    
    /**
     * @Author syf_12138
     * @Description 在获取写锁未释放锁的过程中可以获取读锁进行读写，但在获取读锁过程中不能获取写锁
     * @param: writeLock 写锁
     * @param: readLock 读锁
     * @Return void
     * @Date 2022/7/20 17:04
     */
    private static void test02(ReentrantReadWriteLock.WriteLock writeLock, ReentrantReadWriteLock.ReadLock readLock) {
        writeLock.lock();
        System.out.println("在进行写操作！");
        // 获取读锁，即写的内容写入后不让其他写锁获取写的机会，回去最新写入的内容
        readLock.lock();
        System.out.println("在进行读操作！");
        // 释放写锁
        writeLock.unlock();
        // 释放读锁
        readLock.unlock();
    }


    /**
     * @Author syf_12138
     * @Description 获取读锁、写锁回不影响各自操作，读锁不释放不能获取读锁
     * @param: writeLock 写锁
     * @param: readLock 读锁
     * @Return void
     * @Date 2022/7/20 17:00
     */
    private static void test01(ReentrantReadWriteLock.WriteLock writeLock, ReentrantReadWriteLock.ReadLock readLock) {
        /**
         * 写操作
         */
        writeLock.lock();
        System.out.println("在进行写操作！");
        writeLock.unlock();

        /**
         * 读操作
         */
        readLock.lock();
        System.out.println("在进行读操作！");
        readLock.unlock();
    }
}
