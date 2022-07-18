package com.test.springboot.juc.threadLocal;

import com.sun.org.apache.bcel.internal.generic.NEW;

import javax.management.monitor.CounterMonitor;
import java.sql.Time;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author syf_12138
 * @Description 线程池复用线程前提下ThreadLocal的资源释放
 * @create 2022/7/18 17:24
 */

public class ThreadLocalDemo2 {

    private static final int TIME = 10;

    public static void main(String[] args) {
        testSaleCountByThreadLocal();
    }

    
    /**
     * @Author syf_12138
     * @Description 主要演示线程池下执行出任务的资源释放，还有利用CountDownLatch类保障线程执行完任务后立即获取最终数据
     * @Return void
     * @Date 2022/7/18 17:46
     */
    private static void testSaleCountByThreadLocal() {
        MyData myData = new MyData();
        CountDownLatch countDownLatch = new CountDownLatch(TIME);
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        try {
            for (int i = 0; i < 10; i++) {
                // 线程池执行任务
                threadPool.submit(() -> {
                    try {
                        int count = new Random().nextInt(10) + 1;
                        Integer beforeInt = myData.threadLocalField.get();
                        try {
                            for (int j = 0; j < count; j++) {
                                // 统计所有销售总数
                                myData.addBysynchronized();
                                // 统计该线程销售总数
                                myData.add();
                            }
                        } finally {
                            countDownLatch.countDown();
                        }
                        Integer afterInt = myData.threadLocalField.get();
                        System.out.println(Thread.currentThread().getName() + " beforeInt:" + beforeInt + " afterInt: " + afterInt);
                    } finally {
                        myData.threadLocalField.remove();
                    }
                });
            }
            countDownLatch.await();

            System.out.println("销售总数为：" + myData.saleCount);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}

/**
 * 资源类
 */
class MyData {

    int saleCount = 0;

    public synchronized void addBysynchronized() {
        saleCount++;
    }

    ThreadLocal<Integer> threadLocalField = ThreadLocal.withInitial(() -> 0);

    public void add() {
        threadLocalField.set(1 + threadLocalField.get());
    }
}