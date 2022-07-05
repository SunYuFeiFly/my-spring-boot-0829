package com.test.springboot.juc.locks.locl8;

import java.util.concurrent.TimeUnit;

/**
 * 八锁功能
 *  3 添加一个普通的hello方法，请问先打印邮件还是hello
 *  p.sendEmail(synchronized、sleep) -> p.hello()
 *  普通方法和锁无关，不参与资源竞争
 *
 *  result:
 *  -------hello
 *  -----sendEmail
 *
 * @author Administrator
 */

public class Lock8Demo03 {

    public static void main(String[] args) {
        test01();
    }

    private static void test01() {
        Phone03 phone = new Phone03();

        new Thread(() -> {
            phone.sendEmail();
        },"a").start();

        // 暂停毫秒,保证a线程先启动
        try { TimeUnit.MILLISECONDS.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            phone.hello();
        },"b").start();
    }
}

/**
 * 资源类
 */
class Phone03 {

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
