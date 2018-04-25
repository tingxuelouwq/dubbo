package com.kevin.common.exception;

import com.kevin.common.ret.RetJson;

/**
 * @类名：BizException
 * @包名：com.kevin.article.exception
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/9/23 11:07
 * @版本：1.0
 * @描述：业务异常
 */
public class BaseException extends Exception {

    private static final long serialVersionUID = 7050290885804188730L;

    private RetJson retJson;

    public BaseException() {
    }

    public BaseException(RetJson retJson) {
        this.retJson = retJson;
    }

    public BaseException(Throwable cause, RetJson retJson) {
        super(cause);
        this.retJson = retJson;
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public RetJson getRetJson() {
        return retJson;
    }
}
