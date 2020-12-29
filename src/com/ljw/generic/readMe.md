# 泛型
## 前言
Java 泛型（generics）是 JDK 5 中引入的一个新特性, 泛型提供了编译时类型安全检测机制，该机制允许开发者在编译时检测到非法的类型。泛型的本质是参数化类型，也就是说所操作的数据类型被指定为一个参数
,泛型的好处就是在编译的时候能够检查类型安全，并且所有的强制转换都是自动和隐式的。

## 通配符
约定俗成的 T，E，K，V，本质上这些个都是通配符，没啥区别，只不过是编码时的一种约定俗成的东西。比如上述代码中的 T ，我们可以换成 A-Z 之间的任何一个 字母都可以，并不会影响程序的正常运行，
但是如果换成其他的字母代替 T ，在可读性上可能会弱一些。通常情况下，T，E，K，V，？是这样约定的：

- ？表示不确定的 java 类型

- T (type) 表示具体的一个java类型

- K V (key value) 分别代表java键值中的Key Value

- E (element) 代表Element

## 无界通配符 ？
对于不确定或者不关心实际要操作的类型，可以使用无限制通配符（尖括号里一个问号，即 <?> ），表示可以持有任何类型

## 上界通配符 < ? extends E>
上界：用 extends 关键字声明，表示参数化的类型可能是所指定的类型，或者是此类型的子类。

在类型参数中使用 extends 表示这个泛型中的参数必须是 E 或者 E 的子类，这样有两个好处：

- 如果传入的类型不是 E 或者 E 的子类，编译不成功、
- 泛型中可以使用 E 的方法，要不然还得强转成 E 才能使用

## 下界通配符 < ? super E>
下界: 用 super 进行声明，表示参数化的类型可能是所指定的类型，或者是此类型的父类型，直至 Object

在类型参数中使用 super 表示这个泛型中的参数必须是 E 或者 E 的父类

### < T >使用
- 1 <T>声明泛型类的类型参数

        public class GenericDemo<T> {
            private T t;
        }
        
- 2 <T>声明泛型方法

        public class GenericDemo<T> {
            private T t;
            public void set(T t) { this.t = t; }
            public T get() { return t; }
        
            public static <T> List<T> doSomething(List<T> list){
                return list;
            }
        }

第一个< T >声明是泛型方法，第二个List< T > 声明返回值, 形参 List< T > list 使用声明的泛型

set()和get也是泛型方法，他把类中的声明的T类型作为参数

- 3 声明泛型类不能用无界通配符< ? >

？和 T 都表示不确定的类型，区别在于我们可以对 T 进行操作，但是对 ？不行，比如如下这种 ：
        
        // 可以
        T t = operate();
        
        // 不可以
        ？car = operate();


### < ? >的使用

- 通配符是拿来使用定义好的泛型


    List<?> a2 = new ArrayList<String>();
    
    //a2.add("gggg");//报错
    
    List<?>这个写法非常坑。因为，这时候通配符会捕获具体的String类型，但编译器不叫它String，而是起个临时的代号，比如”capture#1“
    

