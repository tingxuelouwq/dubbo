package com.kevin.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @类名: TimeUtil
 * @包名：com.kevin.util
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/8/6 10:45
 * @版本：1.0
 * @描述：基于JDK 8 time包的时间工具类
 */
public class TimeUtil {

    private static final Logger log = LoggerFactory.getLogger(TimeUtil.class);

    /**
     * 获取默认时间格式：yyyy/MM/dd
     */
    private static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER = TimeFormat.LONG_DATE_TIME_PATTERN_LINE.formatter;

    private TimeUtil() {
        // no constructor function
    }

    public static void main(String[] args) {
        System.out.println(parseEpochMilli(1483200000000L));
        System.out.println(toEpochMilli(parse("2018-03-05 00:00:00")));
    }

    /**
     * GMT时间转中国时间
     * @param gmtTimeStr
     * @return
     */
    public static LocalDateTime parseGMT2CN(String gmtTimeStr) {
        return LocalDateTime.parse(gmtTimeStr).plusHours(8);
    }

    /**
     * 字符串转时间，默认时间格式为：yyyy-MM-dd HH:mm:ss
     * @param timeStr
     * @return
     */
    public static LocalDateTime parse(String timeStr) {
        return LocalDateTime.parse(timeStr, DEFAULT_DATETIME_FORMATTER);
    }

    /**
     * 字符串转时间，使用指定的时间格式
     * @param timeStr
     * @param format
     * @return
     */
    public static LocalDateTime parse(String timeStr, TimeFormat format) {
        return LocalDateTime.parse(timeStr, format.formatter);
    }

    /**
     * 毫秒数转时间
     * @param epochMilli
     * @return
     */
    public static LocalDateTime parseEpochMilli(long epochMilli) {
        Instant instant = Instant.ofEpochMilli(epochMilli);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    /**
     * 时间转字符串，默认时间格式为：yyyy-MM-dd HH:mm:ss
     * @param time
     * @return
     */
    public static String parse(LocalDateTime time) {
        return DEFAULT_DATETIME_FORMATTER.format(time);
    }

    /**
     * 时间转字符串，使用指定的时间格式
     * @param time
     * @param format
     * @return java.lang.String
     */
    public static String parse(LocalDateTime time, TimeFormat format) {
        return format.formatter.format(time);
    }

    /**
     * 时间转毫秒
     * @param time
     * @return
     */
    public static long toEpochMilli(LocalDateTime time) {
        Instant instant = time.atZone(ZoneId.systemDefault()).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * 获取当前时间，默认时间格式为：yyyy-MM-dd HH:mm:ss
     * @param
     * @return java.lang.String
     */
    public static String getCurrentDateTime() {
        return DEFAULT_DATETIME_FORMATTER.format(LocalDateTime.now());
    }

    /**
     * 获取当前时间，使用指定的时间格式
     * @param format
     * @return java.lang.String
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