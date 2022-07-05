package com.test.springboot.juc.locks.locl8;

import java.util.concurrent.TimeUnit;

/**
 * 八锁功能
 *  5 有两个静态同步方法，有1部手机，请问先打印邮件还是短信
 *  p.sendEmail(static、synchronized) -> p.sendSms(static、synchronized)
 *  普通同步方法，锁的是当前实例对象，静态同步方法，锁的是当前类的Class对象
 *
 *  result
 *  -----sendEmail
 *  -----sendSMS
 *
 * @author Administrator
 */

public class Lock8Demo05 {

    public static void main(String[] args) {
        test01();
    }

    private static void test01() {
        Phone05 phone = new Phone05();

        new Thread(() -> {
            phone.sendEmail();
        },"a").start();

        // 暂停毫秒,保证a线程先启动
        try { TimeUnit.MILLISECONDS.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            phone.sendSms();
        },"b").start();
    }
}

/**
 * 资源类
 */
class Phone05 {

    public static synchronized void sendEmail() {
        System.out.println("-----sendEmail");
    }

    public static synchronized void sendSms() {
        System.out.println("-----sendSMS");
    }
}
