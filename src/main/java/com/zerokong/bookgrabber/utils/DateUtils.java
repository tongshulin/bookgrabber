package com.zerokong.bookgrabber.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {


    private static SimpleDateFormat defaultFullFormat;
    private static SimpleDateFormat hyphenFullFormat;
    private static SimpleDateFormat shortFormat;

    static {
        defaultFullFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        hyphenFullFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        shortFormat = new SimpleDateFormat("yyyyMMdd");
    }

    /**
     * 返回当前最新的时间，格式为YYYYMMDDHHmmss
     * @return
     */
    public static String getFullNow() {
        return defaultFullFormat.format(new Date());
    }

    /**
     * 返回当前最新的日期时间，格式为yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getHyphenFullNow() {
        return hyphenFullFormat.format(new Date());
    }

    /**
     * 返回当前最新的时间，格式为HHmmss
     * @return
     */
    public static String getShortNow() {
        return shortFormat.format(new Date());
    }

    /**
     * 返回自定义格式的日期、时间，可以日期，也可以是时间，由入参决定格式。
     * @param regex	String
     * 			返回的时间格式
     * @return
     * @throws Exception
     */
    public static String getDateTime(String regex) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(regex);
        return sdf.format(new Date());
    }



    public static Date parseShort(String dateStr){
        try {
            return shortFormat.parse(dateStr);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static Date parse(String dateStr, String pat){
        SimpleDateFormat format = new SimpleDateFormat(pat);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
