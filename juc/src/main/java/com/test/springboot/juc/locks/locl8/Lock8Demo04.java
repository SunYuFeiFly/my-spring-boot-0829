package com.test.springboot.juc.locks.locl8;

import java.util.concurrent.TimeUnit;

/**
 * 八锁功能
 *  4 有两部手机，请问先打印邮件还是短信
 *  p1.sendEmail(synchronized、sleep) -> p2.hello()
 *  普通方法和锁无关，不参与资源竞争
 *  result
 *  -------hello
 *  -----sendEmail
 *
 * @author Administrator
 */

public class Lock8Demo04 {

    public static void main(String[] args) {
        test01();
    }

    private static void test01() {
        Phone04 phone01 = new Phone04();
        Phone04 phone02 = new Phone04();

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
class Phone04 {

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

    public void hello() {
        System.out.println("-------hello");
    }
}
