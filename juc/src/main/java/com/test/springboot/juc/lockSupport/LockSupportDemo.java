package com.test.springboot.juc.lockSupport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author syf_12138
 * @Description 三种线程等待唤醒示例
 * @create 2022/7/12 22:57
 */

public class LockSupportDemo {

    public static void main(String[] args) {
        testLockSupport();
        // syncWaitNotify();
        // lockAwaitSignal();
    }

    /**
     * 测试Condition的awit()与signal(),他们都必须放在获取锁的块中(synchronized、lock)，先后顺序不能变
     */
    private static void lockAwaitSignal() {
        ReentrantLock lock = new ReentrantLock();
        // 由锁创建测试Condition对象
        Condition condition = lock.newCondition();
        new Thread(() -> {
            // 获取锁
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t ----come in");
                // 线程等待
                condition.await();
                System.out.println(Thread.currentThread().getName() + "\t ----被唤醒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 释放锁
                lock.unlock();
            }
        }, "t1").start();

        //暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            lock.lock();
            try {
                condition.signal();
                System.out.println(Thread.currentThread().getName() + "\t ----发出通知");
            } finally {
                lock.unlock();
            }
        }, "t2").start();
    }


    /**
     * @Author syf_12138
     * @Description 测试synchronized的wait()与notify方法, 两个方法必须都在synchronized锁方法块中，
     * 锁对象相同且先后顺序不能改变，先wait，后notfiy
     * @Return void
     * @Date 2022/7/13 0:21
     */
    private static void syncWaitNotify() {
        Object objectLock = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (objectLock) {
                System.out.println(Thread.currentThread().getName() + "\t ----come in");
                try {
                    objectLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t ----被唤醒");
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
            synchronized (objectLock) {
                objectLock.notify();
                System.out.println(Thread.currentThread().getName() + "\t ----发出通知");
            }
        }, "t2").start();
    }


    /**
     * @Author syf_12138
     * @Description 测试LockSupport的park()阻塞和unpark()放行方法，放行许可证只有一个
     *              与前两者不同的是，前两者先执行释放等待会抛出异常，而LockSupport先发放许可，等执行到park()阻塞方法是会自动无效，相当于上高速先缴费了不阻塞通过（ETC）
     * @Return void
     * @Date 2022/7/12 23:34
     */
    private static void testLockSupport() {
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
