package com.ljw.base;

/**
 * @Description: 测试 wait和 notify
 * @Author: jianweil
 * @date: 2022/1/20 20:29
 */
public class WaitNotifyTest {

    private static Object locker = new Object();

    public static void main(String[] args) throws InterruptedException {
        WaitNotifyTest test = new WaitNotifyTest();

        // 启动新线程，防止主线程被休眠
        new Thread(() -> {
            try {
                synchronized (locker) {
                    System.out.println("wait start");
                    //执行 wait()
                    locker.wait();
                    System.out.println("wait end");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // 为了确保 wait() 先执行再执行 notify()
        Thread.sleep(200);


        synchronized (locker) {
            System.out.println("notify start");
            //执行 notify ()
            locker.notify();
            System.out.println("notify end");
        }
    }
}
