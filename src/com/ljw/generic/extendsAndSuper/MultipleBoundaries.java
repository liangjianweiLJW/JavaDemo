package com.ljw.generic.extendsAndSuper;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2020/12/29 15:12
 */
public class MultipleBoundaries implements Plant,Aninal{
    public static <T extends Plant&Aninal> void test(T t){

    }
}
