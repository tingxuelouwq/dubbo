package com.kevin.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @类名: DateUtils
 * @包名：com.kevin.util
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/8/6 10:45
 * @版本：1.0
 * @描述：基于JDK 8 time包的时间工具类
 */
public class TimeUtils {

    private static final Logger log = LoggerFactory.getLogger(TimeUtils.class);

    /**
     * 获取默认时间格式：yyyy/MM/dd
     */
    private static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER = TimeFormat.SHORT_DATE_TIME_PATTERN_SLASH.formatter;

    private TimeUtils() {
        // no constructor function
    }

    /**
     * @param timeStr
     * @return java.time.LocalDateTime
     * @throws
     * @方法名：parseTime
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/8/6 10:48
     * @描述：String转时间
     */
    public static LocalDateTime parseTime(String timeStr) {
        return LocalDateTime.parse(timeStr, DEFAULT_DATETIME_FORMATTER);
    }

    /**
     * @param timeStr
     * @param format
     * @return java.time.LocalDateTime
     * @throws
     * @方法名：parseTime
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/8/6 10:53
     * @描述：String转时间
     */
    public static LocalDateTime parseTime(String timeStr, TimeFormat format) {
        return LocalDateTime.parse(timeStr, format.formatter);
    }

    /**
     * @param time
     * @return java.lang.String
     * @throws
     * @方法名：parseTime
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/8/6 10:55
     * @描述：时间转String
     */
    public static String parseTime(LocalDateTime time) {
        return DEFAULT_DATETIME_FORMATTER.format(time);
    }

    /**
     * @param time
     * @param format
     * @return java.lang.String
     * @throws
     * @方法名：parseTime
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/8/6 10:58
     * @描述：时间转String
     */
    public static String parseTime(LocalDateTime time, TimeFormat format) {
        return format.formatter.format(time);
    }

    /**
     * @param
     * @return java.lang.String
     * @throws
     * @方法名：getCurrentDateTime
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/8/6 10:56
     * @描述：获取当前时间
     */
    public static String getCurrentDateTime() {
        return DEFAULT_DATETIME_FORMATTER.format(LocalDateTime.now());
    }

    public static void main(String[] args) {
        System.out.println(TimeUtils.getCurrentDateTime(TimeFormat.LONG_DATE_TIME_PATTERN_WITH_MILSEC_NONE_SEPARATOR));
    }

    /**
     * @param format
     * @return java.lang.String
     * @throws
     * @方法名：getCurrentDateTime
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/8/6 10:57
     * @描述：获取当前时间
     */
    public static String getCurrentDateTime(TimeFormat format) {
        return format.formatter.format(LocalDateTime.now());
    }

    /**
     * 时间格式
     */
    public enum TimeFormat {
        /**
         * 短时间格式
         */
        SHORT_DATE_TIME_PATTERN_LINE("yyyy-MM-dd"),
        SHORT_DATE_TIME_PATTERN_SLASH("yyyy/MM/dd"),
        SHORT_DATE_TIME_PATTERN_DOUBLE_SLASH("YYYY\\MM\\dd"),
        SHORT_DATE_TIME_PATTERN_NONE("yyyyMMdd"),

        /**
         * 长时间格式
         */
        LONG_DATE_TIME_PATTERN_LINE("yyyy-MM-dd HH:mm:ss"),
        LONG_DATE_TIME_PATTERN_SLASH("yyyy/MM/dd HH:mm:ss"),
        LONG_DATE_TIME_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss"),
        LONG_DATE_TIME_PATTERN_NONE("yyyyMMdd HH:mm:ss"),
        LONG_DATE_TIME_PATTERN_NONE_SEPARATOR("yyyyMMddHHmmss"),

        /**
         * 长时间格式，带毫秒
         */
        LONG_DATE_TIME_PATTERN_WITH_MILSEC_LINE("yyyy-MM-dd HH:mm:ss.SSS"),
        LONG_DATE_TIME_PATTERN_WITH_MILSEC_SLASH("yyyy/MM/dd HH:mm:ss.SSS"),
        LONG_DATE_TIME_PATTERN_WITH_MILSEC_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss.SSS"),
        LONG_DATE_TIME_PATTERN_WITH_MILSEC_NONE("yyyyMMdd HH:mm:ss.SSS"),
        LONG_DATE_TIME_PATTERN_WITH_MILSEC_NONE_SEPARATOR("yyyyMMddHHmmssSSS");

        private transient DateTimeFormatter formatter;

        TimeFormat(String pattern) {
            formatter = DateTimeFormatter.ofPattern(pattern);
        }
    }
}