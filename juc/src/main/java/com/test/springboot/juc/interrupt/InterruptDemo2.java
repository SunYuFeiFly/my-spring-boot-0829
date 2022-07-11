package com.test.springboot.juc.interrupt;/**
 * @Description TODO
 * @author syf_12138
 * @create 2022/7/11 17:42
 */


import java.util.concurrent.TimeUnit;

/**
 * @author syf_12138
 * @Description 线程打断及线程结束后打断状态
 * @create 2022/7/11 17:42
 */

public class InterruptDemo2 {

    public static void main(String[] args) {
        // testInterrupt();
        testInterrupt01();
    }

    /**
     * @Author syf_12138
     * @Description 线程打断打段位状态为true，但当该线程执行结束后，isInterrupted()永远为false,期间利用线程状态判断打印
     * @Return void
     * @Date 2022/7/11 18:08
     */
    private static void testInterrupt01() {
        Thread t2 = new Thread(() -> {
            for (int i = 1; i < 500; i++) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
                System.out.println("-----------" + i);
            }
            System.out.println("t2线程调用interrupt()后的的中断标识02：" + Thread.currentThread().isInterrupted());
        }, "t2");
        t2.start();

        System.out.println("t1线程默认的中断标识：" + t2.isInterrupted());

        // 暂停一会儿
        try {
            TimeUnit.MILLISECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 将中断标识未设置为true
        t2.interrupt();
        System.out.println("t2线程调用interrupt()后的的中断标识01：" + t2.isInterrupted());

        // T1线程已经结束，对不活动的线程不产生任何影响
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("t2线程调用interrupt()后的的中断标识03：" + t2.isInterrupted());
    }

    /**
     * @Author syf_12138
     * @Description 线程打断打段位状态为true，但当该线程执行结束后，isInterrupted()永远为false
     *              该案列并没有用判断打断状态进行打印流程限制，所以会打印所有数据
     * @Return void
     * @Date 2022/7/11 17:54
     */
    private static void testInterrupt() {
        Thread t1 = new Thread(() -> {
            for (int i = 1; i < 500; i++) {
                System.out.println("-----------" + i);
            }
            System.out.println("t1线程调用interrupt()后的的中断标识02：" + Thread.currentThread().isInterrupted());
        }, "t1");
        t1.start();

        System.out.println("t1线程默认的中断标识：" + t1.isInterrupted());

        // 暂停一会儿
        try {
            TimeUnit.MILLISECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 将中断标识未设置为true
        t1.interrupt();
        System.out.println("t1线程调用interrupt()后的的中断标识01：" + t1.isInterrupted());

        // T1线程已经结束，对不活动的线程不产生任何影响
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("t1线程调用interrupt()后的的中断标识03：" + t1.isInterrupted());
    }


}
