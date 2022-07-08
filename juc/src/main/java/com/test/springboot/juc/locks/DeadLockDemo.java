package com.test.springboot.juc.locks;/**
 * @Description TODO
 * @author syf_12138
 * @create 2022/7/7 16:25
 */

import java.util.concurrent.TimeUnit;

/**
 * @Description 死锁
 * @author syf_12138
 * @create 2022/7/7 16:25
 */

public class DeadLockDemo {

    public static void main(String[] args) {
        test01();
    }

    
    /**
     * @Author syf_12138
     * @Description 两个线程互相持有对方所需锁，造成死锁
     * @Return void
     * @Date 2022/7/7 16:26
     */
    private static void test01() {

        final Object objectA = new Object();
        final Object objectB = new Object();

        new Thread(() -> {
            synchronized (objectA) {
                System.out.println(Thread.currentThread().getName() + "线程获取锁a，希望获取锁b");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (objectB) {
                    System.out.println(Thread.currentThread().getName() + "线程获取锁b");
                }
            }
        },"A").start();

        new Thread(() -> {
            synchronized (objectB) {
                System.out.println(Thread.currentThread().getName() + "线程获取锁b，希望获取锁a");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (objectA) {
                    System.out.println(Thread.currentThread().getName() + "线程获取锁a");
                }
            }
        },"B").start();
    }
}
