package com.test.springboot.juc.cf;

import java.util.concurrent.*;

/**
 * @author Administrator
 */

public class CompletableFutureBuildDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // test01();
        test02();
    }

    private static void test02() throws ExecutionException, InterruptedException {
        // 创建线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "12138";
        }, threadPool);

        System.out.println(stringCompletableFuture.get());
    }


    private static void test01() throws ExecutionException, InterruptedException {
        // 创建线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, threadPool);

        System.out.println(completableFuture.get());
    }
}
