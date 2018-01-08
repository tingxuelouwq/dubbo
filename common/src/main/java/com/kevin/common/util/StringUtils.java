package com.kevin.common.util;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @类名：StringUtils
 * @包名：com.kevin.util
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/7/7 10:33
 * @版本：1.0
 * @描述：字符串操工具类
 */
public class StringUtils {

    private StringUtils() {
        // no constructor function
    }

    /**
     * 判断字符串是否为空，null或者空字符串时返回true，其他情况返回false
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * @方法名：isNotEmpty
     * @作者：kevin
     * @时间：2017/8/29 13:00
     * @描述：判断字符串是否为空，非null并且非空字符串时返回true，其他情况返回false
     * @param str
     * @return boolean
     */
    public static boolean isNotEmpty(String str) {
       return str != null && str.length() != 0;
    }

    /**
     * @方法名：parseToString
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/8/22 12:41
     * @描述：将数组转换成字符串，以逗号分隔，跳过空字符串
     * @param strArr
     * @return java.lang.String
     * @exception
     */
    public static String parseToString(String[] strArr) {
        return parseToString(strArr, ",");
    }

    /**
     * @方法名：parseToString
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/8/22 12:33
     * @描述：将数组转换成字符串，以指定分隔符分隔，跳过空字符串
     * @param strArr
     * @param separator
     * @return String
     * @exception
     */
    public static String parseToString(String[] strArr, String separator) {
        if (strArr.length == 0) {
            return "";
        }

        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < strArr.length; i++) {
            if (isEmpty(strArr[i])) {
                continue;
            }

            if (i > 0) {
                strBuilder.append(separator).append(strArr[i]);
            } else {
                strBuilder.append(strArr[i]);
            }
        }

        return strBuilder.toString();
    }

    /**
     * @方法名：parseJsonAndGet
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/8/22 15:07
     * @描述：解析json字符串，获取指定key对应的value
     * @param key
     * @param jsonStr
     * @return java.lang.String
     * @exception
     */
    public static String parseJsonAndGet(String jsonStr, String key) {
        if (isEmpty(jsonStr)) {
            return null;
        }

        JSONObject content = JsonUtils.json2Bean(jsonStr);
        return content.get(key) != null ? content.get(key).toString() : "";
    }

    /**
     * @方法名：split
     * @作者：kevin
     * @时间：2017/11/3 23:58
     * @描述：根据指定分隔符拆分字符串
     * @param str 要拆分的字符串，可以为null
     * @param separatorChar 分隔符
     * @return java.lang.String[] 拆分得到的字符串数组，如果输入为null的话，则返回null
     */
    public static String[] split(final String str, final char separatorChar) {
        return split(str, separatorChar, false);
    }

    /**
     * @方法名：split
     * @作者：kevin
     * @时间：2017/11/3 23:52
     * @描述：根据指定分隔符拆分字符串
     * <pre>
     * StringUtils.split(null) = null
     * StringUtils.split(",", ',', false) = []
     * StringUtils.split(",", ',', true) = [""]
     * </pre>
     * @param str 要拆分的字符串，可以为null
     * @param separatorChar 分隔符
     * @param preserveAllTokens 如果设置为false，则相邻分隔符会被视为一个分隔符
     * @return java.lang.String[] 拆分得到的字符串数组，如果输入为null的话，则返回null
     */
    public static String[] split(final String str, final char separatorChar, final boolean preserveAllTokens) {
        if (str == null) {
            return null;
        }
        final int len = str.length();
        if (len == 0) {
            return new String[0];
        }
        final List<String> list = new ArrayList<>();
        int i = 0, start = 0;
        boolean match = false;
        while (i < len) {
            if (str.charAt(i) == separatorChar) {
                if (match || preserveAllTokens) {
                    list.add(str.substring(start, i));
                    match = false;
                }
                start = ++i;
                continue;
            }
            match = true;
            i++;
        }
        if (match || preserveAllTokens) {
            list.add(str.substring(start, i));
        }
        return list.toArray(new String[list.size()]);
    }
}
