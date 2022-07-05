package com.test.springboot.juc.cf;

import java.util.concurrent.*;

public class CompletableFutureAPIDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        // test01();
        // test02();
         test03();
//        test04();
    }


    /**
     * 异步执行，对结果进行消费，无返回结果
     */
    private static void test04() {
        CompletableFuture.supplyAsync(() -> {
           return 1;
        }).thenApply((f) -> {
            return f + 10;
        }).thenApply((f) -> {
            return f * 3;
        }).thenAccept((r) -> {
            System.out.println("结果：" + r);
        });

    }


    /**
     * 异步执行，对结果进行处理，有返回值
     * handle()在异常出现的时候会跳过有异常的handle方法体，但最后会执行异常处理exceptionally(),正常执行无异常则会执行whenComplete()
     */
    private static void test03() {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        }, threadPool).handle((f, e) -> {
            int curr = 10 / 0;
            System.out.println("1111");
            return f * 3;
        }).handle((f, e) -> {
            System.out.println("2222");
            return f + 5;
        }).handle((f, e) -> {
            int curr = 20 / 0;
            System.out.println("3333");
            return f + 5;
        }).handle((f, e) -> {
            System.out.println("4444");
            return f + 25;
        }).whenComplete((v, e) -> {
            if (null == e) {
                System.out.println("计算结果为：" + 12138);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            System.out.println("异常信息为：" + e.getMessage());
            return null;
        });

        System.out.println(Thread.currentThread().getName() + "线程正在执行......");

        threadPool.shutdown();
    }

    /**
     * 异步执行，对结果进行处理，有返回值
     * thenApply 出现异常则后续操作停止
     */
    private static void test02() {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        }, threadPool).thenApply(f -> {
            System.out.println("1111");
            return 1111;
        }).thenApply(f -> {
            System.out.println("2222");
            return 2222;
        }).whenComplete((v, e) -> {
            if (null == e) {
                System.out.println("计算结果为：" + v);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            System.out.println("异常信息为：" + e.getMessage());
            return null;
        });

        System.out.println(Thread.currentThread().getName() + "线程正在执行......");

        threadPool.shutdown();
    }


    /**
     * 结果获取
     */
    private static void test01() throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "12138";
        });

//        System.out.println("执行结果：" + completableFuture.get());
//        System.out.println("执行结果：" + completableFuture.join());
//        System.out.println("执行结果：" + completableFuture.get(5, TimeUnit.SECONDS));

//        System.out.println("执行结果：" + completableFuture.getNow("8888"));
//        TimeUnit.SECONDS.sleep(6);
//        System.out.println("执行结果：" + completableFuture.getNow("8888"));

        completableFuture.complete("1597863");
        System.out.println(completableFuture.join());
    }

}
