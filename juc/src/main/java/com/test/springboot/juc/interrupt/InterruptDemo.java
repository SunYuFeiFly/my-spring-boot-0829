package com.test.springboot.juc.interrupt;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Description 线程打断
 * @author syf_12138
 * @create 2022/7/7 17:55
 */

public class InterruptDemo {

    static volatile boolean isStop = false;
    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // testVolatile();
        // testAtomicBoolean();
        // testInterrupt();
        testInterruptAddPool();
    }

    /**
     * @Author syf_12138
     * @Description 通过interrupt()方法将特定线程指定打断标记为true，线程通过isInterrupted（）判断其是否被标记为打断 (加线程池)
     * @Return void
     * @Date 2022/7/8 11:27
     */
    private static void testInterruptAddPool() throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        Future<String> future = threadPool.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                while (true) {
                    if (isStop) {
                        System.out.println(Thread.currentThread().getName() + "线程被打断");
                        break;
                    }
                    System.out.println("hello interrupt api");
                }
                return "12138";
            }
        });

        Future<?> future01 = threadPool.submit(new Runnable() {
            @Override
            public void run() {
                isStop = true;
                System.out.println(Thread.currentThread().getName() + "线程运行");
            }
        });

        System.out.println("future:" + future.get());
        System.out.println("future01:" + future01.get());
    }

    /**
     * @Author syf_12138
     * @Description 通过interrupt()方法将特定线程指定打断标记为true，线程通过isInterrupted（）判断其是否被标记为打断
     * @Return void
     * @Date 2022/7/8 11:26
     */
    private static void testInterrupt() {
        Thread t5 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "线程被打断");
                    break;
                }
                System.out.println("hello interrupt api");
            }
        }, "t5");
        t5.start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t6 = new Thread(() -> {
            System.out.println("利用interrupt()设置t5线程打断标记为true");
            t5.interrupt();
        }, "t6");
        t6.start();
    }


    /**
     * @Author syf_12138
     * @Description 通过AtomicBoolean进行线程打断
     * @Return void
     * @Date 2022/7/8 11:01
     */
    private static void testAtomicBoolean() {
        new Thread(() -> {
            while (true) {
                if (atomicBoolean.get()) {
                    System.out.println(Thread.currentThread().getName() + "线程即将被打断");
                    break;
                }
                System.out.println("hello atomicBoolean api");
            }
        }, "t3").start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println("利用AtomicBoolean打断t3线程");
            atomicBoolean.set(true);
        }, "t4").start();
    }


    /**
     * @Author syf_12138
     * @Description 通过volatile实现线程打断
     * @Return void
     * @Date 2022/7/7 17:55
     */
    private static void testVolatile() {
        new Thread(() -> {
            while (true) {
                if (isStop) {
                    System.out.println(Thread.currentThread().getName() + "线程即将被打断");
                    break;
                }
                System.out.println("hello interrupt api");
            }
        }, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println("打断t1线程");
            isStop = true;
        }, "t2").start();
    }
}
