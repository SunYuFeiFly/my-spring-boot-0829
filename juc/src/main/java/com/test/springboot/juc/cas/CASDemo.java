package com.test.springboot.juc.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author syf_12138
 * @Description AtomicInteger原子操作类
 * @create 2022/7/14 10:23
 */

public class CASDemo {

    public static void main(String[] args) {
        testAtomicInteger();
    }

    /**
     * @Author syf_12138
     * @Description 测试AtomicInteger原子操作类
     * @Return void
     * @Date 2022/7/14 10:33
     */
    private static void testAtomicInteger() {
        AtomicInteger atomicInteger = new AtomicInteger(10);
        // 第一次对比修改前值符合，数值已经修改为2022
        System.out.println(atomicInteger.compareAndSet(10, 2022) + " " + atomicInteger.get());
        // 第二次对比数值不等，数值未修改
        System.out.println(atomicInteger.compareAndSet(10, 2022) + " " + atomicInteger.get());
    }
}
