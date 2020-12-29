package com.ljw.generic.bridgeMethod;

import java.util.Date;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2020/12/29 16:11
 */
public class DateInter extends Pair<Date> {

    @Override
    public void setValue(Date value) {
        super.setValue(value);
    }

    @Override
    public Date getValue() {
        return super.getValue();
    }


    public static void main(String[] args) throws ClassNotFoundException {
        DateInter dateInter = new DateInter();
        dateInter.setValue(new Date());
        Date value = dateInter.getValue();
        //dateInter.setValue(new Object()); //编译错误
    }
}
