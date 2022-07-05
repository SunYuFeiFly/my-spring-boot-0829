package com.test.springboot.juc.locks.locl8;

import java.util.concurrent.TimeUnit;

/**
 * 八锁功能
 *  8 有1个静态同步方法，有1个普通同步方法,有2部手机，请问先打印邮件还是短信
 *  p1.sendEmail(static、synchronized) - p2.hello()
 *  静态同步方法，锁的是当前类的Class对象
 *
 *  result：
 *  -------hello
 *  -----sendEmail
 *
 * @author Administrator
 */

public class Lock8Demo08 {

    public static void main(String[] args) {
        test01();
    }

    private static void test01() {
        Phone08 phone01 = new Phone08();
        Phone08 phone02 = new Phone08();

        new Thread(() -> {
            phone01.sendEmail();
        },"a").start();

        // 暂停毫秒,保证a线程先启动
        try { TimeUnit.MILLISECONDS.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            phone02.hello();
        },"b").start();
    }
}

/**
 * 资源类
 */
class Phone08 {

    public static synchronized void sendEmail() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-----sendEmail");
    }

    public void hello() {
        System.out.println("-------hello");
    }
}
