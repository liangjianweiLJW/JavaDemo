package com.ljw.generic;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * @Description: 类型擦除-原始类型相等
 * @Author: jianweil
 * @date: 2020/12/29 15:37
 */
public class TypeErasure {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        ArrayList<String> list1 = new ArrayList<String>();
        list1.add("abc");

        ArrayList<Integer> list2 = new ArrayList<Integer>();
        list2.add(123);
        //true
        System.out.println(list1.getClass() == list2.getClass());


        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);  //这样调用 add 方法只能存储整形，因为泛型类型的实例为 Integer
        list.getClass().getMethod("add", Object.class).invoke(list, "asd");
        for (int i = 0; i < list.size(); i++) {
            //1
            //asd
            System.out.println(list.get(i));
        }

        //引用 ArrayList<String> list11
        ArrayList<String> list11 = new ArrayList();
        list11.add("1"); //编译通过
        //list11.add(1); //编译错误
        String str1 = list11.get(0); //返回类型就是String

        // 引用 ArrayList list22，真正 new ArrayList<String>()
        ArrayList list22 = new ArrayList<String>();
        list22.add("1"); //编译通过
        list22.add(1); //编译通过
        Object object = list22.get(0); //返回类型就是Object

        new ArrayList<String>().add("11"); //编译通过
        //new ArrayList<String>().add(22); //编译错误


    }
}
