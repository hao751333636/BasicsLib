package com.base.basemodule.utils.date;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.TimeUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期公共包
 *
 * @author tongqing
 * @version 2012-9-7 上午11:18:20
 */
public class DateUtil {
    /**
     * 时间格式数组
     */
    private static String[] dateFormat = {
            "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd",
            "yyyy/MM/dd", "HH:mm:ss",
            "yyyyMMddHHmmss", "yyyyMMdd",
            "yyyy-MM-dd HH:mm", "yyyyMMddHHmm",
            "yyyyMMddhhmmssSSS", "yyyy MM-dd",
            "yyyy/MM", "yyyy-MM",
            "yyyyMM", "yyyy-MM-dd'T'HH:mm:ss",
            "HH:mm", "yyyy.MM.dd",
            "MM/dd"};


    /**
     * @param args String[]
     * @throws ParseException ParseException
     */
    public synchronized static void main(String[] args) throws ParseException {


        String str = "沈阳市啊";

        boolean b = str.endsWith("市");
        if (b)
            System.out.print(str.substring(0, str.length() - 1));
    }

    /**
     * 将字符串时间按格式转换为Calendar
     *
     * @param dateStr 字符串时间
     * @param index   时间格式数组下标
     * @return Calendar
     */
    public static Calendar parseCalendar(String dateStr, int index) {
        if (dateStr == null || dateStr.trim().length() == 0) {
            return null;
        } else {
            Date result = parseDate(dateStr, index);
            Calendar cal = Calendar.getInstance();
            cal.setTime(result);
            return cal;
        }
    }

