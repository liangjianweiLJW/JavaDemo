package com.ljw.generic.bridgeMethod;

/**
 * @Description: 类型擦除与多态的冲突和解决方法 -桥方法
 * @Author: jianweil
 * @date: 2020/12/29 16:11
 */
public class Pair<T> {

    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
