package com.ljw.thread.synchronizedTest;

/**
 * @Description: synchronized跟ReentrantLock一样，也支持可重入锁。但是它是 一个关键字，是一种语法级别的同步方式，称为内置锁：
 * @Author: jianweil
 * @date: 2020/12/30 19:35
 */
public class Testsynchronized {

    public synchronized void execute() {
        System.out.println(Thread.currentThread().getName() + " do something synchronize");
        try {
            anotherLock();
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            System.err.println(Thread.currentThread().getName() + " interrupted");
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void anotherLock() {
        System.out.println(Thread.currentThread().getName() + " invoke anotherLock");
    }

    public static void main(String[] args) {
        Testsynchronized reentrantLockTest = new Testsynchronized();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLockTest.execute();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLockTest.execute();
            }
        });
        thread1.start();
        thread2.start();
    }
}
