package com.kevin.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * @类名: sonUtils
 * @包名：com.kevin.util
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/7/12 10:48
 * @版本：1.0
 * @描述：FastJson工具类
 * Fastjson的SerializerFeature序列化属性
 * QuoteFieldNames———-输出key时是否使用双引号,默认为true
 * WriteMapNullValue——–是否输出值为null的字段,默认为false
 * WriteNullNumberAsZero—-数值字段如果为null,输出为0,而非null
 * WriteNullListAsEmpty—–List字段如果为null,输出为[],而非null
 * WriteNullStringAsEmpty—字符类型字段如果为null,输出为”“,而非null
 * WriteNullBooleanAsFalse–Boolean字段如果为null,输出为false,而非null
 */
public class JsonUtils {

    private static final String DEFAULT_DATEFORMAT = "yyyy-MM-dd HH:mm:ss";

    private JsonUtils() {
        // no constructor function
    }

    /**
     * @param obj 将要序列化的对象
     * @return java.lang.String 序列化得到的json字符串
     * @方法名：bean2Json
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/7/12 10:54
     * @描述：将对象序列化为json字符串，时间格式默认为：yyyy-MM-dd HH:mm:ss
     */
    public static String bean2Json(Object obj) {
        return JSON.toJSONStringWithDateFormat(obj, DEFAULT_DATEFORMAT);
    }

    /**
     * @param obj        将要序列化的对象
     * @param dateFormat 序列化时采用的时间格式
     * @return java.lang.String
     * @方法名：bean2Json
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/7/12 16:53
     * @描述：将对象序列化为json字符串
     */
    public static String bean2Json(Object obj, String dateFormat) {
        return JSON.toJSONStringWithDateFormat(obj, dateFormat);
    }

    /**
     * @param jsonStr 将要反序列化的json字符串
     * @return com.alibaba.fastjson.JSONObject 反序列化得到的对象
     * @方法名：json2Bean
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/7/13 17:02
     * @描述：将json字符串反序列化为Json对象d
     */
    public static JSONObject json2Bean(String jsonStr) {
        return JSON.parseObject(jsonStr);
    }

    /**
     * @param jsonStr  将要反序列化的json字符串
     * @param objClass 反序列化的类
     * @return T 反序列化得到的对象
     * @方法名：json2Bean
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/7/12 10:56
     * @描述：将json字符串反序列化为对象
     */
    public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
        return JSON.parseObject(jsonStr, objClass);
    }

    /**
     * @param jsonStr 将要反序列化的json字符串
     * @param clazz   泛型对象类型
     * @return List<T> 反序列化得到的java.util.List<T>对象
     * @方法名：json2Bean
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/7/12 18:22
     * @描述：将json字符串反序列化为java.util.List<T>对象
     */
    public static <T> List<T> json2List(String jsonStr, Class<T> clazz) {
        return JSONArray.parseArray(jsonStr, clazz);
    }

    /**
     * @param jsonStr 将要反序列化的json字符串
     * @return Map<String,Object> 反序列化得到的java.util.Map<String, Object>对象
     * @方法名：json2Map
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/7/13 15:32
     * @描述：将json字符串反序列化为java.util.Map<String, Object>对象
     */
    public static Map<String, Object> json2Map(String jsonStr) {
        return JSON.parseObject(jsonStr, new TypeReference<Map<String, Object>>() {
        });
    }

    /**
     * @方法名：list2JsonArray
     * @作者：kevin
     * @时间：2017/9/27 22:46
     * @描述：将列表转换为JsonArray
     * @param list
     * @return com.alibaba.fastjson.JSONArray
     */
    public static <T> JSONArray list2JsonArray(List<T> list) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(list);
        return jsonArray;
    }
}
