package com.test.springboot.juc.locks.locl8;

import java.util.concurrent.TimeUnit;

/**
 * 八锁功能
 *  6 有两个静态同步方法，有2部手机，请问先打印邮件还是短信
 *  p1.sendEmail(static、synchronized) -> p2.sendSms(static、synchronized)
 *  普通同步方法，锁的是当前实例对象，静态同步方法，锁的是当前类的Class对象，p1、p2都属于同一个类Phone，所以只有等前面一个对象请求释放锁
 *
 *  result
 *  -----sendEmail
 *  -----sendSMS
 *
 * @author Administrator
 */

public class Lock8Demo06 {

    public static void main(String[] args) {
        test01();
    }

    private static void test01() {
        Phone06 phone01 = new Phone06();
        Phone06 phone02 = new Phone06();

        new Thread(() -> {
            phone01.sendEmail();
        },"a").start();

        // 暂停毫秒,保证a线程先启动
        try { TimeUnit.MILLISECONDS.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            phone02.sendSms();
        },"b").start();
    }
}

/**
 * 资源类
 */
class Phone06 {

    public synchronized void sendEmail() {
        System.out.println("-----sendEmail");
    }

    public synchronized void sendSms() {
        System.out.println("-----sendSMS");
    }
}
