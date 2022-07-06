package com.test.springboot.juc.locks;

import static com.test.springboot.juc.locks.LockSyncDemo.m3;

/**
 * 可重入锁
 * @author Administrator
 */

public class ReEntryLockDemo {

    public static void main(String[] args) {
        // reEntryM1();
        // reEntryM2();
        // reEntryM3();
        reEntryM4();
    }

    /**
     * t1  ----外层调用
     * t1  ----中层调用
     * t1  ----内层调用
     */
    private static void reEntryM1() {
        Object object = new Object();
        new Thread(() -> {
            synchronized (object) {
                System.out.println(Thread.currentThread().getName() + "\t ----外层调用");
                synchronized (object) {
                    System.out.println(Thread.currentThread().getName() + "\t ----中层调用");
                    synchronized (object) {
                        System.out.println(Thread.currentThread().getName() + "\t ----内层调用");
                    }
                }
            }
        }, "t1").start();
    }

    /**
     * t2  ----外层调用
     * t2  ----中层调用
     * t2  ----内层调用
     */
    private static void reEntryM2() {
        Object object01 = new Object();
        Object object02 = new Object();
        Object object03 = new Object();
        new Thread(() -> {
            synchronized (object01) {
                System.out.println(Thread.currentThread().getName() + "\t ----外层调用");
                synchronized (object02) {
                    System.out.println(Thread.currentThread().getName() + "\t ----中层调用");
                    synchronized (object03) {
                        System.out.println(Thread.currentThread().getName() + "\t ----内层调用");
                    }
                }
            }
        }, "t2").start();
    }

    /**
     * main  ---- m1 come in
     * main  ---- m2 come in
     * main  ---- m3 come in
     * main  ---- m1 end m1
     */
    private static void reEntryM3() {
        new ReEntryLockDemo().m1();
    }

    /**
     * t3  ---- m1 come in
     * t3  ---- m2 come in
     * t3  ---- m3 come in
     * t3  ---- m1 end m1
     */
    private static void reEntryM4() {
        new Thread(() -> {
            new ReEntryLockDemo().m1();
        },"t3").start();
    }

    public synchronized void m1() {
        //指的是可重复可递归调用的锁，在外层使用锁之后，在内层仍然可以使用，并且不发生死锁，这样的锁就叫做可重入锁。
        System.out.println(Thread.currentThread().getName() + "\t ---- m1 come in");
        m2();
        System.out.println(Thread.currentThread().getName() + "\t ---- m1 end m1");
    }

    public synchronized void m2() {
        System.out.println(Thread.currentThread().getName() + "\t ---- m2 come in");
        m3();
    }

    public synchronized void m3() {
        System.out.println(Thread.currentThread().getName() + "\t ---- m3 come in");
    }

    /**
     * @Author syf_12138
     * @Description TODO
     * @param: i1
     * @param: i2
     * @Return void
     * @Date 2022/7/6 18:21
     */
    public synchronized void test(int i1, int i2) {
        System.out.println(Thread.currentThread().getName() + "\t ---- m3 come in");
    }
}
