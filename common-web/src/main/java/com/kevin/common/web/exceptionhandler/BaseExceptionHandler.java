package com.kevin.common.web.exceptionhandler;

import com.kevin.common.constant.RetStatusCode;
import com.kevin.common.ret.RetFormatter;
import com.kevin.common.ret.RetJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * @类名：BaseExceptionHandler
 * @包名：com.kevin.exceptionhandler
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/7/7 15:29
 * @版本：1.0
 * @描述：异常处理器基础支撑类
 */
@RestControllerAdvice
public class BaseExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(BaseExceptionHandler.class);

    /**
     * 处理400错误
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public RetJson request400(MissingServletRequestParameterException ex) {
        log.error("400错误: 缺少参数", ex);
        return RetFormatter.format(RetStatusCode.BAD_REQUEST, "缺少请求参数");
    }

    /**
     * 处理400错误
     */
    @ExceptionHandler(TypeMismatchException.class)
    public RetJson request400(TypeMismatchException ex) {
        log.error("400错误: 参数类型不匹配", ex);
        return RetFormatter.format(RetStatusCode.BAD_REQUEST, "参数类型不匹配");
    }

    /**
     * 处理405错误
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RetJson request405(HttpRequestMethodNotSupportedException ex) {
        log.error("405错误: 请求方式错误", ex);
        return RetFormatter.format(RetStatusCode.METHOD_NOT_SUPPORTED, "请求方式错误");
    }

    /**
     * 处理406错误
     */
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public RetJson request406(HttpMediaTypeNotAcceptableException ex) {
        log.error("406错误: 找不到可访问的多媒体类型", ex);
        return RetFormatter.format(RetStatusCode.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE, "找不到可访问的多媒体类型");
    }

    /**
     * 处理500错误
     */
    @ExceptionHandler({ConversionNotSupportedException.class,
            HttpMessageNotWritableException.class})
    public RetJson request500(RuntimeException ex) {
        log.error("500错误: 服务器内部错误", ex);
        return RetFormatter.format(RetStatusCode.INTERNAL_SERVER_ERROR, "服务器内部错误");
    }

    /**
     * 处理运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public RetJson runtimeExceptionHandler(RuntimeException ex) {
        log.error("运行时异常", ex);
        return RetFormatter.format(RetStatusCode.RUNTIME_EXCEPTION, "服务器运行时异常");
    }

    /**
     * 处理空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public RetJson nullPointExceptionHandler(NullPointerException ex) {
        log.error("空指针异常", ex);
        return RetFormatter.format(RetStatusCode.NULL_POINT_EXCEPTION, "空指针异常");
    }

    /**
     * 处理类型转换异常
     */
    @ExceptionHandler(ClassCastException.class)
    public RetJson classCastExceptionHandler(ClassCastException ex) {
        log.error("类型转换异常", ex);
        return RetFormatter.format(RetStatusCode.CLASS_CAST_EXCEPTION, "类型转换异常");
    }

    /**
     * 处理IO异常
     */
    @ExceptionHandler(IOException.class)
    public RetJson ioExceptionHandler(IOException ex) {
        log.error("IO异常", ex);
        return RetFormatter.format(RetStatusCode.IO_EXCEPTION, "IO异常");
    }

    /**
     * 处理未知方法异常
     */
    @ExceptionHandler(NoSuchMethodException.class)
    public RetJson noSuchMethodExceptionHandler(NoSuchMethodException ex) {
        log.error("未知方法异常", ex);
        return RetFormatter.format(RetStatusCode.NO_SUCH_METHOD_EXCEPTION, "未知方法异常");
    }

    /**
     * 处理数组越界异常
     */
    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public RetJson arrayIndexOutOfBoundsExceptionHandler(ArrayIndexOutOfBoundsException ex) {
        log.error("数组越界异常", ex);
        return RetFormatter.format(RetStatusCode.ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION, "数组越界异常");
    }
}
