package com.ljw.generic;

import java.util.List;

/**
 * @Description: 泛型
 * @Author: jianweil
 * @date: 2020/12/29 9:52
 */
public class GenericDemo<T> {
    private T t;
    public void set(T t) { this.t = t; }
    public T get() { return t; }

    public static <T> List<T> doSomething(List<T> list){
        return list;
    }
}
