package org.studyonline.base.utils;


import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Date processing
 *
 */
public class DateUtil {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYYMMDD = "yyyyMMdd";

    public static final String HHmmss = "HHmmss";

    public static final String YYYYMM = "yyyyMM";

    private DateUtil() {
    }

    public static String toDateTime(LocalDateTime date) {
        return toDateTime(date, YYYY_MM_DD_HH_MM_SS);
    }

    public static String toDateTime(LocalDateTime dateTime, String pattern) {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern, Locale.SIMPLIFIED_CHINESE));
    }


    public static String toDateText(LocalDate date, String pattern) {
        if (date == null || pattern == null) {
            return null;
        }
        return date.format(DateTimeFormatter.ofPattern(pattern, Locale.SIMPLIFIED_CHINESE));
    }

    /**
     * From the given date, add hour hours to find the time hour hours after the specified date time.
     *
     * @param date 指定的时间
     * @param hour 多少小时后
     * @return
     */
    public static Date addExtraHour(Date date, int hour) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.HOUR_OF_DAY, hour);
        return cal.getTime();
    }

    /**
     * From the given date, add increase days
     *
     * @param date
     * @param increase
     * @return
     */
    public static Date increaseDay2Date(Date date, int increase) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.DAY_OF_MONTH, increase);
        return cal.getTime();
    }

    /**
     * From the given LocalDateTime, add increase months
     *
     * @param date
     * @param increase
     * @return
     */
    public static LocalDateTime localDateTimeAddMonth(LocalDateTime date, int increase) {
        // LocalDateTime --> Date
        Date temp = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
        // 日期加
        Calendar cal = Calendar.getInstance();
        if (temp != null) {
            cal.setTime(temp);
        }
        cal.add(Calendar.MONTH, increase);
        // LocalDateTime <-- Date
        return LocalDateTime.ofInstant(cal.getTime().toInstant(), ZoneId.systemDefault());
    }


    /**
     * Convert string date to Data object in yyyy-mm-dd format by default
     *
     * @param strDate
     * @return
     */
    public static Date format(String strDate, String format) {
        Date d = null;
        if (null == strDate || "".equals(strDate))
            return null;
        else
            try {
                d = getFormatter(format).parse(strDate);
            } catch (ParseException pex) {
                return null;
            }
        return d;
    }

    /**
     * Get a simple date formatting object
     *
     * @return A simple date formatting object
     */
    private static SimpleDateFormat getFormatter(String parttern) {
        return new SimpleDateFormat(parttern);
    }

    /**
     * Get all days of the month where month is located
     *
     * @param month      The date to query (if null, it defaults to the current month)
     * @param dateFormat Returns the date format (if null, returns the yyyy-MM-dd format result)
     * @return
     */
    public static List<String> getAllDaysOfMonthInString(Date month, DateFormat dateFormat) {
        List<String> rs = new ArrayList<String>();
        DateFormat df = null;
        if (null == dateFormat) {
            df = new SimpleDateFormat("yyyy-MM-dd");
        }
        Calendar cad = Calendar.getInstance();
        if (null != month) {
            cad.setTime(month);
        }
        int day_month = cad.getActualMaximum(Calendar.DAY_OF_MONTH); // 获取当月天数
        for (int i = 0; i < day_month; i++) {
            cad.set(Calendar.DAY_OF_MONTH, i + 1);
            rs.add(df.format(cad.getTime()));

        }
        return rs;
    }

    /**
     * Get all days of the month where month is located
     *
     * @param month The date to query (if null, it defaults to the current month)
     * @return Date List
     */
    public static List<Date> getAllDaysOfMonth(Date month) {
        List<Date> rs = new ArrayList<Date>();
        Calendar cad = Calendar.getInstance();
        if (null != month) {
            cad.setTime(month);
        }
        int day_month = cad.getActualMaximum(Calendar.DAY_OF_MONTH); // 获取当月天数
        for (int i = 0; i < day_month; i++) {
            cad.set(Calendar.DAY_OF_MONTH, i + 1);
            rs.add(cad.getTime());

        }
        return rs;
    }

    /**
     * Get all days in a specified date range
     *
     * @param begin
     * @param end
     * @param dateFormat (If null, returns the date in yyyy-MM-dd format.)
     * @return
     */
    public static List<String> getSpecifyDaysOfMonthInString(Date begin, Date end, DateFormat dateFormat) {
        DateFormat df = null;
        if (null == dateFormat) {
            df = new SimpleDateFormat("yyyy-MM-dd");
        }
        List<String> rs = new ArrayList<String>();
        List<Date> tmplist = getSpecifyDaysOfMonth(begin, end);
        for (Date date : tmplist)
            rs.add(df.format(date));
        return rs;
    }

    /**
     * Get all days in a specified date range
     *
     * @param begin
     * @param end
     * @return
     */
    public static List<Date> getSpecifyDaysOfMonth(Date begin, Date end) {
        List<Date> rs = new ArrayList<Date>();
        Calendar cad = Calendar.getInstance();
        int day_month = -1;
        if (null == begin) {// 设置开始日期为指定日期
            // day_month = cad.getActualMaximum(Calendar.DAY_OF_MONTH); // 获取当月天数
            cad.set(Calendar.DAY_OF_MONTH, 1);// 设置开始日期为当前月的第一天
            begin = cad.getTime();
        }
        cad.setTime(begin);
        if (null == end) {// 如果结束日期为空 ，设置结束日期为下月的第一天
            day_month = cad.getActualMaximum(Calendar.DAY_OF_MONTH); // 获取当月天数
            cad.set(Calendar.DAY_OF_MONTH, day_month + 1);
            end = cad.getTime();
        }
        cad.set(Calendar.DAY_OF_MONTH, 1);// 设置开始日期为当前月的第一天
        Date tmp = begin;
        int i = 1;
        while (true) {
            cad.set(Calendar.DAY_OF_MONTH, i);
            i++;
            tmp = cad.getTime();
            if (tmp.before(end)) {
                rs.add(cad.getTime());
            } else {
                break;
            }
        }
        return rs;
    }

    /**
     * Get current date
     *
     * @return A date of type <code>Date</code> containing year, month and day
     */
    public static synchronized Date getCurrDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static String format(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    /**
     * Get the current complete time, style: yyyy-MM-dd hh:mm:ss
     *
     * @return A <code>String</code> date containing year, month, day, hour, minute and second. yyyy-MM-dd hh:mm:ss
     */
    public static String getCurrDateTimeStr() {
        return format(getCurrDate(), YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * Get the day before the specified date
     *
     * @param specifiedDay YYYY_MM_DD_HH_MM_SS
     * @param formatStr    date type
     * @return
     */
    public static String getSpecifiedDayBefore(String specifiedDay, String formatStr) {// 可以用new
        // Date().toLocalString()传递参数
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        String dayBefore = new SimpleDateFormat(formatStr).format(c.getTime());
        return dayBefore;
    }

    /**
     * Get the day after the specified date
     *
     * @param specifiedDay YYYY_MM_DD_HH_MM_SS
     * @param formatStr    date type
     * @return
     */
    public static String getSpecifiedDayAfter(String specifiedDay, String formatStr) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        String dayAfter = new SimpleDateFormat(formatStr).format(c.getTime());
        return dayAfter;
    }

    /**
     * Get the date of the first day of the week
     *
     * @return
     */
    public static final String getWeekFirstDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 2;
        cal.add(Calendar.DATE, -day_of_week);
        return sdf.format(cal.getTime());
    }

    /**
     * Get the first day of the current month
     *
     * @return
     */
    public static final String getCurrentMonthFirstDay() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 当前月的第一天
        cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
        Date beginTime = cal.getTime();
        return sdf.format(beginTime);
    }

    /**
     * Get yesterday's start time
     *
     * @return
     */
    public static final String getYesterdayStart() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    public static final String getYesterdayEnd() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime()) + " 23:59:59";
    }

    public static final String getCurrDayStart() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    /**
     * Function: Get the first day of the specified month<br/>
     */
    public static final String getStartDayWithMonth(String month) throws ParseException {
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat mf = new SimpleDateFormat("yyyy-MM");
        Date date = mf.parse(month);
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 0);// 因为格式化时默认了DATE为本月第一天所以此处为0
        return sdf.format(calendar.getTime());
    }

    /**
     * Function: Get the last day of the specified month<br/>
     */
    public static final String getEndDayWithMonth(String month) throws ParseException {
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat mf = new SimpleDateFormat("yyyy-MM");
        Date date = mf.parse(month);
        calendar.setTime(date);
        calendar.roll(Calendar.DATE, -1);// api解释roll()：向指定日历字段添加指定（有符号的）时间量，不更改更大的字段
        return sdf.format(calendar.getTime());
    }

    public static final String formatYearMonthDay(String dateStr) throws ParseException {
        if (StringUtils.isNotBlank(dateStr)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dateStr);
            return sdf.format(date);
        } else {
            return "";
        }
    }

    /**
     *
     * Get the week number of this month based on the time yyyy-MM-dd
     */
    public static final int getWeekIndexOfMonth(String dateStr) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
        return weekOfMonth;
    }

    /**
     * Get the number of seconds between the current time and the specified time. Function:<br/>
     */
    public static final int getSecondToDesignationTime(String designationTime) {
        // 24小时制
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date toDate;
        try {
            toDate = dateFormat.parse(designationTime);
            int u = (int) ((toDate.getTime() - dateFormat.parse(DateUtil.getCurrDateTimeStr()).getTime()) / 1000);
            return u;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static final int getYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(cal.YEAR);
    }

    public static final int getMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(cal.MONTH) + 1;
    }

    public static final int getDay() {
        Calendar cal = Calendar.getInstance();
        return cal.get(cal.DATE);
    }

    /**
     * Determine the interval between two times by time seconds and milliseconds
     *
     * @param start
     * @param end
     * @return
     */
    public static int differentDaysByMillisecond(LocalDateTime start, LocalDateTime end) {
        // ZoneOffset.of("+8") 是指定为东8区
        return (int) ((end.toInstant(ZoneOffset.of("+8")).toEpochMilli() - start.toInstant(ZoneOffset.of("+8")).toEpochMilli()) / (1000 * 3600 * 24));
    }
}
