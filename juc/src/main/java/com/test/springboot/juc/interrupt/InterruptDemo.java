package com.test.springboot.juc.interrupt;/**
 * @Description TODO
 * @author syf_12138
 * @create 2022/7/7 17:55
 */


import org.w3c.dom.ls.LSOutput;

import java.util.concurrent.TimeUnit;

/**
 * @Description 线程打断
 * @author syf_12138
 * @create 2022/7/7 17:55
 */

public class InterruptDemo {

    static volatile boolean isStop = false;

    public static void main(String[] args) {
        test01();
    }


    /**
     * @Author syf_12138
     * @Description 通过volatile实现线程打断
     * @Return void
     * @Date 2022/7/7 17:55
     */
    private static void test01() {
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
