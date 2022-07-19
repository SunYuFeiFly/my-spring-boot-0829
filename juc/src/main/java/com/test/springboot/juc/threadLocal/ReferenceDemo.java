package com.test.springboot.juc.threadLocal;

import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author syf_12138
 * @Description ThreadLocal采用弱引用、Entry双重包装原理及强引用、软引用、弱引用、虚引用实例区别
 * @create 2022/7/19 11:16
 */

public class ReferenceDemo {

    public static void main(String[] args) {
        // strongReference();
        // softReference();
        // weakReference();
        phantomReference();
    }

    /**
     * @Author syf_12138
     * @Description 虚引用
     * @Return void
     * @Date 2022/7/19 15:41
     */
    private static void phantomReference() {
        MyObject myObject = new MyObject();
        ReferenceQueue<MyObject> referenceQueue = new ReferenceQueue<>();
        PhantomReference<MyObject> phantomReference = new PhantomReference<>(myObject, referenceQueue);

        List<byte[]> list = new ArrayList<>();

        new Thread(() -> {
            while (true) {
                list.add(new byte[1 * 1024 * 1024]);
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(phantomReference.get() + "\t" + "list add ok");
            }
        }, "t1").start();

        new Thread(() -> {
            while (true) {
                Reference<? extends MyObject> reference = referenceQueue.poll();
                if (reference != null) {
                    System.out.println("-----有虚对象回收加入了队列");
                    break;
                }
            }
        }, "t2").start();
    }


    /**
     * @Author syf_12138
     * @Description 弱引用，只要GC就回收
     * @Return void
     * @Date 2022/7/19 15:06
     */
    private static void weakReference() {
        WeakReference weakReference = new WeakReference<>(new MyObject());
        // 人工开启GC
        System.gc();
        // 睡眠一段时间确保gc已经开启
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("gc after weakReference:" + weakReference.get());
    }


    /**
     * @Author syf_12138
     * @Description 软引用，当运行内存够用不会清理，内存不足时会清理
     * @Return void
     * @Date 2022/7/19 14:55
     */
    private static void softReference() {
        SoftReference softReference = new SoftReference<>(new MyObject());
        // 人工开启GC
        System.gc();
        // 内存够用时不会回收
        System.out.println("gc after 内存足够 softReference:" + softReference.get());

        // 睡眠一段时间确保gc已经开启
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 内存不够用时会回收
        try {
            // 20MB对象,大于设定的idea运行内存
            byte[] bytes = new byte[20 * 1024 * 1024];
        } catch (Exception e) {
            System.out.println("-----gc after 内存不够 softReference:" + softReference.get());
            e.printStackTrace();
        }
    }

    /**
     * @Author syf_12138
     * @Description 强引用，我们日常new出来的对象就是强引用，当对象超出使用域会被垃圾回收期GC回收
     * @Return void
     * @Date 2022/7/19 11:57
     */
    private static void strongReference() {
        MyObject myObject = new MyObject();
        System.out.println("myObject before: " + myObject);

        myObject = null;
        // 人工开启GC，一般不是人工开启
        System.gc();

        // 开启gc到清理需要一定时间
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("myObject after: " + myObject);
    }
}

/**
 * 资源类
 */
class MyObject {

    /**
     * 这个方法一般不用复写，我们只是为了教学给大家演示案例做说明
     */
    @Override
    protected void finalize() throws Throwable {
        // finalize的通常目的是在对象被不可撤销地丢弃之前执行清理操作。
        System.out.println("-------invoke finalize method~!!!");
    }
}
