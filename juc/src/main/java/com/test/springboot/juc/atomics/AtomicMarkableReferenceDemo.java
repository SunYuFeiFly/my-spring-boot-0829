package com.test.springboot.juc.atomics;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * @author syf_12138
 * @Description 原子引用类型，布尔标识位（和AtomicReference不同指出在于，改过则修改标识位false->true，而不是+1）
 * @create 2022/7/15 15:57
 */

public class AtomicMarkableReferenceDemo {

    static AtomicMarkableReference markableReference = new AtomicMarkableReference(100,false);
    public static void main(String[] args) {
        new Thread(() -> {
            // 获取初始状态标识位
            boolean marked = markableReference.isMarked();
            System.out.println(Thread.currentThread().getName() + "线程获取初始标识位内容： " + marked);
            // 睡眠一定时间确保B线程也能拿到一样的标识位数值
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 先将100 -> 1000
            markableReference.compareAndSet(100, 1000, marked, !marked);
        }, "A").start();

        new Thread(() -> {
            // 获取初始状态标识位
            boolean marked = markableReference.isMarked();
            System.out.println(Thread.currentThread().getName() + "线程获取初始标识位内容： " + marked);
            // 睡眠一定时间，确保A线程能完成相关数值修改操作
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean isChange = markableReference.compareAndSet(100, 2000, marked, !marked);
            System.out.println(Thread.currentThread().getName() + "线程是否完成数值修改工作： " + (isChange ? "已修改" : "未修改"));
            System.out.println(Thread.currentThread().getName() + "线程获取数值为： " + markableReference.getReference());
        }, "B").start();
    }
}
