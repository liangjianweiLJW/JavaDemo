package com.ljw.thread.countDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * @Description:
 *  认识 CountDownLatch
 * CountDownLatch类位于java.util.concurrent包下，CountDownLatch 能够使一个线程在等待另外一些线程完成各自工作之后，再继续执行。
 *
 * 它相当于是一个计数器，这个计数器的初始值就是线程的数量，每当一个任务完成后，计数器的值就会减一，
 * 当计数器的值为 0 时，表示所有的线程都已经任务了，然后在 CountDownLatch 上等待的线程就可以恢复执行接下来的任务。
 *
 * CountDownLatch 的使用
 * CountDownLatch 提供了一个构造方法，你必须指定其初始值，还指定了 countDown 方法，这个方法的作用主要用来减小计数器的值
 * ，当计数器变为 0 时，在 CountDownLatch 上 await 的线程就会被唤醒，继续执行其他任务。当然也可以延迟唤醒，
 * 给 CountDownLatch 加一个延迟时间就可以实现。
 *
 * CountDownLatch类只提供了一个构造器： public CountDownLatch(int count) {  };  //参数count为计数值
 * 然后下面这3个方法是CountDownLatch类中最重要的方法：
 *      //调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
 *      public void await() throws InterruptedException { };
 *      //和await()类似，只不过等待一定的时间后count值还没变为0的话就会继续执行
 *      public boolean await(long timeout, TimeUnit unit) throws InterruptedException { };
 *      //将count值减1
 *      public void countDown() { };
 *
 * @Author: jianweil
 * @date: 2020/12/22 18:41
 */
public class TestCountDownLatch {
    public static void main(String[] args) {
        /**
         * 在 main 方法中我们初始化了一个计数器为 5 的 CountDownLatch，在
         * Decrement 方法中我们使用 countDown 执行减一操作，然后睡眠一段时间，同时在 Increment 类中进行等待，
         * 直到 Decrement 中的线程完成计数减一的操作后，唤醒 Increment 类中的 run 方法，使其继续执行。
         */
        CountDownLatch latch = new CountDownLatch(5);
        Increment increment = new Increment(latch);
        Decrement decrement = new Decrement(latch);

        new Thread(increment).start();
        new Thread(decrement).start();

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Decrement implements Runnable {

    CountDownLatch countDownLatch;

    public Decrement(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {

            for (long i = countDownLatch.getCount(); i > 0; i--) {
                Thread.sleep(1000);
                System.out.println("countdown");
                this.countDownLatch.countDown();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class Increment implements Runnable {

    CountDownLatch countDownLatch;

    public Increment(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            System.out.println("await");
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Waiter Released");
    }
}