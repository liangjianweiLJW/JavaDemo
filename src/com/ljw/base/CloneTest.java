package com.ljw.base;

/**
 * @Description: Object对象clone测试
 * <p>
 * @Author: jianweil
 * @date: 2022/1/20 18:57
 */
public class CloneTest implements Cloneable {
    // 声明变量
    String name;
    int age;

    public static void main(String[] args) {

        // 创建对象
        CloneTest cloner = new CloneTest();
        // 初始化变量
        cloner.name = "老王";
        cloner.age = 18;

        // 打印输出
        System.out.println(cloner.name); // 老王
        System.out.println(cloner.age); // 18

        try {
            // 创建 obj1 的拷贝
            CloneTest obj2 = (CloneTest) cloner.clone();
            // 使用 obj2 输出变量
            System.out.println(obj2.name); // 老王
            System.out.println(obj2.age); // 18
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("出错");
        }

    }

    /**
     * 如果对象没有实现Cloneable接口，即使复写了clone方法还是会抛出CloneNotSupportedException异常，所以很简单，实现一下Cloneable接口就可以了
     *
     * @return
     */
    @Override
    public CloneTest clone() {
        try {
            CloneTest clone = (CloneTest) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
