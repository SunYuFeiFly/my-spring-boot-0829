package com.test.springboot.juc.threadLocal;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author syf_12138
 * @Description 卖票总数（加锁原始统计方法）、个人卖票数量统计（ThreadLocal）
 * @create 2022/7/18 16:33
 */

public class ThreadLocalDemo {

    public static void main(String[] args) {
        // testSynchronized();
        House house = new House();
        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                int size = new Random().nextInt(5) + 1;
                try {
                    for (int j = 0; j < size; j++) {
                        // 用于统计所有销售总数
                        house.saleHouse();
                        // 用于计算单个销售数量
                        house.saleVolumeByThreadLocal();
                    }
                    // 获取每一个线程实际操作的数额
                    System.out.println(Thread.currentThread().getName() + "销售房屋数量为： " + house.saleVolume.get());
                } finally {
                    // 线程复用，防止后续使用该线程造成逻辑错误或内存泄漏(此处直接new 线程不存在此问题，但如果使用线程池则需注意该问题)
                    house.saleVolume.remove();
                }
            }, String.valueOf(i)).start();
        }

        // 暂停一定时间让线程执行完
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("销售房屋总数为： " + house.saleCount);
    }

    /**
     * @Author syf_12138
     * @Description 通过原始加锁方法统计各线程执行总和
     * @Return void
     * @Date 2022/7/18 16:41
     */
    private static void testSynchronized() {
        House house = new House();
        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                int size = new Random().nextInt(5) + 1;
                System.out.println("销售房屋数量为：" + size);
                for (int j = 0; j < size; j++) {
                    house.saleHouse();
                }
            }, String.valueOf(i)).start();
        }
        // 暂停一定时间让线程执行完
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("销售房屋总数为： " + house.saleCount);
    }
}

/**
 * 资源类
 */
class House {

    int saleCount = 0;

    public synchronized void saleHouse() {
        ++saleCount;
    }

    // 老版创建ThreadLocal对象方法
//    ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
//        @Override
//        protected Integer initialValue() {
//            return 0;
//        }
//    };

     ThreadLocal<Integer> saleVolume = ThreadLocal.withInitial(() -> 0);

    // 利用ThreadLocal真正进行数据统计
    public void saleVolumeByThreadLocal() {
        saleVolume.set(1 + saleVolume.get());
    }
}