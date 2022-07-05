package com.test.springboot.juc.base;

import java.util.concurrent.TimeUnit;

public class DaemonDemo {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 开始运行, " + (Thread.currentThread().isDaemon() ? "守护线程" : "用户线程"));
            while (true) {
                 System.out.println(12138);
            }
        }, "t1");
        t1.setDaemon(true);
        t1.start();
        // 线程暂停几秒钟
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "\t ----end 主线程");
    }
}
