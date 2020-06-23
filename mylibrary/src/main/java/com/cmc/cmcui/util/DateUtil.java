package com.cmc.cmcui.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间
 */
public class DateUtil {
    private static String time;

    /**
     * 获取当前的时间
     *
     * @return
     */
    public static String getCurrentDefaultDate() {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
        return dateformat.format(new Date());
    }

    /**
     * 获取当前的系统时间
     *
     * @return yyyy年MM月dd日
     */
    public static String getCurrentDateString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
        return dateFormat.format(new Date());
    }

    public static String getCurrentDateStrings() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(new Date());
    }

    public static String getCurrentData() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    /**
     * 获取当前时间
     *
     * @return 00:00:00
     */
    public static String getCurrentHourString() {
        Calendar calendar = Calendar.getInstance();
        //小时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//分钟
        int minute = calendar.get(Calendar.MINUTE);
//秒
        int second = calendar.get(Calendar.SECOND);
        return hour + ":" + minute + ":" + second;
    }

    /**
     * 计算时间差  时  分  秒
     *
     * @param data 结束时间
     * @param time 开始时间
     * @return
     */
    public static String getCurentHourgap(String data, String time) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse(data);
            Date d2 = df.parse(time);
            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别

            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            long second = (diff / 1000 - days * 24 * 60 * 60 - hours * 60 * 60 - minutes * 60);
            long a = 0;
            if (days == 1) {
                a = hours + 24;
            } else if (days == 2) {
                a = hours + 24 * 2;
            } else if (days == 3) {
                a = hours + 24 * 3;
            } else if (days == 4) {
                a = hours + 24 * 4;
            } else if (days == 0) {
                a = hours;
            }
            if (minutes > 10) {
                if (second > 10) {
                    time = "" + a + ":" + minutes + ":" + second;
                } else if (second == 10) {
                    time = "" + a + ":" + minutes + ":" + second;
                } else {
                    time = "" + a + ":" + minutes + ":0" + second;
                }
            } else if (minutes == 10) {
                if (second > 10) {
                    time = "" + a + ":" + minutes + ":" + second;
                } else if (second == 10) {
                    time = "" + a + ":" + minutes + ":" + second;
                } else {
                    time = "" + a + ":" + minutes + ":0" + second;
                }
            } else {
                if (second > 10) {
                    time = "" + a + ":0" + minutes + ":" + second;
                } else if (second == 10) {
                    time = "" + a + ":0" + minutes + ":" + second;
                } else {
                    time = "" + a + ":0" + minutes + ":0" + second;
                }

            }
        } catch (Exception e) {
        }

        return time;
    }

}
