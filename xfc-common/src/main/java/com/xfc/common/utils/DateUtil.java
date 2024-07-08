package com.xfc.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author: chenss
 * @date 2022-10-18 17:15
 */
public class DateUtil {
    private DateUtil() {}

    private static final String COMMON_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String COMMON_DAY_FORMAT = "yyyy-MM-dd";

    public static String dayToStr(Date date) {
        return dateToStr(date, COMMON_DAY_FORMAT);
    }

    public static String dateToStr(LocalDateTime localDateTime){
        return dateToStr(localDateTimeToDate(localDateTime));
    }

    public static String dateToStr(Date date){
        return dateToStr(date, COMMON_TIME_FORMAT);
    }

    public static String dateToStr(LocalDateTime localDateTime, String formatStr){
        return dateToStr(localDateTimeToDate(localDateTime), formatStr);
    }

    public static String dateToStr(Date date, String formatStr){
        try {
            //Date类型转换成字符串
            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            String nowTime = format.format(date);
            System.out.println("当前的时间：：" + nowTime);//2020-08-25 21:28:22
            return nowTime;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Date strToDate(String time,String formatStr){
        //字符串转Date类型
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        try {
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Date strToDate(String time){
        //字符串转Date类型
        SimpleDateFormat format = new SimpleDateFormat(COMMON_TIME_FORMAT);
        try {
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();

        if (localDateTime == null) {
            localDateTime = LocalDateTime.now();
        }

        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }
}
