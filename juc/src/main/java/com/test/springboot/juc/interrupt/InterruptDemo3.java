package com.test.springboot.juc.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * @author syf_12138
 * @Description 当线程调用wait(),sleep(),join()方法时，会将打断标志位值清除为false
 * @create 2022/7/12 10:54
 */

public class InterruptDemo3 {

    public static void main(String[] args) {
        // test01();
        test02();
    }

    /**
     * @Author syf_12138
     * @Description 当线程调用wait(),sleep(),join()方法时，会将打断标志位值清除为false,此时在catch或finily方法体内重新再次做打断动作，则可将打断标志位设置为true。
     * @param: 
     * @Return void
     * @Date 2022/7/12 11:46
     */
    private static void test02() {
        Thread t1 = new Thread(() -> {
            while (true) {
                // 如果线程状态标志位为true，则中断该线程任务
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "中断标志位：" + Thread.currentThread().isInterrupted() + " 程序停止");
                    break;
                }
                System.out.println("-----hello InterruptDemo3");

                // 让线程处于睡眠状态，此时该线程状态标志位设置为true会报异常，并且不会改变现状状态标志位
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Thread.currentThread().interrupt();
                }

                System.out.println(Thread.currentThread().getName() + "中断标志位：" + Thread.currentThread().isInterrupted());
            }
        }, "t1");
        t1.start();

        // 暂停一段时间，方便t1能执行一段时间的遍历任务
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> t1.interrupt(), "t2").start();
    }

    /**
     * @Author syf_12138
     * @Description 当线程调用wait(),sleep(),join()方法时，会将打断标志位值清除为false，所以会出现无限循环打印现象
     * @Return void
     * @Date 2022/7/12 11:32
     */
    private static void test01() {
        Thread t1 = new Thread(() -> {
            while (true) {
                // 如果线程状态标志位为true，则中断该线程任务
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "中断标志位：" + Thread.currentThread().isInterrupted() + " 程序停止");
                    break;
                }
                System.out.println("-----hello InterruptDemo3");

                // 让线程处于睡眠状态，此时该线程状态标志位设置为true会报异常，并且不会改变现状状态标志位
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + "中断标志位：" + Thread.currentThread().isInterrupted());
            }
        }, "t1");
        t1.start();

        // 暂停一段时间，方便t1能执行一段时间的遍历任务
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> t1.interrupt(), "t2").start();
    }
}
