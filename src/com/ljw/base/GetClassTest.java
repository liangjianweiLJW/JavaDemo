package com.ljw.base;

import java.util.ArrayList;

/**
 * @Description: getClass测试
 * @Author: jianweil
 * @date: 2022/1/20 19:27
 */
public class GetClassTest {
    public static void main(String[] args) {

        // getClass() with Object
        Object obj1 = new Object();
        System.out.println("obj1 的类为: " + obj1.getClass());//obj1 的类为: class java.lang.Object

        // getClass() with String
        String obj2 = new String();
        System.out.println("obj2 的类为: " + obj2.getClass());//obj2 的类为: class java.lang.String

        // getClass() with ArrayList
        ArrayList<Integer> obj3 = new ArrayList<>();
        System.out.println("obj3 的类为: " + obj3.getClass());//obj3 的类为: class java.util.ArrayList
    }
}
