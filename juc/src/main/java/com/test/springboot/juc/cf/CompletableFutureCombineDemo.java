package com.test.springboot.juc.cf;

import java.util.concurrent.*;

/**
 * 两个任务执行结果合并
 * @author Administrator
 */

public class CompletableFutureCombineDemo {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        test01();
        System.out.println("执行时间： " + (System.currentTimeMillis() - startTime));
    }

    private static void test01() {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ---启动");
            //暂停几秒钟线程
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        }, threadPool);

        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ---启动");
            //暂停几秒钟线程
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 20;
        }, threadPool);

        CompletableFuture<Integer> completableFuture = completableFuture1.thenCombine(completableFuture2, (x, y) -> {
            return x * y;
        });

        System.out.println("两个任务执行完成后合并结果为： " + completableFuture.join());
    }
}
