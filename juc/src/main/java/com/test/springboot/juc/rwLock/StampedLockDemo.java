package com.test.springboot.juc.rwLock;

import com.sun.corba.se.spi.ior.IdentifiableFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * @author syf_12138
 * @Description 邮戳锁传统读写操作，写操作介入读操作乐观读升级为悲观读
 * @Date 2022/7/20 21:48
 **/

public class StampedLockDemo {

    static int number = 37;
    static StampedLock stampedLock = new StampedLock();

    /**
     * @Author syf_12138
     * @Description 邮戳锁写操作
     * @Return void
     * @Date 2022/7/20 23:09
     */
    public void write() {
        long stamp = stampedLock.writeLock();
        System.out.println(Thread.currentThread().getName() + "\t" + "写线程准备修改");
        try {
            number = number + 63;
        } finally {
            stampedLock.unlockWrite(stamp);
        }
        System.out.println(Thread.currentThread().getName() + "\t" + "写线程结束修改");
    }


    /**
     * @Author syf_12138
     * @Description 邮戳锁读操作
     * @Return void
     * @Date 2022/7/20 23:10
     */
    public void read() {
        long stamp = stampedLock.readLock();
        for (int i = 0; i < 4; i++) {
            //暂停几秒钟线程
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + " 正在读取中......");
        }

        try {
            int result = number;
            System.out.println(Thread.currentThread().getName() + "\t" + " 获得成员变量值result：" + result);
            System.out.println("写线程没有修改成功，读锁时候写锁无法介入，传统的读写互斥");
        } finally {
            stampedLock.unlockRead(stamp);
        }
    }


    /**
     * @Author syf_12138
     * @Description 乐观读，读的过程中也允许获取写锁介入
     * @Return void
     * @Date 2022/7/20 23:58
     */
    public void tryOptimisticRead() {
        long stamp = stampedLock.tryOptimisticRead();
        int result = number;
        System.out.println("4秒前stampedLock.validate方法值(true无修改，false有修改)" + "\t" + stampedLock.validate(stamp));
        // 故意间隔4秒钟，很乐观认为读取中没有其它线程修改过number值，具体靠判断
        for (int i = 0; i < 4; i++) {
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName()+"\t"+"正在读取... "+i+" 秒" +
                    "后stampedLock.validate方法值(true无修改，false有修改)"+"\t"+stampedLock.validate(stamp));
        }
        if (!stampedLock.validate(stamp)) {
            System.out.println("有人修改过------有写操作");
            stamp = stampedLock.readLock();
            try
            {
                System.out.println("从乐观读 升级为 悲观读");
                result = number;
                System.out.println("重新悲观读后result："+result);
            }finally {
                stampedLock.unlockRead(stamp);
            }
        }

        System.out.println(Thread.currentThread().getName()+"\t"+" finally value: "+result);
    }


    public static void main(String[] args) {
        // testStampLockForChuanTong();
        testStampedLock();
    }

    
    /**
     * @Author syf_12138
     * @Description 测试度过程中写操作介入时乐观读 升级为 悲观读
     * @Return void
     * @Date 2022/7/21 0:12
     */
    private static void testStampedLock() {
        StampedLockDemo resource = new StampedLockDemo();
        new Thread(() -> {
            resource.tryOptimisticRead();
        },"readThread").start();

        // 暂停2秒钟线程,读过程可以写介入，演示(每次读间隔1秒，这里2秒之后就会写介入)
        try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }

        // 暂停6秒钟线程(每次读间隔1秒，4秒后4次读完了，写操作还没开始)
        // try { TimeUnit.SECONDS.sleep(6); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t"+"----come in");
            resource.write();
        },"writeThread").start();
    }


    /**
     * @Author syf_12138
     * @Description 传统的读写测试，互相不干扰
     * @Return void
     * @Date 2022/7/21 0:05
     */
    private static void testStampLockForChuanTong() {
        StampedLockDemo resource = new StampedLockDemo();
        // 传统的读写操作
        new Thread(() -> {
            resource.read();
        }, "readThread").start();

        //暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "----come in");
            resource.write();
        }, "writeThread").start();

        //暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "\t" + "number:" + number);
    }
}
