package com.hik.seckill.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wangJinChang on 2019/11/11 13:56
 * 时间相关的工具类
 */
public class DateUtil {

    private static final String YMDHMS_DATE_FORMAT_T = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    private static final String ISO8601_DATE_FORMAT_T = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    private static final String YMDHMS_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String YMDHMS_DATE_FORMAT_V1 = "yyyy/MM/dd HH:mm:ss";

    private static final String CSV_DATE_FORMAT = "yyyyMMddHHmmss";
    private static final String YMD_DATE_FORMAT = "yyyy-MM-dd";
    private static final String YM_DATE_FORMAT = "yyyy-MM";
    private static final String YYYYMMDDHHMMSS_DATE_FORMATE = "yyyyMMddHHmmssSSS";

    //1毫秒
    private static final long ONE_MILLI_SECOND = 1l;
    //1秒
    private static final long ONE_SECOND = ONE_MILLI_SECOND * 1000;
    //1分钟
    private static final long ONE_MINUTE = ONE_SECOND * 60;
    //1小时
    private static final long ONE_HOUR = ONE_MINUTE * 60;
    //1天
    private static final long ONE_DAY = ONE_HOUR * 24;

    public static Date praseDate(String dateStr) throws ParseException {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        return new SimpleDateFormat(YMDHMS_DATE_FORMAT).parse(dateStr);
    }

    public static Date parseYMDDate(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        try {
            return new SimpleDateFormat(YMD_DATE_FORMAT).parse(dateStr);
        } catch (ParseException e) {

        }
        return null;
    }

    /**
     * 格式化Date
     *
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(YMDHMS_DATE_FORMAT).format(date);
    }

    /**
     * 格式化Date
     *
     * @param date
     * @return yyyy/MM/dd HH:mm:ss
     */
    public static String formatDateV1(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(YMDHMS_DATE_FORMAT_V1).format(date);
    }

    public static String formatISODate(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(ISO8601_DATE_FORMAT_T).format(date);
    }

    public static String formatYMDDate(Date date) {
        if (date != null) {
            return new SimpleDateFormat(YMD_DATE_FORMAT).format(date);
        } else {
            return "";
        }
    }

    public static String formatYMDate(Date date) {
        if (date != null) {
            return new SimpleDateFormat(YM_DATE_FORMAT).format(date);
        } else {
            return "";
        }
    }

    public static String formatYMDDateT(Date date) {
        if (date != null) {
            return new SimpleDateFormat(YMDHMS_DATE_FORMAT_T).format(date);
        } else {
            return "";
        }
    }

    public static String formatYMDDateT(BigInteger now) {
        Date date = new Date(now.longValue());
        return new SimpleDateFormat(YMDHMS_DATE_FORMAT_T).format(date);
    }

    public static String convertToUTC(String timeStr) throws ParseException {
        if (StringUtils.isBlank(timeStr)) {
            return "";
        }
        Date date = new SimpleDateFormat(YMDHMS_DATE_FORMAT).parse(timeStr);
        return new SimpleDateFormat(YMDHMS_DATE_FORMAT_T).format(date);
    }

    /**
     * 获取某天的起始时间
     * 获取当前时间day天之前的日期Date
     *
     * @param day
     * @param date
     * @return
     */
    public static Date getOneDayOnset(Integer day, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1 * day);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 两个时间的间隔（计算的数据如果不是整形会被四舍五入，请选择适当的间隔单元）
     *
     * @param date1 日期
     * @param date2 日期
     * @param unit  间隔单元 如 ONE_HOUR(一小时) ONE_DAY(一天) ONE_YEAR(一年)
     * @return 日期差值
     */
    private static long compare(Date date1, Date date2, long unit) {
        if (unit == 0) {
            return date1.getTime() - date2.getTime();
        }
        Long num1 = date1.getTime();
        Long num2 = date2.getTime();
        double num3 = (double) (num1 - num2) / unit;
        return Math.round(num3);
    }

