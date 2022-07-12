package com.test.springboot.juc.lockSupport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author syf_12138
 * @Description 三种线程等待唤醒示例
 * @create 2022/7/12 22:57
 */

public class LockSupportDemo {

    public static void main(String[] args) {
        // test01();
        syncWaitNotify();
    }


    /**
     * @Author syf_12138
     * @Description 测试synchronized的wait()与notify方法
     * @Return void
     * @Date 2022/7/13 0:21
     */
    private static void syncWaitNotify() {
        Object objectLock = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (objectLock) {
                System.out.println(Thread.currentThread().getName()+"\t ----come in");
                try {
                    objectLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"\t ----被唤醒");
            }
        }, "t1");
        t1.start();

        // 暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            synchronized (objectLock){
                objectLock.notify();
                System.out.println(Thread.currentThread().getName()+"\t ----发出通知");
            }
        },"t2").start();
    }


    /**
     * @Author syf_12138
     * @Description TODO
     * @Return void
     * @Date 2022/7/12 23:34
     */
    private static void test01() {
        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t ----come in" + System.currentTimeMillis());
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "\t ----被唤醒" + System.currentTimeMillis());
        }, "t1");
        t1.start();

        // 暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LockSupport.unpark(t1);
    }
}
