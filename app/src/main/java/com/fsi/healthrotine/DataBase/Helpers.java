package com.fsi.healthrotine.DataBase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Helpers {
    public static String strSeparator = "__,__";
    public static String convertArrayToString(List<Date> array){
        String str = "";
        if (array != null){
            for (Date date : array) {
                str = str + date.toString() + strSeparator;
            }
        }
        return str;
    }
    public static List<Date> convertStringToArray(String str){
        String[] arr = str.split(strSeparator);
        if (str != null && str.length() != 0){
            List<Date> dates = new ArrayList<Date>();

            for (int i = 0; i < arr.length; i ++){
                dates.add(new Date(arr[i]));
            }

            return dates;
        }
        else {
            return  null;
        }
    }
    public Date addDays(Date currentDate, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, days);
        return new Date(c.getTimeInMillis());
    }
    public Date addHours(Date currentDate, int hours) {
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.HOUR, hours);
        return new Date(c.getTimeInMillis());
    }
}
