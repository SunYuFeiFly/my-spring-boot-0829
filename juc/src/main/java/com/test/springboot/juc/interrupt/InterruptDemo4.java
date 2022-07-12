package com.test.springboot.juc.interrupt;

/**
 * @author syf_12138
 * @Description 测试interrupted（）获取标志位数值并复位比值为数值为false
 * @create 2022/7/12 22:39
 */

public class InterruptDemo4 {

    public static void main(String[] args) {
        testInterrupted();
    }


    /**
     * @Author syf_12138
     * @Description TODO
     * @Return void
     * @Date 2022/7/12 22:40
     */
    private static void testInterrupted() {
        // 一开始线程默认状态标志位为false，执行interrupted（）后复位依然为false
        System.out.println(Thread.currentThread().getName()+"\t"+Thread.interrupted());
        System.out.println(Thread.currentThread().getName()+"\t"+Thread.interrupted());

        System.out.println("----1");
        Thread.currentThread().interrupt();// 中断标志位设置为true
        System.out.println("----2");

        // 上一步将状态标志位设置为true，执行interrupted（）获取结果true，后复位依为false
        System.out.println(Thread.currentThread().getName()+"\t"+Thread.interrupted());
        System.out.println(Thread.currentThread().getName()+"\t"+Thread.interrupted());

    }
}