- 例子


    public class UnBoundedWildcards {
        static List list1;
        static List<?> list2;
        static List<? extends Object> list3;
    
        static void assign1(List list) {
            list1 = list;
            list2 = list;
            /* 警告：
             * Unchecked，这个时候只要List由原始类型转换为任何泛型都可以。
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
    
            List<?> wildList = new ArrayList();
            wildList = new ArrayList<String>();
            assign1(wildList);
            assign2(wildList);
            assign3(wildList);
        }
    }

貌似< ？>和原生的没有区别，< ？>表示都是任意类型，而在这种情况下，< ? >可以被认为是一种装饰，但是它仍旧是很有价值的，因为，
实际上，它是在声明：“想用Java的泛型来编写代码，但是我在这里并不是要用原生类型，但是在当前这种情况下，泛型参数可以持有任何类型。”
  
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
          a1 = a2;//List<?>不能转换位List<Object>
          a1 = a3;//List<? extends Object>不能转换位List<Object>
          a1 = a4;
  
          a2 = a0;//这个地方并没有出现警告 要求不严格
          a2 = a1;
          a2 = a3;
          a2 = a4;
  
          a3 = a0;//警告：在未检查的情况下进行泛型转换 要求更加严格
          a3 = a1;
          a3 = a2;
          a3 = a4;
  
          a4 = a0;//警告：在未检查的情况下进行泛型转换
          a4 = a1;
          a4 = a2;//List<?>不能转型为List<? super Object>，范围扩大
          a4 = a3;//List<? extends Object>不能转型为List<? super Object>，范围扩大
  
          a0.add(new Object());
          a1.add(new Object());
          a2.add(new Object());//编译错误，无解通配符实际范围要小于Object
          a3.add(new Object());//编译错误，? extends Object不能将Object作为参数是因为泛型的范围小于Object
          a4.add(new Object());
 
 可以看出 < ? extends Object >相比较于< ? >对于原始类型的要求更加严格。 
  
 ### 无界通配符的一个重要的应用
 当你处理多个泛型参数的时，有时允许一个参数可以是任何的类型，同时为其他参数确定某种特定类型的能力会显得尤为重要。
 
        public class UnBoundedWildcards2 {
            static Map map1;
            static Map<?, ?> map2;
            static Map<String, ?> map3;
        
            static void assign1(Map map) {
                map1 = map;
            }
        
            static void assign2(Map<?, ?> map) {
                map2 = map;
            }
        
            static void assign3(Map<String, ?> map) {
                map3 = map;
            }
        
            public static void main(String[] args) {
                assign1(new HashMap());
                assign2(new HashMap());
                // warning
                // Unchecked assignment: 'java.util.HashMap' to 'java.util.Map<java.lang.String,?>'
                assign3(new HashMap());
                
                assign1(new HashMap<String, Integer>());
                assign2(new HashMap<String, Integer>());
                assign3(new HashMap<String, Integer>());
            }
        }
 
 当拥有的全都是无界通配符的时候，就像在Map<?,?>中看到的那样，编译器看起来无法将其与原生Map区分开了       
 
 

 ### 代码       
        //顶层接口 植物
        interface Plant{
        
        }
        //动物
        public interface Aninal {
        }
        
        // 父类 食物
        class Food{
        
        }
        
        // 水果 继承Food并实现Plant
        class Fruit extends Food implements Plant {
        
        }
        //苹果 具体的水果类型
        class Apple extends Fruit{
        
        }
        //香蕉 具体的水果类型
        class Banana extends Fruit{
        
        }
        
 泛型类      
        
        public class Plate<T> {
            T t;
           public T get(){
                return t;
            }
        
            public void add(T t){
                this.t = t;
            }
        }
        
 ### < ? extends E>使用
 
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
 ### < ? super E>使用
                 //泛型参数为Apple的Plate
                        Plate<Apple> applePlate = new Plate<>();
                        //泛型参数为Fruit的Plate
                        Plate<Fruit> fruitPlate = new Plate<>();
                        //泛型参数为Food的Plate
                        Plate<Food> foodPlate = new Plate<>();

                        /*声明一个下界限定通配符的Plate引用，其下界为Fruit*/
                        Plate<? super Fruit> downBoundsFruitPlate1 = foodPlate;
                        Plate<? super Fruit> downBoundsFruitPlate2 = fruitPlate;
                        //compile error 编译器报类型错误
                        //Plate<? super Fruit> downBoundsFruitPlate3=applePlate;
                
                        /*****SCENE 2 DOWN BOUNDS**********/
                        //定义一个泛型是下界限定通配符的引用
                        Plate<? super Fruit> p2= new Plate<Fruit>();
                        //尝试添加Apple OK
                        p2.add(new Fruit());
                        p2.add(new Apple());
                        //Compile Error
                        //尝试从盘子中取出食物 Error
                        // Food food = p2.get();
                        //强转
                        Food food = (Food) p2.get();
                        Fruit fruit = (Fruit) p2.get();
                        Apple A = (Apple) p2.get();
                 
                                       
- 上界只取不放是指当操作一个泛型参数为上界通配符<? extends T>的引用时，只能操作其返回泛型参数相关的方法(返回值为T的方法)而无法操作设置泛型参数相关(修改T)的方法。

- 下界只存不取则是指当操作一个泛型参数为下届通配符<? super T>的饮用时，只能操作设置泛型参数相关(修改T)而无法操作返回泛型参数相关的方法(返回值为T的方法)。
  
 
可以看到泛型类型为上界通配符Plate<? extends Fruit> p1,p1.get()方法编译是能通过的，但是p1.add(new Apple())编译器会报错。

而泛型类型为下界通配符Plate<? super Fruit> p2正好相反,p2.get()编译器会报错，但是p2.add(new Apple())是能成功运行的。

这时候，可能读者一脸懵逼的问，p1追根揭底不是指向Plate<Apple>的引用嘛，怎么p1.add(new Apple())还不允许了呢？没天理了不成？

原因是：代码编译后泛型被擦除

 ## 泛型擦除
 
Java的泛型是伪泛型，这是因为Java在编译期间，所有的泛型信息都会被擦掉，正确理解泛型概念的首要前提是理解类型擦除。Java的泛型基本上都是在编译器这个层次上实现的，在生成的字节码中是不包含泛型中的类型信息的，使用泛型的时候加上类型参数，在编译器编译的时候会去掉，这个过程成为类型擦除。
 
如在代码中定义List<Object>和List<String>等类型，在编译后都会变成List，JVM看到的只是List，而由泛型附加的类型信息对JVM是看不到的。Java编译器会在编译时尽可能的发现可能出错的地方，但是仍然无法在运行时刻出现的类型转换异常的情况，类型擦除也是Java的泛型与C++模板机制实现方式之间的重要区别。

针对上文的Plate类，我们可以通过反编译查看其泛型擦除后的状态(注意太高级的反编译工具依旧可能从编译后的注释信息中恢复泛型信息，因此这里要用低级一些的反编译工具，比如我用了JAD): 
  
        /针对Plate的反编译
        class Plate
        {
        
            Plate()
            {
            }
        
            Object get()
            {
                return t;
            }
        
            void add(Object t)
            {
                this.t = t;
            }
        
            Object t;
        }

再看上述从P1中取值的例子反编译结果

        Plate p1 = new Plate();
        Fruit f = (Fruit)p1.get();
  
 由于Java泛型仅提供编译时期的校验，编译后不再有泛型相关信息。因此Plate在编译后，内部的泛型类型T转换成了Object类型。
 但是由于编译器已经对类型安全做了校验，因此在对Plate取值时，可以将Object强转成Fruit，而不必担心出现类型转换错误。
 
 但是泛型通配符具体表示哪一个泛型类型却是运行时确定的，因此为了确保安全，编译器对泛型通配符的校验尤其严格。考虑以下情况:
 
             public void getFood(String favoriate){
                    Plate<? extends Fruit> p1;
                    if("Apple".equals(favoriate)){
                        p1 = new Plate<Apple>();
                    }else if("Banana".equals(favoriate)){
                        p1 = new Plate<Banana>();
                    }
                    // ...
                }

p1既可以指向Plate<Apple>，也可以指向Plate<Banana>，而这一过程是发生在运行时期的，因此编译器为了类型安全，是不允许p1.add(new Apple())的代码通过编译的。因为万一p1实际上指向的Plate<Banana>，那么就有可能在一个只允许存放Banana的盘子中放入了Apple，这样就相当于破坏了泛型的类型约束。
理解了这一点后，下界通配符的存取规则也就能理解了。同学们可以顺着这个思路自己思考一下。
                     
### 通过两个例子证明Java类型的类型擦除

 - 原始类型相等
 
 
            public class Test {
            
                public static void main(String[] args) {
            
                    ArrayList<String> list1 = new ArrayList<String>();
                    list1.add("abc");
            
                    ArrayList<Integer> list2 = new ArrayList<Integer>();
                    list2.add(123);
                    //true 通过list1对象和list2对象的getClass()方法获取他们的类的信息，最后发现结果为true。说明泛型类型String和Integer都被擦除掉了，只剩下原始类型。
                    System.out.println(list1.getClass() == list2.getClass());
                }
            
            }
  
  - 通过反射添加其它类型元素
  
  在程序中定义了一个ArrayList泛型类型实例化为Integer对象，如果直接调用add()方法，那么只能存储整数数据，不过当我们利用反射调用add()方法的时候，却可以存储字符串，这说明了Integer泛型实例在编译之后被擦除掉了，只保留了原始类型。
  
                
                 public static void main(String[] args) throws Exception {
                
                        ArrayList<Integer> list = new ArrayList<Integer>();
                
                        list.add(1);  //这样调用 add 方法只能存储整形，因为泛型类型的实例为 Integer
                
                        list.getClass().getMethod("add", Object.class).invoke(list, "asd");
                
                        for (int i = 0; i < list.size(); i++) {
                            System.out.println(list.get(i));
                             //1
                             //asd
                        }
                    }


### 类型擦除后保留的原始类型

原始类型 就是擦除去了泛型信息，最后在字节码中的类型变量的真正类型，无论何时定义一个泛型，相应的原始类型都会被自动提供，类型变量擦除，并使用其限定类型（无限定的变量用Object）替换。

ArrayList< Integer >被擦除类型后，原始类型也变为Object，所以通过反射我们就可以存储字符串了。

如果类型变量有限定，那么原始类型就用第一个边界的类型变量类替换。

        public class Pair<T extends Comparable> {}

那么原始类型就是Comparable。

要区分原始类型和泛型变量的类型。

在调用泛型方法时，可以指定泛型，也可以不指定泛型。

在不指定泛型的情况下，泛型变量的类型为该方法中的几种类型的同一父类的最小级，直到Object

在指定泛型的情况下，该方法的几种类型必须是该泛型的实例的类型或者其子类


### 类型检查就是针对引用，而无关它真正引用的对象。
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
                
 
 ### 类型擦除与多态的冲突和解决方法
 
        public class Pair<T> {
        
            private T value;
        
            public T getValue() {
                return value;
            }
        
            public void setValue(T value) {
                this.value = value;
            }
        }
        
       public class DateInter extends Pair<Date> {
       
           @Override
           public void setValue(Date value) {
               super.setValue(value);
           }
       
           @Override
           public Date getValue() {
               return super.getValue();
           }
       
       }
       
 在子类中，我们覆盖了父类的两个方法，我们的原意是这样的：将父类的泛型类型限定为Date，那么父类里面的两个方法的参数都为Date类型。
 
 我们在子类中重写这两个方法一点问题也没有，实际上，从他们的@Override标签中也可以看到，一点问题也没有，实际上是这样的吗？
 
 分析：实际上，类型擦除后，父类的的泛型类型全部变为了原始类型Object，所以父类编译之后会变成下面的样子：
 
        class Pair {  
            private Object value;  
        
            public Object getValue() {  
                return value;  
            }  
        
            public void setValue(Object  value) {  
                this.value = value;  
            }  
        }  
        
先来分析setValue方法，父类的类型是Object，而子类的类型是Date，参数类型不一样，这如果实在普通的继承关系中，根本就不会是重写，而是重载。

我们在一个main方法测试一下：

            public static void main(String[] args) throws ClassNotFoundException {  
                    DateInter dateInter = new DateInter();  
                    dateInter.setValue(new Date());                  
                    dateInter.setValue(new Object()); //编译错误  
            }
            
如果是重载，那么子类中两个setValue方法，一个是参数Object类型，一个是Date类型，可是我们发现，根本就没有这样的一个子类继承自父类的Object类型参数的方法。所以说，却是是重写了，而不是重载了。

原因是这样的，我们传入父类的泛型类型是Date，子类中重写参数类型为Date的那两个方法，实现继承中的多态。

可是由于种种原因，虚拟机并不能将泛型类型变为Date，只能将类型擦除掉，变为原始类型Object。这样，我们的本意是进行重写，实现多态。可是类型擦除后，只能变为了重载。这样，类型擦除就和多态有了冲突。JVM知道你的本意吗？知道！！！可是它能直接实现吗，不能！！！如果真的不能的话，那我们怎么去重写我们想要的Date类型参数的方法啊。

于是JVM采用了一个特殊的方法，来完成这项功能，那就是桥方法。

首先，我们用javap -c className的方式反编译下DateInter子类的字节码，结果如下：

            class com.tao.test.DateInter extends com.tao.test.Pair<java.util.Date> {  
              com.tao.test.DateInter();  
                Code:  
                   0: aload_0  
                   1: invokespecial #8                  // Method com/tao/test/Pair."<init>":()V  
                   4: return  
            
              public void setValue(java.util.Date);  //我们重写的setValue方法  
                Code:  
                   0: aload_0  
                   1: aload_1  
                   2: invokespecial #16                 // Method com/tao/test/Pair.setValue:(Ljava/lang/Object;)V  
                   5: return  
            
              public java.util.Date getValue();    //我们重写的getValue方法  
                Code:  
                   0: aload_0  
                   1: invokespecial #23                 // Method com/tao/test/Pair.getValue:()Ljava/lang/Object;  
                   4: checkcast     #26                 // class java/util/Date  
                   7: areturn  
            
              public java.lang.Object getValue();     //编译时由编译器生成的巧方法  
                Code:  
                   0: aload_0  
                   1: invokevirtual #28                 // Method getValue:()Ljava/util/Date 去调用我们重写的getValue方法;  
                   4: areturn  
            
              public void setValue(java.lang.Object);   //编译时由编译器生成的巧方法  
                Code:  
                   0: aload_0  
                   1: aload_1  
                   2: checkcast     #26                 // class java/util/Date  
                   5: invokevirtual #30                 // Method setValue:(Ljava/util/Date; 去调用我们重写的setValue方法)V  
                   8: return  
            }

从编译的结果来看，我们本意重写setValue和getValue方法的子类，竟然有4个方法，其实不用惊奇，最后的两个方法，就是编译器自己生成的桥方法。可以看到桥方法的参数类型都是Object，也就是说，子类中真正覆盖父类两个方法的就是这两个我们看不到的桥方法。而打在我们自己定义的setvalue和getValue方法上面的@Oveerride只不过是假象。而桥方法的内部实现，就只是去调用我们自己重写的那两个方法。

所以，虚拟机巧妙的使用了桥方法，来解决了类型擦除和多态的冲突。

这里面的setValue和getValue这两个桥方法的意义又有不同。

- setValue方法是为了解决类型擦除与多态之间的冲突。
- 而getValue却有普遍的意义，怎么说呢，如果这是一个普通的继承关系：

那么父类的setValue方法如下：

        public Object getValue() {  
            return super.getValue();  
        }
        
而子类重写的方法是：

        public Date getValue() {  
            return super.getValue();  
        }
        
其实这在普通的类继承中也是普遍存在的重写，这就是协变。

子类中的桥方法Object getValue()和Date getValue()是同时存在的，可是如果是常规的两个方法，他们的方法签名是一样的，也就是说虚拟机根本不能分别这两个方法。如果是我们自己编写Java代码，这样的代码是无法通过编译器的检查的，但是虚拟机却是允许这样做的，因为虚拟机通过参数类型和返回类型来确定一个方法，所以编译器为了实现泛型的多态允许自己做这个看起来“不合法”的事情，然后交给虚拟器去区别。

Java支持协变返回类型的重写方法。这意味着重写的方法可能具有更特定的返回类型。也就是说，只要新的返回类型可分配给你要覆盖的方法的返回类型，就可以使用。

这在Java语言规范的8.4.5节中指定：
    
如果返回类型是引用类型，则返回类型在彼此覆盖的方法之间可能会有所不同。返回类型可替换性的概念支持协变返回，即返回类型到子类型的特殊化。
    
当且仅当满足以下条件时，返回类型为R1的方法声明d1才可以替换为返回类型为R2的另一个方法d2：
    
- 如果R1为空，则R2为空。
- 如果R1是原始类型，则R2与R1相同。
- 如果R1是引用类型，则：
- R1是R2的子类型，或者R1可以通过未经检查的转换（第5.1.9节）转换为R2的子类型，或者 R1 = | R2 |
    

 ## ？和 T 的区别
 
 
 - 区别1：通过 T 来 确保 泛型参数的一致性
 
 
            // 通过 T 来 确保 泛型参数的一致性
            public <T extends Number> void test(List<T> dest, List<T> src)
            
            //通配符是 不确定的，所以这个方法不能保证两个 List 具有相同的元素类型
            public void test(List<? extends Number> dest, List<? extends Number> src)
 
 - 区别2：类型参数可以多重限定而通配符?不行
 
使用 & 符号设定多重边界（Multi Bounds)

            public class MultipleBoundaries implements Plant,Aninal{
                public static <T extends Plant & Aninal> void test(T t){
                    
                }
            }
指定泛型类型 T 必须是 Plant 和 Aninal 的共有子类型，此时变量 t 就具有了所有限定的方法和属性。对于通配符来说，因为它不是一个确定的类型，所以不能进行多重限定。 
 
 - 区别3：通配符?可以使用超类限定而类型参数不行
        
类型参数 T 只具有 一种 类型限定方式：

        T extends A
        
但是通配符 ? 可以进行 两种限定：

        ? extends A
        ? super A
        
 ### 使用场景
 - 类型参数< T >主要用于声明泛型类或泛型方法
 
 - 无界通配符< ? >主要用于使用泛型类或泛型方法
 
 - T 是一个 确定的 类型，通常用于泛型类和泛型方法的定义，
 ？是一个 不确定 的类型，通常用于泛型方法的调用代码和形参，不能用于定义类和泛型方法
 
 ##  Class< T > 和 Class<?> 区别
        
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

## 泛型在静态方法和静态类中的问题

泛型类中的静态方法和静态变量不可以使用泛型类所声明的泛型类型参数

            public class Test2<T> {    
                public static T one;   //编译错误    
                public static  T show(T one){ //编译错误    
                    return null;    
                }    
            }
            
因为泛型类中的泛型参数的实例化是在定义对象的时候指定的，而静态变量和静态方法不需要使用对象来调用。对象都没有创建，如何确定这个泛型参数是何种类型，所以当然是错误的。

但是要注意区分下面的一种情况：

            public class Test2<T> {    
            
                public static <T >T show(T one){ //这是正确的    
                    return null;    
                }    
            }

因为这是一个泛型方法，在泛型方法中使用的T是自己在方法中定义的 T，而不是泛型类中的T。




              