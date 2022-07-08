package com.test.springboot.juc.interrupt;/**
 * @Description TODO
 * @author syf_12138
 * @create 2022/7/7 17:55
 */


import org.w3c.dom.ls.LSOutput;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Description 线程打断
 * @author syf_12138
 * @create 2022/7/7 17:55
 */

public class InterruptDemo {

    static volatile boolean isStop = false;
    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public static void main(String[] args) {
        // testInterrupt();
        testAtomicBoolean();
    }

    
    /**
     * @Author syf_12138
     * @Description TODO
     * @param: 
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
    private static void testInterrupt() {
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
