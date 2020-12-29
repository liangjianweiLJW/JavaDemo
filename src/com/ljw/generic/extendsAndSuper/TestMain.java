package com.ljw.generic.extendsAndSuper;

/**
 * @Description: 测试上界通配符合下界通配符
 * @Author: jianweil
 * @date: 2020/12/29 13:54
 */
public class TestMain {

    public static void main(String[] args) {
        //泛型参数为Apple的Plate
        Plate<Apple> applePlate = new Plate<>();
        //泛型参数为Fruit的Plate
        Plate<Fruit> fruitPlate = new Plate<>();
        //泛型参数为Food的Plate
        Plate<Food> foodPlate = new Plate<>();

        /*声明一个 上界限定通配符的Plate引用，其上界为Fruit*/
        Plate<? extends Fruit> upBoundsFruitPlate1 = applePlate;
        Plate<? extends Fruit> upBoundsFruitPlate2 = fruitPlate;
        //compile error 编译器报类型错误
        //Plate<? extends Fruit> upBoundsFruitPlate3=foodPlate;

        /*声明一个下界限定通配符的Plate引用，其下界为Fruit*/
        Plate<? super Fruit> downBoundsFruitPlate1 = foodPlate;
        Plate<? super Fruit> downBoundsFruitPlate2 = fruitPlate;
        //compile error 编译器报类型错误
        //Plate<? super Fruit> downBoundsFruitPlate3=applePlate;


        /******SCENE 1 UP BOUNDS **********/
        //声明一个泛型是上界限定通配符的引用
        Plate<? extends Fruit> p1;
        //使其指向泛型类型为Apple的引用
        p1 = new Plate<Apple>();
        //尝试从Plate中拿出水果 OK
        Fruit f = p1.get();
        //Compile Error
        //尝试往盘子中放苹果 Error
        //p1.add(new Apple());

        /*****SCENE 2 DOWN BOUNDS**********/
        //定义一个泛型是下界限定通配符的引用
        Plate<? super Fruit> p2= new Plate<Fruit>();
        //尝试添加Apple OK
        p2.add(new Fruit());
        p2.add(new Apple());
        //Compile Error
        //尝试从盘子中取出食物 Error
        Food food = (Food) p2.get();
        Fruit fruit = (Fruit) p2.get();
        Apple A = (Apple) p2.get();
    }

}
