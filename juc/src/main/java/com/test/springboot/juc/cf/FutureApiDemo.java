package com.test.springboot.juc.cf;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Administrator
 */

public class FutureApiDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
//        new FutureApiDemo().test01();
        new FutureApiDemo().test02();
    }

    public void test01() throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        FutureTask<String> futureTask = new FutureTask<String>(() -> {
            System.out.println(Thread.currentThread().getName() + "进入FutureTask");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "12138";
        });
        new Thread(futureTask, "线程1").start();
        System.out.println("运行到主线程了，用时：" + (System.currentTimeMillis() - startTime));
        System.out.println("返回结果为： " + futureTask.get());
    }

    /**
     * 当调用get()方法时边会立即到futureTask线程中获取答案，会对后续的流程开启阻塞，所以get()方法越靠后调用约好
     */
    public void test02() throws ExecutionException, InterruptedException, TimeoutException {
        long startTime = System.currentTimeMillis();
        FutureTask<String> futureTask = new FutureTask<String>(() -> {
            System.out.println(Thread.currentThread().getName() + "进入FutureTask");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "12138";
        });
        new Thread(futureTask, "线程1").start();

        // 一、直接获取
        // System.out.println("返回结果为： " + futureTask.get());
        // 二、最多等3秒获取
        // System.out.println("返回结果为： " + futureTask.get(3, TimeUnit.SECONDS));
        // 三、等相应线程完成了再获取
        while (true) {
            if (futureTask.isDone()) {
                System.out.println("返回结果为： " + futureTask.get());
                break;
            } else {
                System.out.println("正在处理中");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("运行到主线程了，用时：" + (System.currentTimeMillis() - startTime));
    }

}

