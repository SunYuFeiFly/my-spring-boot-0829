package com.test.springboot.juc.locks;

public class LockSyncDemo {

    public static void main(String[] args) {
        new LockSyncDemo().m1();
    }

    Object object = new Object();

    public void m1() {
        synchronized (object) {
            System.out.println("----hello synchronized code block");
            throw new RuntimeException("-----exp");
        }
    }

    public synchronized void m2() {
        System.out.println("----hello synchronized m2");
    }

    public static synchronized void m3() {
        System.out.println("----hello static synchronized m3");
    }
}
