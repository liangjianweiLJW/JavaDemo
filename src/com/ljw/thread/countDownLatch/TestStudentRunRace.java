package com.ljw.thread.countDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: CountDownLatch 还可以实现学生一起比赛跑步的程序，
 * CountDownLatch 初始化为学生数量的线程，鸣枪后，每个学生就是一条线程，来完成各自的任务，
 * 当第一个学生跑完全程后，CountDownLatch 就会减一，直到所有的学生完成后，CountDownLatch 会变为 0 ，
 * 接下来再一起宣布跑步成绩。
 * @Author: jianweil
 * @date: 2020/12/22 18:44
 */
public class TestStudentRunRace {

    CountDownLatch stopLatch = new CountDownLatch(1);
    CountDownLatch runLatch = new CountDownLatch(10);

    public void waitSignal() throws Exception {
        System.out.println("选手" + Thread.currentThread().getName() + "正在等待裁判发布口令");
        stopLatch.await();
        System.out.println("选手" + Thread.currentThread().getName() + "已接受裁判口令");
        Thread.sleep((long) (Math.random() * 10000));
        System.out.println("选手" + Thread.currentThread().getName() + "到达终点");
        runLatch.countDown();
    }

    public void waitStop() throws Exception {
        Thread.sleep((long) (Math.random() * 10000));
        System.out.println("裁判" + Thread.currentThread().getName() + "即将发布口令");
        stopLatch.countDown();
        System.out.println("裁判" + Thread.currentThread().getName() + "已发送口令，正在等待所有选手到达终点");
        runLatch.await();
        System.out.println("所有选手都到达终点");
        System.out.println("裁判" + Thread.currentThread().getName() + "汇总成绩排名");
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        TestStudentRunRace studentRunRace = new TestStudentRunRace();
        for (int i = 0; i < 10; i++) {
            Runnable runnable = () -> {
                try {
                    studentRunRace.waitSignal();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            service.execute(runnable);
        }
        try {
            studentRunRace.waitStop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.shutdown();
    }
}
