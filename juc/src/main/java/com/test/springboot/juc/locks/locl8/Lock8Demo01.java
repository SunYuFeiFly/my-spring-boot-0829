package com.test.springboot.juc.locks.locl8;

import java.util.concurrent.TimeUnit;

/**
 * 八锁功能
 *  1 标准访问有ab两个线程，请问先打印邮件还是短信
 *  p.sendEmail(synchronized) -> p.sendSms(synchronized)
 *  单个对象访问多个的同步方法（对象锁），此时对于单个对象来说是锁定所有同步方法，同一时间只能有一个对象进行访问请求
 *
 *  result：
 *  -----sendEmail
 *  -----sendSMS
 *
 * @author Administrator
 */

public class Lock8Demo01 {

    public static void main(String[] args) {
        test01();
    }

    private static void test01() {
        Phone01 phone = new Phone01();

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
class Phone01 {

    public synchronized void sendEmail() {
        System.out.println("-----sendEmail");
    }

    public synchronized void sendSms() {
        System.out.println("-----sendSMS");
    }
}
