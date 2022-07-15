package com.test.springboot.juc.atomics;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author syf_12138
 * @Description 引用对象的属性修改原子类
 * @create 2022/7/15 16:55
 */

public class AtomicReferenceFieldUpdaterDemo {

    public static void main(String[] args) {
        MyVar myVar = new MyVar();
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                myVar.init(myVar);
            }, String.valueOf(i)).start();
        }
    }
}


class MyVar {

    public volatile Boolean isInit = Boolean.FALSE;

    AtomicReferenceFieldUpdater<MyVar, Boolean> referenceFieldUpdater =
            AtomicReferenceFieldUpdater.newUpdater(MyVar.class, Boolean.class, "isInit");

    /**
     * 初始化工作
     */
    public void init(MyVar myVar) {
        if (referenceFieldUpdater.compareAndSet(myVar, Boolean.FALSE, Boolean.TRUE)) {
            // 已经更新
            System.out.println(Thread.currentThread().getName() + "线程已经完成初始化工作！");
            //暂停几秒钟线程
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(Thread.currentThread().getName() + "已经有线程完成初始化工作！");
        }
    }

}