package com.test.springboot.juc.cas;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author syf_12138
 * @Description 测试AtomicReference抽象代理
 * @create 2022/7/14 15:16
 */

public class AtomicReferenceDemo {

    public static void main(String[] args) {
        testAtomicReference();
    }

    /**
     * @Author syf_12138
     * @Description 测试AtomicReference抽象代理
     * @Return void
     * @Date 2022/7/14 15:33
     */
    private static void testAtomicReference() {
        AtomicReference<User> atomicReference = new AtomicReference<>();
        User z3 = new User("z3",22);
        User li4 = new User("li4",28);
        atomicReference.set(z3);
        System.out.println(atomicReference.compareAndSet(z3, li4) + " " + atomicReference.get().toString());
        System.out.println(atomicReference.compareAndSet(z3, li4) + " " + atomicReference.get().toString());
    }
}


@Getter
@ToString
@AllArgsConstructor
class User {

    String userName;
    int age;
}