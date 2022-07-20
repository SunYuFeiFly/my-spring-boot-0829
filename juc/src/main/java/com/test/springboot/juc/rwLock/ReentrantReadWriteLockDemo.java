package com.test.springboot.juc.rwLock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author syf_12138
 * @Description 利用ReentrantReadWriteLock锁解决ReentrantLock锁读读不可同时读取资源情况（读写、写读仍然互斥）
 * @create 2022/7/20 14:35
 */

public class ReentrantReadWriteLockDemo {

    public static void main(String[] args) {
        // testReentrantLock();
        testReentrantReadWriteLock();
    }


    /**
     * @Author syf_12138
     * @Description 利用ReentrantReadWriteLock锁完成读写过程，写过程其它线程无法插手，但读线程时其他线程也可以同时读。
     * @Return void
     * @Date 2022/7/20 15:37
     */
    private static void testReentrantReadWriteLock() {
        MyResourceForReentrantReadWriteLock myResource = new MyResourceForReentrantReadWriteLock();
        for (int i = 1; i <= 10; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.write(finalI + "", finalI + "");
            }, String.valueOf(i)).start();
        }
        for (int i = 1; i <= 10; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.read(finalI + "");
            }, String.valueOf(i)).start();
        }
        //暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 1; i <= 3; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.write(finalI + "", finalI + "");
            }, "新写锁线程->" + String.valueOf(i)).start();
        }
    }

    /**
     * @Author syf_12138
     * @Description 利用ReentrantLock锁完成读写过程，读写过程其他线程都无法"插手"
     * @Return void
     * @Date 2022/7/20 15:34
     */
    private static void testReentrantLock() {
        MyResourceForReentrantLock myResource = new MyResourceForReentrantLock();
        for (int i = 1; i <= 10; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.write(finalI + "", finalI + "");
            }, String.valueOf(i)).start();
        }
        for (int i = 1; i <= 10; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.read(finalI + "");
            }, String.valueOf(i)).start();
        }
        //暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 1; i <= 3; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.write(finalI + "", finalI + "");
            }, "新写锁线程->" + String.valueOf(i)).start();
        }
    }
}

/**
 * 资源类，模拟简单的缓存（ReentrantLock）
 */
class MyResourceForReentrantLock {

    Map<String, String> map = new HashMap<String, String>();
    /**
     * ReentrantLock 等价于 synchronized
     */
    Lock lock = new ReentrantLock();

    /**
     * @Author syf_12138
     * @Description 模拟ReentrantLock加锁读操作
     * @param: key 用于获取map内信息主键
     * @Return void
     * @Date 2022/7/20 15:20
     */
    public void read(String key) {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t" + "#线程正在读取");
            String result = map.get(key);
            // 暂停2000毫秒,演示读锁没有完成之前，写锁无法获得
            try {
                TimeUnit.MILLISECONDS.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + "#线程完成读取" + "\t" + result);
        } finally {
            lock.unlock();
        }
    }


    /**
     * @Author syf_12138
     * @Description 模拟ReentrantLock加锁写操作
     * @param: key 主键
     * @param: value 值
     * @Return void
     * @Date 2022/7/20 15:24
     */
    public void write(String key, String value) {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t" + "#线程正在写入");
            map.put(key, value);
            // 暂停一会完成写入操作
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + "#线程完成写入");
        } finally {
            lock.unlock();
        }
    }
}

/**
 * 资源类，模拟简单的缓存（ReentrantReadWriteLock）
 */
class MyResourceForReentrantReadWriteLock {

    Map<String, String> map = new HashMap<String, String>();
    /**
     * ReentrantReadWriteLock 一体两面，读写互斥，读读共享
     */
    ReadWriteLock rwLock = new ReentrantReadWriteLock();

    /**
     * @Author syf_12138
     * @Description 模拟ReentrantLock加锁读操作
     * @param: key 用于获取map内信息主键
     * @Return void
     * @Date 2022/7/20 15:20
     */
    public void read(String key) {
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t" + "#线程正在读取");
            String result = map.get(key);
            // 暂停2000毫秒,演示读锁没有完成之前，写锁无法获得
            try {
                TimeUnit.MILLISECONDS.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + "#线程完成读取" + "\t" + result);
        } finally {
            rwLock.readLock().unlock();
        }
    }


    /**
     * @Author syf_12138
     * @Description 模拟ReentrantLock加锁写操作（解决读读情况不可同时读取情况）
     * @param: key 主键
     * @param: value 值
     * @Return void
     * @Date 2022/7/20 15:24
     */
    public void write(String key, String value) {
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t" + "#线程正在写入");
            map.put(key, value);
            // 暂停一会完成写入操作
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + "#线程完成写入");
        } finally {
            rwLock.writeLock().unlock();
        }
    }
}