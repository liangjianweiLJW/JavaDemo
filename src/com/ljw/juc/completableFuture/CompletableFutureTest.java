package com.ljw.juc.completableFuture;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * @Description: CompletableFuture 测试
 * @Author: jianweil
 * @date: 2022/1/21 13:27
 */
public class CompletableFutureTest {

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());


    /**
     * 默认线程池
     * 运行结果：
     * main.................start.....
     * main.................end......
     * 当前线程：ForkJoinPool.commonPool-worker-9
     * 运行结果：5
     */
    @Test
    public void defaultThread() {
        System.out.println("main.................start.....");
        CompletableFuture.runAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getName());
            int i = 10 / 2;
            System.out.println("运行结果：" + i);
        });
        System.out.println("main.................end......");
    }


    /**
     * 自定义线程池
     * 运行结果：
     * main.................start.....
     * main.................end......
     * 当前线程：pool-1-thread-1
     * 运行结果：5
     */
    @Test
    public void mYThread() {
        System.out.println("main.................start.....");
        CompletableFuture.runAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getName());
            int i = 10 / 2;
            System.out.println("运行结果：" + i);
        }, executor);
        System.out.println("main.................end......");
    }


    /***
     * 无返回值
     *  runAsync
     *  结果：
     * main.................start.....
     * main.................end......
     * 当前线程：33
     * 运行结果：5
     */
    @Test
    public void runAsync() {
        System.out.println("main.................start.....");
        CompletableFuture.runAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果：" + i);
        }, executor);
        System.out.println("main.................end......");
    }

    /**
     * 有返回值
     * supplyAsync
     * 结果：
     * main.................start.....
     * 当前线程：33
     * 运行结果：5
     * main.................end.....5
     */
    @Test
    public void supplyAsync() throws ExecutionException, InterruptedException {
        System.out.println("main.................start.....");
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果：" + i);
            return i;
        }, executor);
        System.out.println("main.................end....." + completableFuture.get());
    }

    /**
     * 方法完成后的感知
     * 有返回值并且有后续操作 whenComplete
     * 感知错误并返回指定值 exceptionally
     * <p>
     * 结果：
     * main.................start.....
     * 当前线程：33
     * 异步完成。。。。结果是：null...异常是：java.util.concurrent.CompletionException: java.lang.ArithmeticException: 除以零
     * main.................end.....0
     */
    @Test
    public void main3() throws ExecutionException, InterruptedException {
        System.out.println("main.................start.....");
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 0;
            System.out.println("运行结果：" + i);
            return i;
        }, executor).whenComplete((result, throwable) -> {
            //whenComplete虽然得到异常信息，但是不能修改返回信息
            System.out.println("异步完成。。。。结果是：" + result + "...异常是：" + throwable);
        }).exceptionally(throwable -> {
            //R apply(T t);
            //exceptionally可以感知错误并返回指定值
            return 0;
        });
        System.out.println("main.................end....." + completableFuture.get());
    }


    /**
     *结果：
     * main.................start.....
     * 当前线程：33
     * main.................end.....报错返回
     */
    @Test
    public void handle() throws ExecutionException, InterruptedException {
        System.out.println("main.................start.....");
        final CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 0;
            System.out.println("运行结果：" + i);
            return i;
        }, executor).handleAsync((in, throwable) -> {
            if (throwable != null) {
                return "报错返回";
            }
            return "正确了";
        });
        System.out.println("main.................end....." + completableFuture.get());

    }


    /**
     * 有返回值并且有后续操作 whenComplete
     * <p>
     * 结果：
     * main.................start.....
     * 当前线程：33
     * 异步完成。。。。结果是：null...异常是：java.util.concurrent.CompletionException: java.lang.ArithmeticException: 除以零
     * 报错了2
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void whenComplete() {
        System.out.println("main.................start.....");
        final CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            //int i = 10 / 0;
            int i = 10 / 10;
            System.out.println("运行结果：" + i);
            return i;
        }, executor).whenComplete((result, throwable) -> {
            //whenComplete虽然得到异常信息，但是不能修改返回信息
            System.out.println("异步完成。。。。结果是：" + result + "...异常是：" + throwable);
        });

        try {
            System.out.println("main.................end..T..." + completableFuture.get());
        } catch (InterruptedException e) {
            System.out.println("报错了1");
        } catch (ExecutionException e) {
            System.out.println("报错了2");
        }
    }


    /**
     * 方法完成后的感知
     * 感知错误并返回指定值 exceptionally
     * 结果：
     * main.................start.....
     * 当前线程：33
     * main.................end.....0
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void exceptionally() throws ExecutionException, InterruptedException {
        System.out.println("main.................start.....");
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 0;
            //int i = 10 / 10;
            System.out.println("运行结果：" + i);
            return i;
        }, executor).exceptionally(throwable -> {
            //R apply(T t);
            //exceptionally可以感知错误并返回指定值
            System.out.println("执行了exceptionally");
            return 0;
        });
        System.out.println("main.................end....." + completableFuture.get());
    }


    /**
     * 方法完成后的处理
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void main4() throws ExecutionException, InterruptedException {
        System.out.println("main.................start.....");
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            //int i = 10 / 2;
            int i = 10 / 5;
            //int i = 10 / 0;
            System.out.println("运行结果：" + i);
            return i;
        }, executor).handle((result, throwable) -> {
            //R apply(T t, U u);
            if (result == 5) {
                return result * 2;
            }
            if (throwable != null) {
                return 8888;
            }
            return 0;
        });
        System.out.println("main.................end....." + completableFuture.get());
    }

    /**
     * 线程串行化方法
     * <p>
     * //.thenApply()上面任务执行完执行+可以拿到结果+可以返回值
     * //.thenAccept()上面任务执行完执行+可以拿到结果
     * //.thenRun() 上面任务执行完执行
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    /**
     * 上面任务执行完执行
     * 结果
     * main.................start.....
     * 当前线程：33
     * 运行结果：5
     * 任务2启动了。。。。。
     */
    @Test
    public void thenRunAsync() throws ExecutionException, InterruptedException {
        System.out.println("main.................start.....");
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果：" + i);
            return i;
        }, executor).thenRunAsync(() -> {
            System.out.println("任务2启动了。。。。。");
        }, executor);
    }

    /**
     * 上面任务执行完执行+可以拿到结果
     * 结果:
     * thenAcceptAsync当前线程：33
     * thenAcceptAsync运行结果：5
     * thenAcceptAsync任务2启动了。。。。。上步结果：5
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void thenAcceptAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> thenAcceptAsync = CompletableFuture.supplyAsync(() -> {
            System.out.println("thenAcceptAsync当前线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("thenAcceptAsync运行结果：" + i);
            return i;
        }, executor).thenAcceptAsync(result -> {
            System.out.println("thenAcceptAsync任务2启动了。。。。。上步结果：" + result);
        }, executor);
    }

    /**
     * 上面任务执行完执行+可以拿到结果+可以返回值
     * 结果：
     * thenApplyAsync当前线程：33
     * thenApplyAsync运行结果：5
     * thenApplyAsync任务2启动了。。。。。上步结果：5
     * main.................end.....hello10
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void thenApplyAsync() throws ExecutionException, InterruptedException {

        CompletableFuture<String> thenApplyAsync = CompletableFuture.supplyAsync(() -> {
            System.out.println("thenApplyAsync当前线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("thenApplyAsync运行结果：" + i);
            return i;
        }, executor).thenApplyAsync(result -> {
            System.out.println("thenApplyAsync任务2启动了。。。。。上步结果：" + result);
            return "hello" + result * 2;
        }, executor);
        System.out.println("main.................end....." + thenApplyAsync.get());
    }


    /**
     * 当原任务完成后接收返回值，返回一个新的任务
     * 结果：
     * hello: thenCompose
     */
    @Test
    public void thenCompose() {
        CompletableFuture cf = CompletableFuture.completedFuture("hello")
                .thenCompose(str -> CompletableFuture.supplyAsync(() -> {
                    return str + ": thenCompose";
                }, executor));
        System.out.println(cf.join());
    }


    /**
     * 两任务组合 都要完成
     * <p>
     * completableFuture.runAfterBoth() 组合两个future
     * completableFuture.thenAcceptBoth() 获取两个future返回结果，无返回值
     * completableFuture.thenCombine() 获取两个future返回结果，有返回值
     */

    /**
     * 两任务组合 都要完成
     * completableFuture.runAfterBoth() 组合两个future
     * 结果：
     * 任务1线程：33
     * 任务1运行结果：5
     * 任务2线程：34
     * 任务2运行结果：
     * 任务3启动。。。
     */
    @Test
    public void runAfterBothAsync() {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务1线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("任务1运行结果：" + i);
            return i;
        }, executor);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务2线程：" + Thread.currentThread().getId());
            System.out.println("任务2运行结果：");
            return "hello";
        }, executor);

        CompletableFuture<Void> runAfterBothAsync = future1.runAfterBothAsync(future2, () -> {
            System.out.println("任务3启动。。。");
        }, executor);

    }

    /**
     * 两任务组合 都要完成
     * completableFuture.thenAcceptBoth() 获取两个future返回结果，无返回值
     * 结果：
     * 任务1线程：33
     * 任务1运行结果：5
     * 任务2线程：34
     * 任务2运行结果：
     * 任务4启动。。。结果1：5。。。结果2：hello
     */
    @Test
    public void thenAcceptBothAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务1线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("任务1运行结果：" + i);
            return i;
        }, executor);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务2线程：" + Thread.currentThread().getId());
            System.out.println("任务2运行结果：");
            return "hello";
        }, executor);

        CompletableFuture<Void> thenAcceptBothAsync = future1.thenAcceptBothAsync(future2, (result1, result2) -> {
            System.out.println("任务4启动。。。结果1：" + result1 + "。。。结果2：" + result2);
        }, executor);

    }


    /**
     * 两任务组合 都要完成
     * completableFuture.thenCombine()获取两个future返回结果，有返回值
     * 结果：
     * 任务1线程：33
     * 任务1运行结果：5
     * 任务2线程：34
     * 任务2运行结果：
     * 任务5启动。。。结果1：5。。。结果2：hello
     * 任务5结果hello-->5
     */
    @Test
    public void thenCombine() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务1线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("任务1运行结果：" + i);
            return i;
        }, executor);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务2线程：" + Thread.currentThread().getId());
            System.out.println("任务2运行结果：");
            return "hello";
        }, executor);
        CompletableFuture<String> thenCombineAsync = future1.thenCombineAsync(future2, (result1, result2) -> {
            System.out.println("任务5启动。。。结果1：" + result1 + "。。。结果2：" + result2);
            return result2 + "-->" + result1;
        }, executor);
        System.out.println("任务5结果" + thenCombineAsync.get());
    }

    /***
     * 两任务组合，一个任务完成就执行
     *
     *  objectCompletableFuture.runAfterEither() 其中一个执行完执行
     *  objectCompletableFuture.acceptEither() 其中一个执行完执行+获取返回值
     *  objectCompletableFuture.applyToEither() 其中一个执行完执行+获取返回值+有返回值
     *
     */
    /**
     * 两任务组合，一个任务完成就执行
     * <p>
     * objectCompletableFuture.runAfterEither() 其中一个执行完执行
     * 结果：
     * 任务1线程：33
     * 任务2线程：34
     * 任务2运行结果：
     * 任务3开始执行。。。
     */
    @Test
    public void runAfterEither() {
        CompletableFuture<Object> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务1线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            try {
                Thread.sleep(3000);
                System.out.println("任务1运行结果：" + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return i;
        }, executor);

        CompletableFuture<Object> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务2线程：" + Thread.currentThread().getId());
            System.out.println("任务2运行结果：");
            return "hello";
        }, executor);

        CompletableFuture<Void> runAfterEitherAsync = future1.runAfterEitherAsync(future2, () -> {
            System.out.println("任务3开始执行。。。");
        }, executor);
    }

    /**
     * 两任务组合，一个任务完成就执行
     * objectCompletableFuture.acceptEither() 其中一个执行完执行+获取返回值
     * 结果：
     * 任务1线程：33
     * 任务2线程：34
     * 任务2运行结果：
     * 任务4开始执行。。。结果：hello
     */
    @Test
    public void acceptEither() {
        CompletableFuture<Object> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务1线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            try {
                Thread.sleep(3000);
                System.out.println("任务1运行结果：" + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return i;
        }, executor);

        CompletableFuture<Object> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务2线程：" + Thread.currentThread().getId());
            System.out.println("任务2运行结果：");
            return "hello";
        }, executor);

        CompletableFuture<Void> acceptEitherAsync = future1.acceptEitherAsync(future2, result -> {
            System.out.println("任务4开始执行。。。结果：" + result);
        }, executor);

    }

    /**
     * 两任务组合，一个任务完成就执行
     * objectCompletableFuture.applyToEither() 其中一个执行完执行+获取返回值+有返回值
     * 结果：
     * 任务1线程：33
     * 任务2线程：34
     * 任务2运行结果：
     * 任务5开始执行。。。结果：hello
     * 任务5结果：hello world
     * <p>
     * Process finished with exit code 0
     */
    @Test
    public void applyToEither() throws ExecutionException, InterruptedException {
        CompletableFuture<Object> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务1线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            try {
                Thread.sleep(3000);
                System.out.println("任务1运行结果：" + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return i;
        }, executor);

        CompletableFuture<Object> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务2线程：" + Thread.currentThread().getId());
            System.out.println("任务2运行结果：");
            return "hello";
        }, executor);

        CompletableFuture<String> applyToEitherAsync = future1.applyToEitherAsync(future2, result -> {
            System.out.println("任务5开始执行。。。结果：" + result);
            return result.toString() + " world";
        }, executor);
        System.out.println("任务5结果：" + applyToEitherAsync.get());
    }


    /**
     * 多任务组合
     * <p>
     * allOf 等待所有任务完成
     * anyOf 只要一个任务完成
     */


    /**
     * 多任务组合
     * allOf 等待所有任务完成
     * 结果：
     * 任务1
     * 任务3
     * 任务2
     * allOf任务1-------任务2-------任务3
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void allOf() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务1");
            return "任务1";
        }, executor);
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("任务2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "任务2";
        }, executor);
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务3");
            return "任务3";
        }, executor);

        CompletableFuture<Void> allOf = CompletableFuture.allOf(future1, future2, future3);
        //等待所有任务完成
        //allOf.get();
        allOf.join();
        System.out.println("allOf" + future1.get() + "-------" + future2.get() + "-------" + future3.get());

    }


    /**
     * 多任务组合
     * anyOf 只要一个任务完成
     * 结果：
     * 任务1
     * anyOf--最先完成的是任务1
     * 任务3
     * 等等任务2
     * 任务2
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void anyOf() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务1");
            return "任务1";
        }, executor);
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("任务2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "任务2";
        }, executor);

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务3");
            return "任务3";
        }, executor);
        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(future1, future2, future3);
        System.out.println("anyOf--最先完成的是" + anyOf.get());
        //等待future2打印
        System.out.println("等等任务2");
        Thread.sleep(3000);
    }
}
