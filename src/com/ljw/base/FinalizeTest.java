package com.ljw.base;

import java.util.GregorianCalendar;

/**
 * @Description: finalize测试
 * @Author: jianweil
 * @date: 2022/1/20 19:19
 */
public class FinalizeTest extends GregorianCalendar {
    public static void main(String[] args) {
        try {

            FinalizeTest cal = new FinalizeTest();
            // 输出当前时间
            System.out.println("" + cal.getTime());

            // finalize cal
            System.out.println("Finalizing...");
            cal.finalize();
            System.out.println("Finalized.");

        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }
}
