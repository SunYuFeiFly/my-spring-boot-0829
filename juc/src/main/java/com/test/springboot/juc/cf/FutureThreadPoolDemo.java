package com.test.springboot.juc.cf;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 */

public class FutureThreadPoolDemo {

    public static void main(String[] args) {
//        new FutureThreadPoolDemo().test();
        new FutureThreadPoolDemo().FutureThreadPool();
    }

    public void FutureThreadPool() {
        long startTime = System.currentTimeMillis();
        // 3个任务，目前开启多个异步任务线程来处理
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        FutureTask<String> futureTask1 = new FutureTask<>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println("futureTask1..." + (System.currentTimeMillis() - startTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "task1 over";
        });
        threadPool.submit(futureTask1);

        FutureTask<String> futureTask2 = new FutureTask<>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
                System.out.println("futureTask2..." + (System.currentTimeMillis() - startTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "task1 over";
        });
        threadPool.submit(futureTask2);

        FutureTask<String> futureTask3 = new FutureTask<>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
                System.out.println("futureTask3..." + (System.currentTimeMillis() - startTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "task1 over";
        });
        threadPool.submit(futureTask3);

        long endTime = System.currentTimeMillis();
        System.out.println("执行时间：" + (endTime - startTime) + "毫秒");
        threadPool.shutdown();
    }

    public void test() {
        // 3个任务顺序由一个线程来执行，时间为各任务所需时间总和
        long startTime = System.currentTimeMillis();
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (Exception e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("执行时间：" + (endTime - startTime) + "毫秒");
    }
}
