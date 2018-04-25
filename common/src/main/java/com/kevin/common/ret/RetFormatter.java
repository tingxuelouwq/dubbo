package com.kevin.common.ret;

import com.kevin.common.constant.RetStatusCode;

import java.util.HashMap;
import java.util.Map;

/**
 * @类名: RetFormat
 * @包名：org.xinhua.common.ret
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2018/1/30 14:46
 * @版本：1.0
 * @描述：格式化响应为Json格式响应
 */
public class RetFormatter {

    private static final Map<Integer, String> messageMap = new HashMap<>();

    // 初始化状态码和响应信息
    static {
        // 响应异常码及对应的异常信息
        messageMap.put(RetStatusCode.BAD_REQUEST, "Bad Request");
        messageMap.put(RetStatusCode.NOT_AUTHORIZATION, "Not Authorization");
        messageMap.put(RetStatusCode.FORBIDDEN, "Forbidden");
        messageMap.put(RetStatusCode.METHOD_NOT_SUPPORTED, "Method Not Supported");
        messageMap.put(RetStatusCode.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE, "Http Media Type Not Acceptable");
        messageMap.put(RetStatusCode.INTERNAL_SERVER_ERROR, "Internal Server Error");
        messageMap.put(RetStatusCode.BAD_GATEWAY, "Bad Gateway");
        messageMap.put(RetStatusCode.SERVICE_UNAVAILABLE, "Service Unavailable");

        // 程序异常码及对应的异常信息
        messageMap.put(RetStatusCode.RUNTIME_EXCEPTION, "[integrate-service服务器]运行时异常");
        messageMap.put(RetStatusCode.NULL_POINT_EXCEPTION, "[integrate-service服务器]空指针异常");
        messageMap.put(RetStatusCode.CLASS_CAST_EXCEPTION, "[integrate-service服务器]类型转换异常");
        messageMap.put(RetStatusCode.IO_EXCEPTION, "[integrate-service服务器]IO异常");
        messageMap.put(RetStatusCode.NO_SUCH_METHOD_EXCEPTION, "[integrate-service服务器]未知方法异常");
        messageMap.put(RetStatusCode.ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION, "[integrate-service服务器]数据越界异常");
        messageMap.put(RetStatusCode.HTTP_URL_CONNECTION_EXCEPTION, "[integrate-service服务器]网络异常");

        // 数据库操作异常码及对应的异常信息
        messageMap.put(RetStatusCode.DB_INSERT_RESULT_0, "数据库操作异常, insert返回0");
        messageMap.put(RetStatusCode.DB_DELETE_RESULT_0, "数据库操作异常, delete返回0");
        messageMap.put(RetStatusCode.DB_UPDATE_RESULT_0, "数据库操作异常, update返回0");
        messageMap.put(RetStatusCode.DB_SELECT_RESULT_0, "数据库操作异常, select返回0");
    }

    public static RetJson format(int code) {
        RetJson retJson = new RetJson(code, messageMap.get(code), null);
        return retJson;
    }

    public static RetJson format(int code, String message) {
        RetJson retJson = new RetJson(code, message, null);
        return retJson;
    }

    public static RetJson format(int code, String message, Object data) {
        RetJson retJson = new RetJson(code, message, data);
        return retJson;
    }
}
