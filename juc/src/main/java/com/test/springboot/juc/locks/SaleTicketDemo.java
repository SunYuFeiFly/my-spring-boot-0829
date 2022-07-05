package com.test.springboot.juc.locks;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁、非公平锁
 * 模拟多站台售票员卖完50张票
 *
 * @author Administrator
 */

public class SaleTicketDemo {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(() -> {
            for (int i = 0; i < 500; i++) {
                ticket.sale();
            }
        }, "a").start();

        new Thread(() -> {
            for (int i = 0; i < 500; i++) {
                ticket.sale();
            }
        }, "b").start();

        new Thread(() -> {
            for (int i = 0; i < 500; i++) {
                ticket.sale();
            }
        }, "c").start();
    }
}

class Ticket {

    private int number = 500;

    ReentrantLock lock = new ReentrantLock(true);

    public void sale() {
        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName()+"卖出第：\t"+(number--)+"\t 还剩下:"+number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