    /**
     * @param date1
     * @param date2
     * @return 月份相差几个月
     * @throws ParseException
     */
    public static int getMonthSpace(String date1, String date2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        bef.setTime(sdf.parse(date1));
        aft.setTime(sdf.parse(date2));
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        return Math.abs(month + result);

    }

    public static int getMonthSpaceYm(String date1, String date2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        bef.setTime(sdf.parse(date1));
        aft.setTime(sdf.parse(date2));
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        return Math.abs(month + result);

    }

    /**
     * 两个时间的间隔的天数（计算的数据如果不是整形会被四舍五入）
     *
     * @param date1 日期
     * @param date2 日期
     * @return 日期相差天数
     */
    public static Long compareToDay(Date date1, Date date2) {
        return compare(date1, date2, ONE_DAY);
    }

    /**
     * 两个时间的间隔的分钟数（计算的数据如果不是整形会被四舍五入）
     *
     * @param date1 日期
     * @param date2 日期
     * @return 日期相差天数
     */
    public static Long compareToMinute(Date date1, Date date2) {
        return compare(date1, date2, ONE_MINUTE);
    }


    /**
     * 当前时间加上15天时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String addFifteenDays() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 24 * 15);
        return formatDate(cal.getTime());
    }

    /**
     * 当前时间减去对应天数
     *
     * @return
     */
    public static Date reduceSomeDays(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -24 * days);
        return cal.getTime();
    }

    /**
     * 获取当前时间,精确到秒
     *
     * @return
     */
    public static String getCurrentTime2String() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int YY = cal.get(Calendar.YEAR);
        int MM = cal.get(Calendar.MONTH) + 1;
        int DD = cal.get(Calendar.DATE);
        int HH = cal.get(Calendar.HOUR_OF_DAY);
        int mm = cal.get(Calendar.MINUTE);
        int SS = cal.get(Calendar.SECOND);
        String time = "" + YY + MM + DD + HH + mm + SS;
        return time;
    }

    /**
     * 获取当前时间,精确到微秒
     *
     * @return
     */
    public static String getCurrentAccurateTime2String() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int YY = cal.get(Calendar.YEAR);
        int MM = cal.get(Calendar.MONTH) + 1;
        int DD = cal.get(Calendar.DATE);
        int HH = cal.get(Calendar.HOUR_OF_DAY);
        int mm = cal.get(Calendar.MINUTE);
        int SS = cal.get(Calendar.SECOND);
        int MS = cal.get(Calendar.MILLISECOND);
        String time = "" + YY + MM + DD + HH + mm + SS + MS;
        return time;
    }

    /**
     * 获取当天开始时间
     *
     * @return
     */
    public static long getCurrentDayStart() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime().getTime();
    }

    /**
     * 获取当天结束时间
     *
     * @return
     */
    public static long getCurrentDayEnd() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime().getTime();
    }

    public static Date getBeforeNMonthTime(int num) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -num);
        Date beforeNMonthTime = cal.getTime();
        return beforeNMonthTime;
    }

    /**
     * 获取精确到秒的时间并格式化
     *
     * @param date
     * @return yyyyMMddHHmmss
     */
    public static String formatCSVDate(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(CSV_DATE_FORMAT).format(date);
    }

    /**
     * 获取精确到毫秒的当前时间并格式化
     *
     * @return yyyyMMddHHmmssSSS
     */
    public static String getCurrentTimeStr() {
        return new SimpleDateFormat(YYYYMMDDHHMMSS_DATE_FORMATE).format(new Date());
    }

    /**
     * 获取五分钟之前的时间
     *
     * @return 五分钟前的时间
     */
    public static Date getFiveMinutesBefore() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -5);
        return cal.getTime();
    }

    /**
     * 获取五分钟之后的时间
     *
     * @return 五分钟后的时间
     */
    public static Date getFiveMinutesAfter() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 5);
        return cal.getTime();
    }

    /**
     * 获取本月开始时间
     *
     * @return 本月开始时间
     */
    public static Date getCurrentMonthStart() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取本月结束时间
     *
     * @return 本月结束时间
     */
    public static Date getCurrentMonthEnd() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

}
