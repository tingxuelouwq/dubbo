package com.kevin.facade.user.common.ret;

import com.kevin.facade.user.common.util.JsonUtils;

import java.io.Serializable;

/**
 * @类名：RetJson
 * @包名：com.kevin.util
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/7/7 15:02
 * @版本：1.0
 * @描述：格式化响应数据，包括响应状态码，提示信息，业务数据
 */
public class RetJson implements Serializable {
    private static final long serialVersionUID = 1155124090420409354L;

    /**
     * 状态码
     **/
    private int code;

    /**
     * 提示信息
     **/
    private String message;

    /**
     * 业务数据
     **/
    private Object data;

    public RetJson(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JsonUtils.bean2Json(this);
    }
}
