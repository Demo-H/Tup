package com.tupperware.biz.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dhunter on 2018/3/23.
 */

public class DateFormatter {

    /**设置每个阶段时间*/
    private static final int seconds_of_1minute = 60;

    private static final int seconds_of_30minutes = 30 * 60;

    private static final int seconds_of_1hour = 60 * 60;

    private static final int seconds_of_1day = 24 * 60 * 60;

    private static final int seconds_of_15days = seconds_of_1day * 15;

    private static final int seconds_of_30days = seconds_of_1day * 30;

    private static final int seconds_of_6months = seconds_of_30days * 6;

    private static final int seconds_of_1year = seconds_of_30days * 12;

    public String DateFormat(Date date) {
        String sDate;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        sDate = format.format(date);
        return sDate;
    }

    public static String MonthFormat(Date date) {
        String sDate;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        sDate = format.format(date);
        return sDate;
    }

    public static String DateFormatToSeconds(Date date) {
        String sDate;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sDate = format.format(date);
        return sDate;
    }

    public static Date stringToDate(String dateString){
        Date date=null;
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        try{
            date = sim.parse(dateString);
        }catch (ParseException e){
            Log.d("String to Date Error!:",dateString);
        }
        return date;
    }

    public Date dateAddOne(Date date){
        long time = date.getTime();
        time = time+24*60*60*1000;
        Date date1 = new Date(time);
        return  date1;
    }
    public Date dateMinusOne(Date date){
        long time = date.getTime();
        time = time-24*60*60*1000;
        Date date1 = new Date(time);
        return  date1;
    }

    public static String timestampToDateToSecond(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateTime = new Date(time);
        String dateString = formatter.format(dateTime);
        return dateString;
    }

    public static String DateToSecond(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }

    public static String DatetoString(long time) {
        time = time * 1000;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateTime = new Date(time);
        String dateString = formatter.format(dateTime);
        return dateString;
    }

    public static String timestampToDate(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateTime = new Date(time);
        String dateString = formatter.format(dateTime);
        return dateString;
    }

    public static String timeSecondToDateDay(long time) {
        time = time * 1000;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateTime = new Date(time);
        String dateString = formatter.format(dateTime);
        return dateString;
    }

    public static String timestampToTime(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dateTime = new Date(time);
        String dateString = formatter.format(dateTime);
        return dateString;
    }

    public static String timesecondToDate(long time) {
        time = time * 1000;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dateTime = new Date(time);
        String dateString = formatter.format(dateTime);
        return dateString;
    }

    /**
     * 日期格式字符串转换成时间戳
     * @param date 字符串日期
     * @return
     */
    public static long date2TimeStamp(String date){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(date).getTime()/1000;
//            return String.valueOf(sdf.parse(date).getTime()/1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 格式化时间
     * @param mTime
     * @return
     */
    public static String getTimeRange(long mTime) {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        /**获取当前时间*/
//        Date  curDate = new  Date(System.currentTimeMillis());
//        String dataStrNew= formatter.format(curDate);
//        Date startTime=null;
//        try {
//            /**将时间转化成Date*/
//            curDate=formatter.parse(dataStrNew);
//            startTime = formatter.parse(mTime);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        /**除以1000是为了转换成秒*/
//        long   between=(curDate.getTime()- startTime.getTime())/1000;
        long between = (System.currentTimeMillis() - mTime)/1000;
        int   elapsedTime= (int) (between);
        if (elapsedTime < seconds_of_1minute) {

            return "刚刚";
        }
        if (elapsedTime < seconds_of_30minutes) {

            return elapsedTime / seconds_of_1minute + "分钟前";
        }
        if (elapsedTime < seconds_of_1hour) {

            return "半小时前";
        }
        if (elapsedTime < seconds_of_1day) {

            return elapsedTime / seconds_of_1hour + "小时前";
        }
        if (elapsedTime < seconds_of_15days) {

            return elapsedTime / seconds_of_1day + "天前";
        }
        if (elapsedTime < seconds_of_30days) {

            return "半个月前";
        }
        if (elapsedTime < seconds_of_6months) {

            return elapsedTime / seconds_of_30days + "月前";
        }
        if (elapsedTime < seconds_of_1year) {

            return "半年前";
        }
        if (elapsedTime >= seconds_of_1year) {

            return elapsedTime / seconds_of_1year + "年前";
        }
        return "";
    }

    /**
     * 获得指定日期的前一天
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayBefore(String specifiedDay){
//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date=null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day=c.get(Calendar.DATE);
        c.set(Calendar.DATE,day-1);

        String dayBefore=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }
    /**
     * 获得指定日期的后一天
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedDayAfter(String specifiedDay){
        Calendar c = Calendar.getInstance();
        Date date=null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day=c.get(Calendar.DATE);
        c.set(Calendar.DATE,day+1);

        String dayAfter=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayAfter;
    }

    /**
     * 判断是今天还是历史
     * @param selectDate
     * @return
     */
    public static boolean isHistory(String selectDate) {
        if(selectDate.equals(new DateFormatter().timestampToDate(System.currentTimeMillis()))) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取当前月
     * @return
     */
    public static String getCurrentMonth() {
        long time = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月");
        Date dateTime = new Date(time);
        String dateString = formatter.format(dateTime);
        return dateString;
    }

    public static String getCurrentMonthbyFormat() {
        long time = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        Date dateTime = new Date(time);
        String dateString = formatter.format(dateTime);
        return dateString;
    }

    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getCurMonth() {
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前季度
     * @return
     */
    public static String getCurrentSeason() {
        int season = (getMonth() + 2) / 3;
        int year = getYear();
        String dateString = year + "年" + season + "季度";
        return dateString;
    }

    /**
     * 获取当前年
     */
    public static String getCurrentYear() {
        long time = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年");
        Date dateTime = new Date(time);
        String dateString = formatter.format(dateTime);
        return dateString;
    }

    public static int getCurrentYearForSetMin() {
        long time = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        Date dateTime = new Date(time);
        String dateString = formatter.format(dateTime);
        int year = StringUtils.StringChangeToInt(dateString);
        return year;
    }

    /**
     * 获取当天
     */
    public static String getCurrentDay() {
        long time = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateTime = new Date(time);
        String dateString = formatter.format(dateTime);
        return dateString;
    }

    public static Date getCurrentDate() {
        long time = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateTime = new Date(time);
        return dateTime;
    }

}
