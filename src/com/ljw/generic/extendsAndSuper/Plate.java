package com.ljw.generic.extendsAndSuper;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2020/12/29 13:53
 */
public class Plate<T> {
    T t;
   public T get(){
        return t;
    }

    public void add(T t){
        this.t = t;
    }
}
