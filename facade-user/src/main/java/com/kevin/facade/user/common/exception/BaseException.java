package com.kevin.facade.user.common.exception;

import com.kevin.facade.user.common.ret.RetJson;

/**
 * @类名: DMLException
 * @包名：com.kevin.exception
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/7/18 14:43
 * @版本：1.0
 * @描述：异常基类
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 3492432326278365303L;

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
