package com.baizhi.shiro.test;


import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @author syf_12138
 * @Description 测试MD5加盐、多次散列加密
 * @create 2022/7/25 11:35
 */

public class TestMD5 {

    public static void main(String[] args) {
        testMD5("syf_12139", "12139", 10);
    }

    
    /**
     * @Author syf_12138
     * @Description 测试MD5加盐、多次散列加密
     * @param: str 加密字符串
     * @param: salt 盐值
     * @param: count 散列次数
     * @Date 2022/7/25 11:40
     */
    private static void testMD5(String str, String salt, Integer count) {
        Md5Hash result = new Md5Hash(str, salt, count);
        System.out.println("syf_12139 MD5加密后结果为：" + result);
    }
}
