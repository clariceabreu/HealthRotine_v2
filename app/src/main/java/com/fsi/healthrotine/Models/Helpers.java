package com.fsi.healthrotine.Models;


import java.util.Calendar;
import java.util.Date;

public class Helpers {
    public static Date addDays(Date currentDate, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, days);
        return new Date(c.getTimeInMillis());
    }
    public static Date addHours(Date currentDate, int hours) {
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.HOUR, hours);
        return new Date(c.getTimeInMillis());
    }
}
