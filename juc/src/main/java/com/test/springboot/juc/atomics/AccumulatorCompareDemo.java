package com.test.springboot.juc.atomics;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author syf_12138
 * @Description 高并发大数据量下的点赞统计方案
 * @create 2022/7/15 17:59
 */

public class AccumulatorCompareDemo {

    public static final int _1W = 10000;
    public static final int threadNumber = 50;

    public static void main(String[] args) throws InterruptedException {
        testSynchronized();
        testAtomicLong();
        testLongAdder();
        testLongAccumulator();
    }


    /**
     * @Author syf_12138
     * @Description 利用LongAccumulator原子类操作5000万点赞数，用时：70s，获取结果为：LongAccumulator.get()
     * @Return void
     * @Date 2022/7/18 10:44
     */
    private static void testLongAccumulator() throws InterruptedException {
        ClickNumber clickNumber = new ClickNumber();
        CountDownLatch countDownLatch = new CountDownLatch(threadNumber);
        long startTime = System.currentTimeMillis();
        for (int i = 1; i <= threadNumber; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * _1W; j++) {
                        clickNumber.clickByLongAccumulator();
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }, String.valueOf(i)).start();
        }
        countDownLatch.await();
        long endTime = System.currentTimeMillis();
        System.out.println("----costTime: " + (endTime - startTime) + " 毫秒" + "\t clickByLongAccumulator: " + clickNumber.longAccumulator.get());
    }


    /**
     * @Author syf_12138
     * @Description 利用LongAdder原子类操作5000万点赞数，用时：90s，获取结果为：LongAdder属性.sum()
     * @Return void
     * @Date 2022/7/18 10:44
     */
    private static void testLongAdder() throws InterruptedException {
        ClickNumber clickNumber = new ClickNumber();
        CountDownLatch countDownLatch = new CountDownLatch(threadNumber);
        long startTime = System.currentTimeMillis();
        for (int i = 1; i <= threadNumber; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * _1W; j++) {
                        clickNumber.clickByLongAdder();
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }, String.valueOf(i)).start();
        }
        countDownLatch.await();
        long endTime = System.currentTimeMillis();
        System.out.println("----costTime: " + (endTime - startTime) + " 毫秒" + "\t clickByLongAdder: " + clickNumber.longAdder.sum());
    }


    /**
     * @Author syf_12138
     * @Description 利用AtomicLong原子类操作5000万点赞数，用时：1313s，获取结果为：AtomicLong对象.get()
     * @Return void
     * @Date 2022/7/18 10:44
     */
    private static void testAtomicLong() throws InterruptedException {
        ClickNumber clickNumber = new ClickNumber();
        CountDownLatch countDownLatch = new CountDownLatch(threadNumber);
        long startTime = System.currentTimeMillis();
        for (int i = 1; i <= threadNumber; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * _1W; j++) {
                        clickNumber.clickByAtomicLong();
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }, String.valueOf(i)).start();
        }
        countDownLatch.await();
        long endTime = System.currentTimeMillis();
        System.out.println("----costTime: " + (endTime - startTime) + " 毫秒" + "\t clickByAtomicLong: " + clickNumber.atomicLong.get());
    }


    /**
     * @Author syf_12138
     * @Description 利用Synchronized锁操作5000万点赞数，用时：1335s，获取结果为：对象.属性
     * @Return void
     * @Date 2022/7/18 10:44
     */
    private static void testSynchronized() throws InterruptedException {
        ClickNumber clickNumber = new ClickNumber();
        CountDownLatch countDownLatch = new CountDownLatch(threadNumber);
        long startTime = System.currentTimeMillis();
        for (int i = 1; i <= threadNumber; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * _1W; j++) {
                        clickNumber.clickBySynchronized();
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }, String.valueOf(i)).start();
        }
        countDownLatch.await();
        long endTime = System.currentTimeMillis();
        System.out.println("----costTime: " + (endTime - startTime) + " 毫秒" + "\t clickBySynchronized: " + clickNumber.number);
    }
}

/**
 * 资源类
 */
class ClickNumber {
    int number = 0;
    AtomicLong atomicLong = new AtomicLong(0);
    LongAdder longAdder = new LongAdder();
    LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x + y, 0);

    /**
     * Synchronized锁
     */
    public synchronized void clickBySynchronized() {
        number++;
    }

    /**
     * AtomicLong原子类
     */
    public void clickByAtomicLong() {
        atomicLong.getAndIncrement();
    }

    /**
     * LongAdder类
     */
    public void clickByLongAdder() {
        longAdder.increment();
    }

    /**
     * LongAccumulator
     */
    public void clickByLongAccumulator() {
        longAccumulator.accumulate(1);
    }
}
