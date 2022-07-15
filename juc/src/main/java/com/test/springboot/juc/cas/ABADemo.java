package com.test.springboot.juc.cas;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author syf_12138
 * @Description ABA问题实际解决方法
 * @create 2022/7/15 11:22
 */

public class ABADemo {

    static AtomicInteger atomicInteger = new AtomicInteger(100);
    static AtomicStampedReference atomicStampedReference = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {
        // abaHappen();
        sloveABAQuestion();
    }

    
    /**
     * @Author syf_12138
     * @Description TODO
     * @Return AtomicStampedReference有版本号数据，我们更改数据时先对照版本再进行更改需求
     * @Date 2022/7/15 11:51
     */
    private static void sloveABAQuestion() {
        new Thread(() -> {
            // 获取首次操作时版本号
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + " 线程获取最初版本号为：" + stamp);
            // 初次将值由100 -> 101 (stamp 1 -> 2)
            atomicStampedReference.compareAndSet(100, 101, stamp, stamp + 1);
            // 再次将值由101 -> 100 (stamp 2 -> 3)
            atomicStampedReference.compareAndSet(100, 101, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
        }, "t3").start();

        new Thread(() -> {
            // 获取首次操作时版本号
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + " 线程获取最初版本号为：" + stamp);
            // 睡眠一段时间，确保A线程完成两次值交换过程
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 此时需求为将值100 -> 101，但stamp 1 -> 3，不符合但stamp=1需求，不能完成只更换过程
            boolean isChange = atomicStampedReference.compareAndSet(100, 101, stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName() + " 线程获取时版本号为：" + atomicStampedReference.getStamp() + (isChange ? "  修改成功" : "  修改未成功"));
        }, "t4").start();
    }


    /**
     * @Author syf_12138
     * @Description 不带版本号的数值变换我们不能探知值变换过程
     * @Return void
     * @Date 2022/7/15 11:39
     */
    private static void abaHappen() {
        boolean isChange = false;
        // A线程将值由100变为101，再将值由101变回100
        new Thread(() -> {
            atomicInteger.compareAndSet(100, 101);
            atomicInteger.compareAndSet(101, 100);
        }, "t1").start();

        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // B线程将值100变为200(未意识到期间100->101->100的变化过程)
        new Thread(() -> {
            atomicInteger.compareAndSet(100, 200);
            System.out.println("atomicInteger： " + atomicInteger.get());
        }, "t2").start();
    }
}
