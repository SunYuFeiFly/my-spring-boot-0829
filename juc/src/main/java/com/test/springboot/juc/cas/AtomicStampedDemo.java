package com.test.springboot.juc.cas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author syf_12138
 * @Description CAS中ABA问题演示，及我们将一个值更换为其他对象再更换回来，如果按照以前AtomicReference没有版本号，我们只对比值是否相等来判断，无法探知期间的变动
 * @create 2022/7/15 10:58
 */

public class AtomicStampedDemo {

    public static void main(String[] args) {
        TestAtomicStampedReference();
    }

    private static void TestAtomicStampedReference() {
        Book javaBook = new Book(1, "javaBook");
        // 存储javaBook
        AtomicStampedReference<Book> stampedReference = new AtomicStampedReference<>(javaBook, 1);
        System.out.println(stampedReference.getReference() + " 版本号： " + stampedReference.getStamp());

        Book mysqlBook = new Book(2,"mysqlBook");
        // 将书籍更换为我自己的
        boolean isReplace = false;
        isReplace = stampedReference.compareAndSet(javaBook, mysqlBook, stampedReference.getStamp(), stampedReference.getStamp() + 1);
        System.out.println(stampedReference.getReference() + " 版本号： " + stampedReference.getStamp() + (isReplace? "  替换成功" : "  替换不成功"));

        // 再讲我自己的书籍替换会最初的书籍
        isReplace = stampedReference.compareAndSet(mysqlBook, javaBook, stampedReference.getStamp(), stampedReference.getStamp() + 1);
        System.out.println(stampedReference.getReference() + " 版本号： " + stampedReference.getStamp() + (isReplace? "  替换成功" : "  替换不成功"));
    }
}


@NoArgsConstructor
@AllArgsConstructor
@Data
class Book {

    private int id;
    private String bookName;
}
