package com.test.springboot.juc.cf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 案例说明：电商比价需求，模拟如下情况：
 *
 * 1需求：
 *  1.1 同一款产品，同时搜索出同款产品在各大电商平台的售价;
 *  1.2 同一款产品，同时搜索出本产品在同一个电商平台下，各个入驻卖家售价是多少
 *
 * 2输出：出来结果希望是同款产品的在不同地方的价格清单列表，返回一个List<String>
 * 《mysql》 in jd price is 88.05
 * 《mysql》 in dangdang price is 86.11
 * 《mysql》 in taobao price is 90.43
 *
 * 3 技术要求
 *   3.1 函数式编程
 *   3.2 链式编程
 *   3.3 Stream流式计算
 *
 * @author Administrator
 */

public class CompletableFutureMallDemo {

    static List<NetMall> NetMallList = Arrays.asList(
            new NetMall("jd"),
            new NetMall("dangdang"),
            new NetMall("taobao"),
            new NetMall("pdd"),
            new NetMall("tmall")
    );

    private static int count = 1;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // test01();
        long startTime = System.currentTimeMillis();
        // 同步执行
        // List<String> list = getPrice(NetMallList, "Mysql");
        // 异步执行
        List<String> list = getPriceByCompletableFuture(NetMallList, "Mysql");
        System.out.println("同步执行耗时：" + (System.currentTimeMillis() - startTime));
        for (String s : list) {
            System.out.println("price：" + s);
        }
    }

    /**
     * 获取商品在每一个电商的价格,异步执行
     *
     * @param netMallList 平台名称列表
     * @param productName 商品名称
     * @return 该商品在各平台价格描述
     */
    private static List<String> getPriceByCompletableFuture(List<NetMall> netMallList, String productName) {
        count = 1;
        List<String> strList = netMallList.stream().map(netMall -> {
            return CompletableFuture.supplyAsync(() -> {
                return String.format(productName + " in %s price is %.2f",
                        netMall.getNetMallName(),
                        netMall.calcPrice(productName, count++));
            });
        }).collect(Collectors.toList())
        .stream()
        .map(s -> s.join())
        .collect(Collectors.toList());

        return strList;
    }

    /**
     * 获取商品在每一个电商的价格,单个同步执行
     */
    public static List<String> getPrice(List<NetMall> list,String productName) {
        // mysql in taobao price is 90.43
        return list.stream()
                .map(netMall ->
                        String.format(productName + "in %s price is %.2f",netMall.getNetMallName(), netMall.calcPrice(productName, count++)))
                .collect(Collectors.toList());
    }


    private static void test01() throws ExecutionException, InterruptedException {
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            return "12138";
        });

        System.out.println(stringCompletableFuture.get());
        System.out.println(stringCompletableFuture.join());
    }
}

class NetMall {

    @Getter
    private String netMallName;

    public NetMall(String netMallName) {
        this.netMallName = netMallName;
    }

    public double calcPrice(String productName, int count) {
        try {
            TimeUnit.SECONDS.sleep(2);
            System.out.println(productName + "第" + count + "次价格查询");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return ThreadLocalRandom.current().nextDouble() * 2;
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
class Student {

    private Integer id;
    private String studentName;
    private String major;
}