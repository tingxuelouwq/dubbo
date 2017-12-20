package com.kevin.exception;

/**
 * @描述：业务异常基类，所有业务异常都必须继承于此异常
 * @作者：kevin
 * @时间：2017/7/7 9:11
 * @版本：1.0
 */
public class BizException extends RuntimeException {
    private static final long serialVersionUID = -1346663682354396101L;

    /**
     * 数据库操作,insert返回0
     */
    public static final int DB_INSERT_RESULT_0 = 90040001;

    /**
     * 数据库操作,update返回0
     */
    public static final int DB_UPDATE_RESULT_0 = 90040002;

    /**
     * 数据库操作,selectOne返回null
     */
    public static final int DB_SELECTONE_IS_NULL = 90040003;

    /**
     * 数据库操作,list返回null
     */
    public static final int DB_LIST_IS_NULL = 90040004;

    /**
     * 会话超时　获取session时，如果是空，throws 下面这个异常 拦截器会拦截爆会话超时页面
     */
    public static final int SESSION_IS_OUT_TIME = 90040006;

    /**
     * 异常信息
     */
    protected String msg;

    /**
     * 具体异常码
     */
    protected int code;

    public BizException() {
    }

    public BizException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BizException(int code, String msgFormat, Object... args) {
        this.code = code;
        this.msg = String.format(msgFormat, args);
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }
}
