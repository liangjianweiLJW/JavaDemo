package com.ljw.base;

/**
 * @Description: 测试 toString
 * @Author: jianweil
 * @date: 2022/1/20 20:46
 */
public class ToStringTest {

    public static class  OverrideToString {

        String name;

        public OverrideToString(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "hello:" + this.name;
        }
    }

    public static class NoOverrideToString {
       
        public NoOverrideToString(String name) {
            this.name = name;
        }

        String name;
    }

    public static void main(String[] args) {

        final Object o = new Object();
        System.out.println(o);//java.lang.Object@bf23d05c

        final OverrideToString overrideToString = new OverrideToString("重写");
        System.out.println(overrideToString);//hello:重写

        final NoOverrideToString noOverrideToString = new NoOverrideToString("没有重写");
        System.out.println(noOverrideToString);//com.ljw.base.ToStringTest$NoOverrideToString@973ff776

    }
}