    /**
     * 将字符串时间按格式转换为Date
     *
     * @param dateStr 字符串时间
     * @param index   时间格式数组下标
     * @return Date
     */
    public static Date parseDate(String dateStr, int index) {
        DateFormat df = null;
        df = new SimpleDateFormat(dateFormat[index]);
        try {
            return df.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 将时间转换为yyyy-MM-dd HH:mm:ss格式的字符串
     *
     * @param date  Calendar
     * @param index 时间格式数组下标
     * @return 格式化后的时间
     */
    public static String toDateTimeStr(Calendar date, int index) {
        if (date == null) {
            return null;
        } else {
            return (new SimpleDateFormat(dateFormat[index])).format(date
                    .getTime());
        }
    }

    /**
     * 根据格式获取系统当前时间
     *
     * @param index 时间格式数组下标
     * @return String
     */
    public static String getSysDateTime(int index) {
        Calendar cal = Calendar.getInstance();
        return DateUtil.toDateTimeStr(cal, index);
    }

    /**
     * 取得当前小时分钟
     *
     * @return hhmm
     */
    public static String getSysDateMinute() {
        Calendar cal = Calendar.getInstance();
        String hh = new SimpleDateFormat("HH").format(cal.getTime());
        String mm = new SimpleDateFormat("mm").format(cal.getTime());
        return hh + mm;
    }

    /**
     *
     * 取得一个日期所在星期的星期一的日期
     *
     * @param dateStr
     *            当前日期
     * @param index
     *            时间格式数组下标
     * @return 星期一日起
     */
    // public static String getMonOfDateWeek(String dateStr, int index) {
    // Calendar cal = parseCalendar(dateStr, index);
    // int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    // int mod = (dayOfWeek + Constants.FIVE) % Constants.SEVEN;
    // cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
    // cal.get(Calendar.DAY_OF_MONTH) - mod);
    // return toDateTimeStr(cal, index);
    // }

    /**
     *
     * 取得一个日期所在星期的星期日的日期
     *
     * @param dateStr
     *            当前日期
     * @param index
     *            时间格式数组下标
     * @return 星期日日期
     */
    // public static String getSunOfDateWeek(String dateStr, int index) {
    // Calendar cal = parseCalendar(dateStr, index);
    // int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    // int mod = (dayOfWeek + Constants.FIVE) % Constants.SEVEN;
    // cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
    // cal.get(Calendar.DAY_OF_MONTH) - mod + Constants.SIX);
    // return toDateTimeStr(cal, index);
    // }

    /**
     * 取得一个日期所在星期的星期日的日期
     *
     * @return 星期日日期
     */
    public static int getNowWeek() {
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }

    /**
     * 得到当前时间的毫秒+000
     *
     * @return 毫秒+000
     */
    public static String getMillseconds() {
        Calendar cal = Calendar.getInstance();
        String mill = String.valueOf(cal.getTime().getTime());
        cal.setTimeInMillis(Long.parseLong(mill.concat("000")));
        String mails = String.valueOf(cal.getTime().getTime());
        return mails;
    }

    /**
     * 获取当前时间的毫秒数
     *
     * @return 毫秒数
     */
    public static long getNowLong() {
        Date d = new Date();
        return d.getTime();
    }

    /**
     *
     * 获得当前时间转化成long类型 距1970年的秒数
     *
     * @return long距1970年的秒数
     */
    // public static long getNowSeconds() {
    // Calendar cal = Calendar.getInstance();
    // String date = new SimpleDateFormat(dateFormat[0]).format(cal.getTime());
    // Calendar cd = parseCalendar(date, 0);
    // if (cd == null) {
    // return 0;
    // }
    // return (cd.getTimeInMillis() / Constants.MS);
    // }

    /**
     * 将毫秒数转换成字符串形式的时间
     *
     * @param millis 毫秒数
     * @param index  转出时间格式
     * @return 字符串时间
     */
    public static String parseDateLong(long millis, int index) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        String date = new SimpleDateFormat(dateFormat[index]).format(cal
                .getTime());
        return date;
    }

    /**
     *
     * 获取当前时间戳
     *
     * @return 时间戳
     */
    // public static String getSysTimeStamp() {
    // SimpleDateFormat sdFormat = new
    // SimpleDateFormat(dateFormat[Constants.EIGHT]);
    // Calendar cal = Calendar.getInstance();
    // String myTime = sdFormat.format(cal.getTime());
    // return myTime;
    // }

    /**
     * 获取传入时间后n天的时间
     *
     * @param date  当前时间 值为空 默认当前时间
     * @param index 当前时间 时间格式数组下标
     * @param days  n天 负数代表n天前
     * @return 计算后的时间
     */
    public static String getInputTimeAftDay(String date, int days, int index) {
        Calendar cal = Calendar.getInstance();
        if (date != null && !"".equals(date)) {
            Date dateP = parseDate(date, index);
            cal.setTime(dateP);
        } else {
            cal.setTime(new Date());
        }
        cal.add(Calendar.DATE, days);
        return new SimpleDateFormat(dateFormat[index]).format(cal.getTime());
    }

    /**
     * 获取传入时间n分钟后的时间
     *
     * @param date    当前时间 值为空 默认当前时间
     * @param index   当前时间 时间格式数组下标
     * @param minutes n分钟 负数代表n分钟前
     * @return 计算后的时间
     */
    public static String getInputTimeAftMinute(String date, int minutes,
                                               int index) {
        Calendar cal = Calendar.getInstance();
        if (date != null && !"".equals(date)) {
            Date dateP = parseDate(date, index);
            cal.setTime(dateP);
        } else {
            cal.setTime(new Date());
        }
        cal.add(Calendar.MINUTE, minutes);
        return new SimpleDateFormat(dateFormat[index]).format(cal.getTime());
    }

    /**
     * 传入时间如当前时间对比 传入时间小于当前时间返回true
     *
     * @param dateStr 传入时间
     * @param index   传入时间格式
     * @return 对比结果
     * @throws ParseException 格式异常
     */
    public static boolean checkDateIsBefore(String dateStr, int index)
            throws ParseException {
        // 当前时间
        Calendar calNow = Calendar.getInstance();
        calNow.setTime(parseDate(getSysDateTime(index), index));
        // 传入时间七天前时间
        Date date = parseDate(dateStr, index);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 传入时间小于当前时间
        boolean result = cal.before(calNow);
        return result;
    }

    /**
     * 时间前后对比 前一时间小于后裔时间返回true
     *
     * @param dateStrBef 传入时间
     * @param dateStrAft 传入时间
     * @param index      传入时间格式
     * @return 对比结果
     */
    public static boolean checkDateBigger(String dateStrBef, String dateStrAft,
                                          int index) {
        boolean result;
        try {
            // 后一个时间
            Date dateAft = parseDate(dateStrAft, index);
            Calendar calAft = Calendar.getInstance();
            calAft.setTime(dateAft);
            // 前一个时间
            Date date = parseDate(dateStrBef, index);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            // 前一个时间小于后一个时间
            result = cal.before(calAft);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return result;
    }

    /**
     * date转String
     *
     * @param date Date型时间
     * @param i    时间格式数组下标
     * @return 字符串型时间
     */
    public static String getDatetoString(Date date, int i) {
        String result = "";
        try {
            Calendar cal = Calendar.getInstance();
            if (date != null) {
                cal.setTime(date);
                result = new SimpleDateFormat(dateFormat[i]).format(cal
                        .getTime());
            }
        } catch (Exception e) {
            return "";
        }
        return result;
    }

    /**
     * 根据日期格式把日期字符串转化为日期格式
     *
     * @param dateStr 字符串时间
     * @param index   格式数组下标
     * @return Calendar
     */
    public static Calendar parseDateByFormat(String dateStr, int index) {
        if (dateStr == null || dateStr.trim().length() == 0) {
            return null;
        } else {
            Date result = parseDate(dateStr, index);
            Calendar cal = Calendar.getInstance();
            cal.setTime(result);
            return cal;
        }
    }

    /**
     * 将字符串时间按格式转换为long
     *
     * @param dateStr 字符串时间
     * @param index   时间格式数组下标
     * @return Calendar
     */
    public static long parseLongTime(String dateStr, int index) {
        if (dateStr == null || dateStr.trim().length() == 0) {
            return 0;
        } else {
            Date result = parseDate(dateStr, index);
            Calendar cal = Calendar.getInstance();
            cal.setTime(result);
            long time = cal.getTimeInMillis();
            return time;
        }
    }

    /**
     * 计算两个时间相隔的分钟数
     *
     * @param time1 时间1
     * @param time2 时间2
     * @param index 时间格式
     * @return 分钟间隔
     */
    public static long getMinuteInterval(String time1, String time2, int index) {
        Calendar cal1 = parseDateByFormat(time1, index);
        Calendar cal2 = parseDateByFormat(time2, index);
        long long1 = cal1.getTimeInMillis();
        long long2 = cal2.getTimeInMillis();
        long interval = (int) ((long2 - long1) / (1000 * 60));
        return interval;
    }

    /**
     * 计算参数时间戳的下一个时间戳
     *
     * @param time  当前时间戳
     * @param index 下一时间戳与当前时间戳相差的分钟
     * @return 下一个时间戳
     */
    public static String getNextTimeStamp(String time, int index) {
        Calendar cal = parseDateByFormat(time, 8);
        cal.add(Calendar.MINUTE, index);
        return new SimpleDateFormat(dateFormat[8]).format(cal.getTime());
    }

    /**
     *
     * 获取被5整除的时间戳
     *
     * @return 时间戳
     */
    // public static String getTimeStampFive() {
    // Calendar cal = Calendar.getInstance();
    // int year = cal.get(Calendar.YEAR);
    // int month = cal.get(Calendar.MONTH) + 1;
    // int day = cal.get(Calendar.DAY_OF_MONTH);
    // int hour = cal.get(Calendar.HOUR_OF_DAY);
    // int minute = cal.get(Calendar.MINUTE);
    // int num = minute % Constants.FIVE;
    // if ((num) != 0) {
    // minute = minute - num;
    // }
    // String timeStamp = year + getZero(month)
    // + getZero(day) + getZero(hour) + getZero(minute);
    // return timeStamp;
    // }

    /**
     * 时间戳补零
     *
     * @param num 数字
     * @return 补零后数字
     */
    private static String getZero(int num) {
        String str = String.valueOf(num);
        if (str.length() == 1) {
            str = "0" + str;
        }
        return str;
    }

    /**
     * TODO(时间字符串格式互相转换)
     *
     * @throws ParseException ParseException
     */
    public static String formatDate(String date, int oldIndex, int newIndex) {
        DateFormat format = new SimpleDateFormat(dateFormat[oldIndex]);
        DateFormat format2 = new SimpleDateFormat(dateFormat[newIndex]);
        Date dDate;
        String reTime = "";
        try {
            dDate = format.parse(date);
            reTime = format2.format(dDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reTime;
    }

    @SuppressWarnings("deprecation")
    public static List<String> findTimeStamps(String time1, String time2) {
        Date date1 = parseDate(time1, 9);
        Date date2 = parseDate(time2, 9);

        Calendar cal1 = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        cal1.setTime(date1);

        // List<Date> dateList = new ArrayList<Date>();

        List<String> dateStrList = new ArrayList<String>();
        /** */
        while (!date2.before(cal1.getTime())) {
            if (cal1.getTime().getMinutes() % 5 == 0) {
                // dateList.add(cal1.getTime());
                dateStrList.add(toDateTimeStr(cal1, 8));
                cal1.add(Calendar.MINUTE, 5);
            } else {
                cal1.add(Calendar.MINUTE, 1);
            }
        }
        return dateStrList;
    }

    @SuppressWarnings("deprecation")
    public static List<String> findTimeStampsByInterval(String time1,
                                                        String time2, int interval) {
        Date date1 = parseDate(time1, 9);
        Date date2 = parseDate(time2, 9);

        Calendar cal1 = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        cal1.setTime(date1);

        // List<Date> dateList = new ArrayList<Date>();

        List<String> dateStrList = new ArrayList<String>();
        /** */
        while (!date2.before(cal1.getTime())) {
            if (cal1.getTime().getMinutes() % interval == 0) {
                // dateList.add(cal1.getTime());
                dateStrList.add(toDateTimeStr(cal1, 8));
                cal1.add(Calendar.MINUTE, interval);
            } else {
                cal1.add(Calendar.MINUTE, 1);
            }
        }
        return dateStrList;
    }

    public static List<String> findTimeStampIntervalOneMinute(String startTime,
                                                              String endTime) {
        Date date1 = parseDate(startTime, 9);
        Date date2 = parseDate(endTime, 9);

        Calendar cal1 = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        cal1.setTime(date1);

        List<String> dateStrList = new ArrayList<String>();
        /** */
        while (!date2.before(cal1.getTime())) {
            dateStrList.add(toDateTimeStr(cal1, 4));
            cal1.add(Calendar.MINUTE, 1);
        }
        return dateStrList;
    }

    /***
     * TODO取得两个时间点之间的时间
     *
     * @throws ParseException ParseException
     */
    public static List<String> getBetweenDate(String strStartDate,
                                              String strEndDate) {
        ArrayList<String> dateArray = new ArrayList<String>();
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = df.parse(strStartDate);
            endDate = df.parse(strEndDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        startCalendar.setTime(startDate);
        endCalendar.setTime(endDate);
        while (true) {
            if (startCalendar.getTimeInMillis() <= endCalendar
                    .getTimeInMillis()) {
                String dateString = df.format(startCalendar.getTime());
                // System.out.println(dateString);
                dateArray.add(dateString);
            } else {
                break;
            }
            startCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dateArray;
    }

    /**
     * 校验数据时间戳是否是5的倍数，接收5分钟的数据
     *
     * @param timeStamp 时间戳
     * @return boolean
     */
    public static boolean checkTimeStamp(String timeStamp, int interval) {
        boolean result = false;
        long time = Long.parseLong(timeStamp);
        /** 时间戳能被5整除 */
        if ((time % interval) == 0) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }


    /**
     * @param time yyyy-MM-dd HH:mm
     * @return
     */
    public static String formatDateTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm");
        if (time == null || "".equals(time)) {
            return "";
        }
        Date date = null;
        try {
            date = format.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Calendar current = Calendar.getInstance();

        Calendar today = Calendar.getInstance(); // 今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        // Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
//        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance(); // 昨天

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH,
                current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
//        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);

        if (current.after(today)) {
            String s = time.split(" ")[1];
            s = s.substring(0, s.lastIndexOf(":"));
            return s;
        } else if (current.before(today) && current.after(yesterday)) {

            String s = time.split(" ")[1];
            s = s.substring(0, s.lastIndexOf(":"));
            return "昨天 " + s;
        } else {
            int index = time.indexOf("-") + 1;

            String substring = time.substring(index, time.length());
            substring = substring.substring(0, substring.lastIndexOf(":"));
            return substring;
        }
    }

    /**
     * @param time yyyy-MM-dd HH:mm
     * @return
     */
    public static String formatDateTime2(String time) {
        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm");
        SimpleDateFormat format2 = new SimpleDateFormat(
                "yyyy-MM-dd");
        if (time == null || "".equals(time)) {
            return "";
        }
        Date date = null;
        try {
            date = format.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Calendar current = Calendar.getInstance();

        Calendar today = Calendar.getInstance(); // 今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        // Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance(); // 昨天

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH,
                current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);

        if (current.after(today)) {
            return "今天 " + time.split(" ")[1];
        } else if (current.before(today) && current.after(yesterday)) {

            return "昨天 " + time.split(" ")[1];
        } else {
            int index = time.indexOf("-") + 1;
            time = time.substring(index, time.length());
            int index2 = time.lastIndexOf(" ");
            if (index2 != -1) {
                return time.substring(0, index2);
            } else {
                return time;

            }
        }
    }

    /**
     * 计算两个时间间隔
     *
     * @param beginDate
     * @param endDate
     * @return 返回天
     */
    public static int dateDiff(String beginDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(endDate);
        } catch (ParseException e) {
            date = new Date();
            e.printStackTrace();
        }
        long end = date.getTime();
        try {
            date = sdf.parse(beginDate);
        } catch (ParseException e) {
            date = new Date();
            e.printStackTrace();
        }
        long begin = date.getTime();
        long day = (end - begin) / (1000 * 3600 * 24); // 除1000是把毫秒变成秒
        return Integer.parseInt(Long.toString(day));
    }

}