package com.ljw.generic.extendsAndSuper;

/**
 * @Description: Class<T> 和 Class<?> 区别
 * Class<T> 在实例化的时候，T 要替换成具体类。Class<?> 它是个通配泛型，? 可以代表任何类型，所以主要用于声明时的限制情况。比如，我们可以这样做申明：
 * @Author: jianweil
 * @date: 2020/12/29 15:22
 */
public class TestClass<T> {
    // 所以当不知道定声明什么类型的 Class 的时候可以定义一 个Class<?>。不需要类传入
    public Class<?> clazz;
    // 那如果也想 public Class<T> clazzT; 这样的话，就必须让当前的类TestClass<T>也指定 T,这样才不报错
    public Class<T> clazzT;

    /**
     * 通过反射的方式生成  multiLimit对象，这里比较明显的是，我们需要使用强制类型转换
     * MultiLimit multiLimit = (MultiLimit)
     * Class.forName("com.glmapper.bridge.boot.generic.MultiLimit").newInstance();
     * 对于上述代码，在运行期，如果反射的类型不是 MultiLimit 类，那么一定会报
     * java.lang.ClassCastException 错误。 对于这种情况，则可以使用下面的代码来代替，使得在在编译期就能直接 检查到类型的问题：
     *
     * @param clazz
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <E> E createInstance(Class<E> clazz) throws IllegalAccessException, InstantiationException {
        return clazz.newInstance();
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        A a = createInstance(A.class);
        B b = createInstance(B.class);
    }

}

class A {

}

class B {

}
