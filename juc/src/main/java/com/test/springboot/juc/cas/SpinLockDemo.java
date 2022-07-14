package com.test.springboot.juc.cas;


import java.sql.SQLOutput;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author syf_12138
 * @Description 实现一个自旋锁, 复习CAS思想。自旋锁好处：循环比较获取没有类似wait的阻塞。
 * @create 2022/7/14 16:40
 */

public class SpinLockDemo {

    AtomicReference<Thread> atomicReference = new AtomicReference<Thread>();

    /**
     * 线程获取锁
     */
    public void lock() {
        // 获取进入该方法的线程
        Thread thread = Thread.currentThread();
        // 如果当前接口实现为空，则将该线程设置进去
        while (!atomicReference.compareAndSet(null, thread)) {
            System.out.println(thread.getName() + " 等待锁......");
        }
        System.out.println(thread.getName() + " come in......");
    }

    /**
     * 线程释放锁
     */
    public void unLock() {
        // 获取进入该方法的线程
        Thread thread = Thread.currentThread();
        // 将接口实现里面线程清空
        atomicReference.compareAndSet(thread, null);
        System.out.println(thread.getName() + " task over,unLock......");
    }

    public static void main(String[] args) {
        realizeSpinLock();
    }

    private static void realizeSpinLock() {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(() -> {
            // 线程A先获取锁
            spinLockDemo.lock();
            // 线程A具体完成功能所需时间
            try {
                TimeUnit.SECONDS.sleep(6);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ;
            // 线程A释放锁
            spinLockDemo.unLock();
        }, "A").start();

        // 睡眠一定时间，确保线程A先获取到锁
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            // 线程B先获取锁
            spinLockDemo.lock();
            // 线程B释放锁
            spinLockDemo.unLock();
        }, "B").start();
    }
}
