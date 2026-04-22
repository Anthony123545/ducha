package com.supervision.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime endtime = currentDateTime.withHour(23).withMinute(59);
        LocalDateTime starttime = currentDateTime.withHour(00).withMinute(00);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = starttime.format(formatter);
        String formattedDateTime1 = endtime.format(formatter);
        System.out.print(formattedDateTime);
        System.out.print(formattedDateTime1);
//        Date date1 =new Date();
//        Calendar calendarj = Calendar.getInstance();
//        calendarj.setTime(date1);
//        // 将时分秒,毫秒域清零
//        calendarj.set(Calendar.HOUR_OF_DAY, 20);
//        calendarj.set(Calendar.MINUTE, 00);
//        calendarj.set(Calendar.SECOND, 00);
//        System.out.print(calendarj.getTime());
//        Date date2 = new Date();
//        Calendar calendarl = Calendar.getInstance();
//        calendarl.setTime(date2);
//        // 将时分秒,毫秒域清零
//        calendarl.add(Calendar.DAY_OF_MONTH, 1);
//        calendarl.set(Calendar.HOUR_OF_DAY, 6);
//        calendarl.set(Calendar.MINUTE, 00);
//        calendarl.set(Calendar.SECOND, 00);
//        System.out.print(calendarl.getTime());

    }
}
