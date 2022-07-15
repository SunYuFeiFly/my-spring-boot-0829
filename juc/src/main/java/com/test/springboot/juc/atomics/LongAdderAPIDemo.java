package com.test.springboot.juc.atomics;

import com.sun.org.apache.bcel.internal.generic.ARETURN;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.LongBinaryOperator;

/**
 * @author syf_12138
 * @Description LongAdder、LongAccumulator简单操作
 * @create 2022/7/15 18:25
 */

public class LongAdderAPIDemo {

    public static void main(String[] args) {
        // testLongAdder();
        testLongAccumulator();
    }


    /**
     * @Author syf_12138
     * @Description LongAccumulator在LongAdder基础上可以定义自己的计算方式，在LongBinaryOperator()方法内重写
     * @Return void
     * @Date 2022/7/15 18:32
     */
    private static void testLongAccumulator() {
        LongAccumulator longAccumulator = new LongAccumulator(new LongBinaryOperator() {
            @Override
            public long applyAsLong(long left, long right) {
                return left + right;
            }
        }, 0);

        // 相当于0 + 2
        longAccumulator.accumulate(2);
        // 相当于0 + 2 + 5
        longAccumulator.accumulate(5);

        System.out.println(longAccumulator.get());
    }


    /**
     * @Author syf_12138
     * @Description 初始值只能为0，且只能增加或减少操作
     * @Return void
     * @Date 2022/7/15 18:28
     */
    private static void testLongAdder() {
        LongAdder longAdder = new LongAdder();
        // +1
        longAdder.increment();
        longAdder.increment();
        System.out.println(longAdder.sum());

        // -1
        longAdder.decrement();
        System.out.println(longAdder.sum());

        // 重置为0
        longAdder.reset();
        System.out.println(longAdder.sum());
    }
}
