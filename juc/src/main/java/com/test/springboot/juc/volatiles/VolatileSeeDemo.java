package com.test.springboot.juc.volatiles;

import java.util.concurrent.TimeUnit;

/**
 * @author syf_12138
 * @Description 属性可见性
 * @create 2022/7/13 17:14
 */

public class VolatileSeeDemo {

    static boolean flag = true;
    // static volatile boolean flag = true;

    public static void main(String[] args) {
        testNoVolatile();
    }

    /**
     * 元素没有Volatile修饰没有可见性
     */
    private static void testNoVolatile() {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t -----come in");
            while (flag) {
                System.out.println(Thread.currentThread().getName() + "\t ----- 正在遍历");
            }
            System.out.println(Thread.currentThread().getName() + "\t -----flag被设置为false，程序停止");
        }, "t1").start();

        //暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        flag = false;
        System.out.println(Thread.currentThread().getName() + "\t 修改完成flag: " + flag);

    }
}
