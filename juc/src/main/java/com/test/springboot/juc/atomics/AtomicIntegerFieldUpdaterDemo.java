package com.test.springboot.juc.atomics;

import lombok.Data;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author syf_12138
 * @Description 对象的属性修改原子类
 * @create 2022/7/15 16:21
 */

public class AtomicIntegerFieldUpdaterDemo {

    public static void main(String[] args) throws InterruptedException {
        // testSynchronized();
        testAtomicIntegerFieldUpdater();
    }


    /**
     * @Author syf_12138
     * @Description 利用AtomicIntegerFieldUpdater对其中需要修改的属性money单独加锁，锁较轻
     * @Return void
     * @Date 2022/7/15 16:48
     */
    private static void testAtomicIntegerFieldUpdater() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(50);
        BankAccount bankAccount = new BankAccount();
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 1000; j++) {
                        bankAccount.transMoney(bankAccount);
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }, String.valueOf(i)).start();
        }
        countDownLatch.await();

        System.out.println("最终结果为： " + bankAccount.money);
    }


    /**
     * @Author syf_12138
     * @Description 对修改操作的属性方法synchronized加锁，锁较重
     * @Return void
     * @Date 2022/7/15 16:44
     */
    private static void testSynchronized() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(50);
        BankAccount01 bankAccount01 = new BankAccount01();
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 1000; j++) {
                        bankAccount01.add();
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }, String.valueOf(i)).start();
        }
        countDownLatch.await();

        System.out.println("最终结果为： " + bankAccount01.getMoney());
    }
}


class BankAccount {
    String bankName = "BBB";

    /** 更新的对象属性必须使用 public volatile 修饰符。*/
    public volatile int money = 0;

    public void add() {
        money++;
    }

    /**
     * 因为对象的属性修改类型原子类都是抽象类，所以每次使用都必须
     * 使用静态方法newUpdater()创建一个更新器，并且需要设置想要更新的类和属性。
     */
    AtomicIntegerFieldUpdater<BankAccount> fieldUpdater =
            AtomicIntegerFieldUpdater.newUpdater(BankAccount.class, "money");

    /**
     * 不加synchronized，保证高性能原子性，局部微创小手术
     */
    public void transMoney(BankAccount bankAccount) {
        fieldUpdater.getAndIncrement(bankAccount);
    }
}

@Data
class BankAccount01 {

    String bankName = "AAA";
    int money = 0;

    public synchronized void add() {
        money++;
    }
}