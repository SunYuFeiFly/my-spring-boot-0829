package com.test.springboot.juc.atomics;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author syf_12138
 * @Description 基本类型数组原子类
 * @create 2022/7/15 15:35
 */

public class AtomicIntegerArrayDemo {

    public static void main(String[] args) {
        testAtomicIntegerArray();
    }

    /**
     * @Author syf_12138
     * @Description 基本类型数组原子类操作
     * @Return void
     * @Date 2022/7/15 15:43
     */
    private static void testAtomicIntegerArray() {
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[5]);

        for (int i = 0; i < atomicIntegerArray.length(); i++) {
            System.out.println("第" + (i + 1) + "个元素的值是：" + atomicIntegerArray.get(i));
        }
        atomicIntegerArray.getAndSet(0, 12138);
        atomicIntegerArray.getAndDecrement(0);
        System.out.println("第0个元素的值是：" + atomicIntegerArray.get(0));
    }
}
