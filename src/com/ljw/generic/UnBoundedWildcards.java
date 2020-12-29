package com.ljw.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 无界通配符
 * @Author: jianweil
 * @date: 2020/12/29 10:23
 */
public class UnBoundedWildcards {
    static List list1;
    static List<?> list2;
    static List<? extends Object> list3;

    static void assign1(List list) {
        list1 = list;
        list2 = list;
        /* 警告：
         * 泛型转换错误，这个时候只要List由原始类型转换为任何泛型都可以。
         * */
        list3 = list;

    }

    static void assign2(List<?> list) {
        list1 = list;
        list2 = list;
        /* 我觉得这个地方list3 = list并没有报错的原因还是因为
         * 类型擦除的原因*/
        list3 = list;
    }

    static void assign3(List<? extends Object> list) {
        list1 = list;
        list2 = list;
        list3 = list;
        /*类型擦除的原因*/
    }

    public static void main(String[] args) {
        assign1(new ArrayList());
        assign2(new ArrayList());
        /* 警告：
         * 泛型转换异常，ArrayList虽然可以转化为List和List<?> 但是由于assign3方法中参数要求转化为 List<? extends Object>
         * */
        assign3(new ArrayList());



        //所有泛型都可以转化为<?>从而被所有接受
        assign1(new ArrayList<String>());
        assign2(new ArrayList<String>());
        assign3(new ArrayList<String>());
        assign1(new ArrayList<>());
        assign2(new ArrayList<>());
        assign3(new ArrayList<>());

        List<?> wildList = new ArrayList();
        wildList = new ArrayList<String>();
        assign1(wildList);
        assign2(wildList);
        assign3(wildList);

        //------------------------------------
        List a0 = new ArrayList();
        List<Object> a1 = new ArrayList<>();
        List<?> a2 = new ArrayList<>();
        List<? extends Object> a3 = new ArrayList<>();
        List<? super Object> a4 = new ArrayList<>();

        a0 = a1;
        a0 = a2;
        a0 = a3;
        a0 = a4;
        //可见原型容器可以接受所有参数类容器的转型

        a1 = a0;
        //a1 = a2;//List<?>不能转换位List<Object>
        //a1 = a3;//List<? extends Object>不能转换位List<Object>
        a1 = a4;

        a2 = a0;//这个地方并没有出现警告
        a2 = a1;
        a2 = a3;
        a2 = a4;

        a3 = a0;//警告：在未检查的情况下进行泛型转换
        a3 = a1;
        a3 = a2;
        a3 = a4;

        a4 = a0;//警告：在未检查的情况下进行泛型转换
        a4 = a1;
        //a4 = a2;//List<?>不能转型为List<? super Object>，范围扩大
        //a4 = a3;//List<? extends Object>不能转型为List<? super Object>，范围扩大

        a0.add(new Object());
        a1.add(new Object());
        //a2.add(new Object());//编译错误，无解通配符实际范围要小于Object
        //a3.add(new Object());//编译错误，? extends Object不能将Object作为参数是因为泛型的范围小于Object
        a4.add(new Object());


    }
}