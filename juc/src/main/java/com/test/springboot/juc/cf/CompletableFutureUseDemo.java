package com.test.springboot.juc.cf;

import javax.xml.bind.annotation.XmlInlineBinaryData;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author Administrator
 */

public class CompletableFutureUseDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
       // test01(); 
        test02();
    }

    /**
     * 验证其较FutureTask改进方法
     */
    private static void test02() {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "进入异步方法");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }

            int result = ThreadLocalRandom.current().nextInt(10);
            int curr = 10/0;
            return result;
        }, threadPool).whenComplete((v, e) -> {
            if (null == e) {
                System.out.println("异步线程计算完成！");
                // 进行后续相关操作
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            System.out.println("异常情况为：" + e.getCause() + "\t" + e.getMessage());
            return null;
        });

        System.out.println("主线程运行......");
    }


    /**
     * 用于验证其完全可替代FutureTask使用方法
     */
    private static void test01() throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        CompletableFuture<Integer> supplyAsync = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "进入异步方法");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return ThreadLocalRandom.current().nextInt(10);
        }, threadPool);

        System.out.println("主线程运行......");
        System.out.println("异步线程执行结果：" + supplyAsync.get());
    }
}
