package com.test.springboot.juc.threadLocal;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author syf_12138
 * @Description 卖票总数、个人卖票数量统计
 * @create 2022/7/18 16:33
 */

public class ThreadLocalDemo {

    public static void main(String[] args) {
        testSynchronized();
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
}