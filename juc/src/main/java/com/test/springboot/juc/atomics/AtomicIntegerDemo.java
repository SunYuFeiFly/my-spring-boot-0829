package com.test.springboot.juc.atomics;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author syf_12138
 * @Description 基本类型原子类
 * @create 2022/7/15 14:12
 */

public class AtomicIntegerDemo {

    private static int SIZE = 50;
    public static void main(String[] args) throws InterruptedException {
        // testAtomicInteger();
        testAtomicIntegerWithCountDownLatch();
    }

    /**
     * @Author syf_12138
     * @Description 利用CountDownLatch技术判断所有操作是否完成
     * @Return void
     * @Date 2022/7/15 14:38
     */
    private static void testAtomicIntegerWithCountDownLatch() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(SIZE);
        MyNumber myNumber = new MyNumber();
        for (int i = 0; i < SIZE; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 1000; j++) {
                        myNumber.addPlusPlus();
                    }
                } finally {
                    // 上面完成一个大循环则-1
                    countDownLatch.countDown();
                }
            }, String.valueOf(i)).start();
        }
        // 这里不用睡眠时间来保证所有线程完成任务，只要countDownLatch=0说明所有线程已经完成了
        countDownLatch.await();

        // 主线程获取值
        System.out.println("总和为：" + myNumber.atomicInteger.get());
    }

    /**
     * @Author syf_12138
     * @Description 为了确保所有线程完成计算，我们添加特定的睡眠时间，这样不科学
     * @Return void
     * @Date 2022/7/15 14:32
     */
    private static void testAtomicInteger() {
        MyNumber myNumber = new MyNumber();
        for (int i = 0; i < SIZE; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myNumber.addPlusPlus();
                }
            }, String.valueOf(i)).start();
        }

        // 确保线程计算完成，不然还未计算完成就去获取值
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 主线程获取值
        System.out.println("总和为：" + myNumber.atomicInteger.get());
    }
}


class MyNumber {

    AtomicInteger atomicInteger = new AtomicInteger();

    public void addPlusPlus() {
        atomicInteger.getAndIncrement();
    }
}