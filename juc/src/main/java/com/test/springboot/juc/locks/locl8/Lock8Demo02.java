package com.test.springboot.juc.locks.locl8;

import java.util.concurrent.TimeUnit;

/**
 * 八锁功能
 *  2 sendEmail方法中加入暂停3秒钟，请问先打印邮件还是短信
 *  p.sendEmail(synchronized、sleep) -> p.sendSms(synchronized)
 *  单个对象访问多个同步方法（对象锁），同一时间只有一个对象能访问这些同步方法，当前获取对象锁同时线程睡眠，那么只能等睡眠时间过后释放锁，其他对象才能获取所进行访问
 *
 *  result：
 *  -----sendEmail
 *  -----sendSMS
 *
 * @author Administrator
 */

public class Lock8Demo02 {

    public static void main(String[] args) {
        test01();
    }

    private static void test01() {
        Phone02 phone = new Phone02();

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
class Phone02 {

    public synchronized void sendEmail() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-----sendEmail");
    }

    public synchronized void sendSms() {
        System.out.println("-----sendSMS");
    }
}
