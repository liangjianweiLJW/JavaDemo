package com.ljw.thread.volatileTest;

/**
 * @Description: volatile test
 *   保持属性在各个线程的可见性
 *   防止指令重编排
 * @Author: jianweil
 * @date: 2021/1/12 13:55
 */
public class VolatileTest {

    private static boolean runFlag = false; // 此处没有加 volatile

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            System.out.println("线程一等待执行");
            while (!runFlag) {
                //System.out.println(runFlag);
            }
            System.out.println("线程一开始执行");
        }).start();

        Thread.sleep(1000);
        new Thread(() -> {
            System.out.println("线程二开始执行");
            runFlag = true;
            System.out.println("线程二执行完毕");
        }).start();
    }

}
