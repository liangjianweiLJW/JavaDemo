package com.ljw.base;

/**
 * @Description: Equals测试
 * @Author: jianweil
 * @date: 2022/1/20 19:10
 */
public class EqualsTest {
    public static void main(String[] args) {

        final Object o1 = new Object();
        final Object o2 = new Object();
        System.out.println(o1.equals(o2)); // false
        System.out.println(o1==o2); // false

        // String 类使用 equals() 方法
        String obj1 = new String();
        String obj2 = new String();

        // 判断 obj1 与 obj2 是否相等
        System.out.println(obj1.equals(obj2)); // true
        System.out.println(obj1==obj2); // false

        // 给对象赋值
        obj1 = "Runoob";
        obj2 = "Google";

        // 判断 obj1 与 obj2 是否相等
        // 两个值不同，内存地址也不同，所以不相等，返回 false
        System.out.println(obj1.equals(obj2)); // false

    }
}
