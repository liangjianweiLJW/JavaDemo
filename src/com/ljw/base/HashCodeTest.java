package com.ljw.base;

import java.util.ArrayList;

/**
 * @Description: hashCode测试
 * @Author: jianweil
 * @date: 2022/1/20 19:33
 */
public class HashCodeTest {

    private int a;

    public HashCodeTest(int a) {
        this.a = a;
    }

    @Override
    public int  hashCode() {
        return 1;
    }

    public static void main(String[] args) {

        // Object 使用 hashCode(),默认会更加对象地址动态计算
        Object obj1 = new Object();
        System.out.println(obj1.hashCode());//-850578890

        Object obj2 = new Object();
        System.out.println(obj2.hashCode());//-689286518

        Object obj3 = new Object();
        System.out.println(obj3.hashCode());//1792384971


        // String 重写了 hashCode()
        String str1 = new String("haha");
        String str2 = new String("haha");
        System.out.println(str1.hashCode() == str2.hashCode()); // true
        System.out.println(str1.equals(str2)); // true

        // ArrayList 使用 hashCode()
        ArrayList<Integer> list = new ArrayList<>();
        System.out.println(list.hashCode()); // 1

        final HashCodeTest hashCodeTest1 = new HashCodeTest(1);
        final HashCodeTest hashCodeTest2 = new HashCodeTest(2);
        System.out.println(hashCodeTest1.hashCode() == hashCodeTest2.hashCode()); // true
        System.out.println(hashCodeTest1.equals(hashCodeTest2)); // false
    }
}
