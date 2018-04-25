package com.kevin.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @类名: JacksonUtil
 * @包名：org.xinhua.common.util
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2018/3/4 19:06
 * @版本：1.0
 * @描述：
 */
public class JacksonUtil {

    private static final Logger log = LoggerFactory.getLogger(JacksonUtil.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String DEFAULT_DATEFORMAT = "yyyy-MM-dd HH:mm:ss";

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setDateFormat(new SimpleDateFormat(DEFAULT_DATEFORMAT));
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private JacksonUtil() {
        // no constructor function
    }

    /**
     * 序列化
     * @param obj
     * @return
     */
    public static String bean2Json(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("jackson序列化异常", e);
            return null;
        }
    }

    /**
     * 反序列化
     * @param jsonStr
     * @param objClass
     * @param <T>
     * @return
     */
    public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
        try {
            return objectMapper.readValue(jsonStr, objClass);
        } catch (IOException e) {
            log.error("jackson反序列化异常", e);
            return null;
        }
    }

    /**
     * 序列化map为json
     * @param map
     * @return
     */
    public static String map2Json(Map map) {
        return bean2Json(map);
    }

    /**
     * 反序列化json为map
     * @param jsonStr
     * @return
     */
    public static Map json2Map(String jsonStr) {
        return json2Bean(jsonStr, Map.class);
    }

    /**
     * 序列化list为json
     * @param list
     * @param <T>
     * @return
     */
    public static <T> String list2Json(List<T> list) {
        return bean2Json(list);
    }

    /**
     * 反序列化json为list
     * @param jsonArr
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> json2List(String jsonArr, Class<T> clazz) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
            return objectMapper.readValue(jsonArr, javaType);
        } catch (IOException e) {
            log.error("jackson反序列化异常", e);
            return null;
        }
    }
}
