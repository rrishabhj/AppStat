package com.example.android.appusagestatistics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

    public final static long SECOND_MILLIS = 1000;

    public final static long MINUTE_MILLIS = SECOND_MILLIS * 60;

    public final static long HOUR_MILLIS = MINUTE_MILLIS * 60;

    public final static long DAY_MILLIS = HOUR_MILLIS * 24;

    public final static long YEAR_MILLIS = DAY_MILLIS * 365;

    public static DateFormat OUT_DATE_FORMAT = new SimpleDateFormat(
            "dd/MM/yyyy");

    public static DateFormat OUT_TIME_FORMAT = new SimpleDateFormat("H:mm:ss");

    public static DateFormat OUT_DATETIME_FORMAT = new SimpleDateFormat(
            "d/M/yyyy H:mm:ss");

    public static DateFormat OUT_TIMESTAMP_FORMAT = new SimpleDateFormat(
            "d/M/yy H:mm:ss.SSS");

    public static DateFormat IN_DATE_FORMAT = new SimpleDateFormat("d/M/yy");

    public static DateFormat IN_TIME_FORMAT = new SimpleDateFormat("H:mm:ss");

    public static DateFormat IN_DATETIME_FORMAT = new SimpleDateFormat(
            "d/M/yy H:mm:ss");

    public static DateFormat IN_TIMESTAMP_FORMAT = new SimpleDateFormat(
            "d/M/yy H:mm:ss.SSS");

    public static DateFormat DATE_TIME_FORMAT = new SimpleDateFormat(
            "yyyyMMddkkmmss");

    public static Calendar calendar = new GregorianCalendar();

    static {
        IN_DATE_FORMAT.setLenient(false);
        IN_TIME_FORMAT.setLenient(false);
        IN_DATETIME_FORMAT.setLenient(false);
    }

    /**
     * The maximum date possible.
     */
    public static Date MAX_DATE = new Date(Long.MAX_VALUE);

    /**
     * /** <p> Checks if two dates are on the same day ignoring time. </p>
     *
     * @param date1 the first date, not altered, not null
     * @param date2 the second date, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either date is <code>null</code>
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    /**
     * <p> Checks if two calendars represent the same day ignoring time. </p>
     *
     * @param cal1 the first calendar, not altered, not null
     * @param cal2 the second calendar, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either calendar is <code>null</code>
     */
    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
                && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) == cal2
                .get(Calendar.DAY_OF_YEAR));
    }

    /**
     * <p> Checks if a date is today. </p>
     *
     * @param date the date, not altered, not null.
     * @return true if the date is today.
     * @throws IllegalArgumentException if the date is <code>null</code>
     */
    public static boolean isToday(Date date) {
        return isSameDay(date, Calendar.getInstance().getTime());
    }

    /**
     * <p> Checks if a calendar date is today. </p>
     *
     * @param cal the calendar, not altered, not null
     * @return true if cal date is today
     * @throws IllegalArgumentException if the calendar is <code>null</code>
     */
    public static boolean isToday(Calendar cal) {
        return isSameDay(cal, Calendar.getInstance());
    }

    /**
     * <p> Checks if the first date is before the second date ignoring time.
     * </p>
     *
     * @param date1 the first date, not altered, not null
     * @param date2 the second date, not altered, not null
     * @return true if the first date day is before the second date day.
     * @throws IllegalArgumentException if the date is <code>null</code>
     */
    public static boolean isBeforeDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isBeforeDay(cal1, cal2);
    }

    /**
     * <p> Checks if the first calendar date is before the second calendar date
     * ignoring time. </p>
     *
     * @param cal1 the first calendar, not altered, not null.
     * @param cal2 the second calendar, not altered, not null.
     * @return true if cal1 date is before cal2 date ignoring time.
     * @throws IllegalArgumentException if either of the calendars are
     *                                  <code>null</code>
     */
    public static boolean isBeforeDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        if (cal1.get(Calendar.ERA) < cal2.get(Calendar.ERA)) {
            return true;
        }
        if (cal1.get(Calendar.ERA) > cal2.get(Calendar.ERA)) {
            return false;
        }
        if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR)) {
            return true;
        }
        if (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR)) {
            return false;
        }
        return cal1.get(Calendar.DAY_OF_YEAR) < cal2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * <p> Checks if the first date is after the second date ignoring time.
     * </p>
     *
     * @param date1 the first date, not altered, not null
     * @param date2 the second date, not altered, not null
     * @return true if the first date day is after the second date day.
     * @throws IllegalArgumentException if the date is <code>null</code>
     */
    public static boolean isAfterDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isAfterDay(cal1, cal2);
    }

    /**
     * <p> Checks if the first calendar date is after the second calendar date
     * ignoring time. </p>
     *
     * @param cal1 the first calendar, not altered, not null.
     * @param cal2 the second calendar, not altered, not null.
     * @return true if cal1 date is after cal2 date ignoring time.
     * @throws IllegalArgumentException if either of the calendars are
     *                                  <code>null</code>
     */
    public static boolean isAfterDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        if (cal1.get(Calendar.ERA) < cal2.get(Calendar.ERA)) {
            return false;
        }
        if (cal1.get(Calendar.ERA) > cal2.get(Calendar.ERA)) {
            return true;
        }
        if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR)) {
            return false;
        }
        if (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR)) {
            return true;
        }
        return cal1.get(Calendar.DAY_OF_YEAR) > cal2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * <p> Checks if a date is after today and within a number of days in the
     * future. </p>
     *
     * @param date the date to check, not altered, not null.
     * @param days the number of days.
     * @return true if the date day is after today and within days in the future
     * .
     * @throws IllegalArgumentException if the date is <code>null</code>
     */
    public static boolean isWithinDaysFuture(Date date, int days) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return isWithinDaysFuture(cal, days);
    }

    /**
     * <p> Checks if a calendar date is after today and within a number of days
     * in the future. </p>
     *
     * @param cal  the calendar, not altered, not null
     * @param days the number of days.
     * @return true if the calendar date day is after today and within days in
     * the future .
     * @throws IllegalArgumentException if the calendar is <code>null</code>
     */
    public static boolean isWithinDaysFuture(Calendar cal, int days) {
        if (cal == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar today = Calendar.getInstance();
        Calendar future = Calendar.getInstance();
        future.add(Calendar.DAY_OF_YEAR, days);
        return (isAfterDay(cal, today) && !isAfterDay(cal, future));
    }

    /**
     * Returns the given date with the time set to the start of the day.
     */
    public static Date getStart(Date date) {
        return clearTime(date);
    }

    /**
     * Determines whether or not a date has any time values (hour, minute,
     * seconds or millisecondsReturns the given date with the time values
     * cleared.
     */

    /**
     * Returns the given date with the time values cleared.
     */
    public static Date clearTime(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * Determines whether or not a date has any time values.
     *
     * @param date The date.
     * @return true iff the date is not null and any of the date's hour, minute,
     * seconds or millisecond values are greater than zero.
     */
    public static boolean hasTime(Date date) {
        if (date == null) {
            return false;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (c.get(Calendar.HOUR_OF_DAY) > 0) {
            return true;
        }
        if (c.get(Calendar.MINUTE) > 0) {
            return true;
        }
        if (c.get(Calendar.SECOND) > 0) {
            return true;
        }
        if (c.get(Calendar.MILLISECOND) > 0) {
            return true;
        }
        return false;
    }

    /**
     * Returns the given date with time set to the end of the day
     */
    public static Date getEnd(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /**
     * Returns the maximum of two dates. A null date is treated as being less
     * than any non-null date.
     */
    public static Date max(Date d1, Date d2) {
        if (d1 == null && d2 == null) {
            return null;
        }
        if (d1 == null) {
            return d2;
        }
        if (d2 == null) {
            return d1;
        }
        return (d1.after(d2)) ? d1 : d2;
    }

    /**
     * Returns the minimum of two dates. A null date is treated as being greater
     * than any non-null date.
     */
    public static Date min(Date d1, Date d2) {
        if (d1 == null && d2 == null) {
            return null;
        }
        if (d1 == null) {
            return d2;
        }
        if (d2 == null) {
            return d1;
        }
        return (d1.before(d2)) ? d1 : d2;
    }

    public static java.util.Date newDateTime() {
        return new java.util.Date(
                (System.currentTimeMillis() / SECOND_MILLIS) * SECOND_MILLIS);
    }

    /**
     * Create a new Date. To the last day.
     */
    public static java.sql.Date newDate() {
        return new java.sql.Date(
                (System.currentTimeMillis() / DAY_MILLIS) * DAY_MILLIS);
    }

    /**
     * Create a new Time, with no date component.
     */
    public static java.sql.Time newTime() {
        return new java.sql.Time(System.currentTimeMillis() % DAY_MILLIS);
    }

    /**
     * Create a new Timestamp.
     */
    public static java.sql.Timestamp newTimestamp() {
        return new java.sql.Timestamp(System.currentTimeMillis());
    }

    /**
     * Get the seconds difference
     */
    public static int secondsDiff(Date earlierDate, Date laterDate) {
        if (earlierDate == null || laterDate == null) {
            return 0;
        }

        return (int) ((laterDate.getTime() / SECOND_MILLIS) - (
                earlierDate.getTime() / SECOND_MILLIS));
    }

    /**
     * Get the minutes difference
     */
    public static int minutesDiff(Date earlierDate, Date laterDate) {
        if (earlierDate == null || laterDate == null) {
            return 0;
        }

        return (int) ((laterDate.getTime() / MINUTE_MILLIS) - (
                earlierDate.getTime() / MINUTE_MILLIS));
    }

    /**
     * Get the hours difference
     */
    public static int hoursDiff(Date earlierDate, Date laterDate) {
        if (earlierDate == null || laterDate == null) {
            return 0;
        }

        return (int) ((laterDate.getTime() / HOUR_MILLIS) - (
                earlierDate.getTime() / HOUR_MILLIS));
    }

    /**
     * Get the days difference
     */
    public static int daysDiff(Date earlierDate, Date laterDate) {
        if (earlierDate == null || laterDate == null) {
            return 0;
        }

        return (int) ((laterDate.getTime() / DAY_MILLIS) - (
                earlierDate.getTime() / DAY_MILLIS));
    }

    /**
     * Tells you if the date part of a datetime is in a certain time range.
     */
    public static boolean isTimeInRange(java.sql.Time start, java.sql.Time end,
            java.util.Date d) {
        d = new java.sql.Time(d.getHours(), d.getMinutes(), d.getSeconds());

        if (start == null || end == null) {
            return false;
        }

        if (start.before(end) && (!(d.after(start) && d.before(end)))) {
            return false;
        }

        if (end.before(start) && (!(d.after(end) || d.before(start)))) {
            return false;
        }
        return true;
    }

    public static String toString(Object date) {
        if (date == null) {
            return null;
        }

        if (java.sql.Timestamp.class.isAssignableFrom(date.getClass())) {
            return OUT_TIMESTAMP_FORMAT.format(date);
        }
        if (java.sql.Time.class.isAssignableFrom(date.getClass())) {
            return OUT_TIME_FORMAT.format(date);
        }
        if (java.sql.Date.class.isAssignableFrom(date.getClass())) {
            return OUT_DATE_FORMAT.format(date);
        }
        if (java.util.Date.class.isAssignableFrom(date.getClass())) {
            return OUT_DATETIME_FORMAT.format(date);
        }

        throw new IllegalArgumentException(
                "Unsupported type " + date.getClass());
    }
    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
}
