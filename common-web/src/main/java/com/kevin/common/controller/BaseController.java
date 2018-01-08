package com.kevin.common.controller;

import com.kevin.common.ret.RetFormat;
import com.kevin.common.ret.RetJson;

/**
 * @类名：BaseController
 * @包名：com.kevin.controller
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/7/7 14:32
 * @版本：1.0
 * @描述：控制层基类，所有控制层类均继承于此类
 */
public abstract class BaseController {

    /**
     * @param code 状态码
     * @param data 业务数据
     * @return java.lang.String 响应
     * @方法名：retResponse
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/7/7 14:32
     * @描述：返回统一格式的响应
     */
    protected String retResponse(int code, Object data) {
        return RetFormat.format(code, data).toString();
    }

    /**
     * @方法名：retResponse
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/10/16 14:27
     * @描述：返回统一格式的响应
     * @param code
     * @param message
     * @param data
     * @return java.lang.String
     * @exception
     */
    protected String retResponse(int code, String message, Object data) {
        return RetFormat.format(code, message, data).toString();
    }

    /**
     * @方法名：retResponse
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/10/18 19:02
     * @描述：返回统一格式的响应
     * @param retJson
     * @return java.lang.String
     * @exception
     */
    protected String retResponse(RetJson retJson) {
        return retJson.toString();
    }
}
