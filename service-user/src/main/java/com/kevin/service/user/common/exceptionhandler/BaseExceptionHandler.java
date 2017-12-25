package com.kevin.service.user.common.exceptionhandler;

import com.kevin.service.user.common.ret.RetFormat;
import com.kevin.service.user.common.ret.RetJson;
import com.kevin.service.user.common.ret.RetStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * @类名：BaseExceptionHandler
 * @包名：com.kevin.exceptionhandler
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/7/7 15:29
 * @版本：1.0
 * @描述：异常处理基类
 */
@ControllerAdvice
public class BaseExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(BaseExceptionHandler.class);

    /**
     * 处理400错误
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public RetJson request400(MissingServletRequestParameterException ex) {
        log.error("400错误: 缺少参数", ex);
        return RetFormat.format(RetStatusCode.BAD_REQUEST, null);
    }

    /**
     * 处理400错误
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(TypeMismatchException.class)
    @ResponseBody
    public RetJson request400(TypeMismatchException ex) {
        log.error("400错误: 参数类型不匹配", ex);
        return RetFormat.format(RetStatusCode.BAD_REQUEST, null);
    }

    /**
     * 处理405错误
     *
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public RetJson request405(HttpRequestMethodNotSupportedException ex) {
        log.error("405错误: 请求方式错误", ex);
        return RetFormat.format(RetStatusCode.METHOD_NOT_SUPPORTED, null);
    }

    /**
     * 处理406错误
     *
     * @return
     */
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseBody
    public RetJson request406(HttpMediaTypeNotAcceptableException ex) {
        log.error("406错误: 找不到可访问的多媒体类型", ex);
        return RetFormat.format(RetStatusCode.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE, null);
    }

    /**
     * 处理500错误
     *
     * @return
     */
    @ExceptionHandler({ConversionNotSupportedException.class,
            HttpMessageNotWritableException.class})
    @ResponseBody
    public RetJson request500(RuntimeException ex) {
        log.error("500错误: 服务器内部错误", ex);
        return RetFormat.format(RetStatusCode.INTERNAL_SERVER_ERROR, null);
    }

    /**
     * 处理运行时异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public RetJson runtimeExceptionHandler(RuntimeException ex) {
        log.error("运行时异常", ex);
        return RetFormat.format(RetStatusCode.RUNTIME_EXCEPTION, null);
    }

    /**
     * 处理空指针异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public RetJson nullPointExceptionHandler(NullPointerException ex) {
        log.error("空指针异常", ex);
        return RetFormat.format(RetStatusCode.NULL_POINT_EXCEPTION, null);
    }

    /**
     * 处理类型转换异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(ClassCastException.class)
    @ResponseBody
    public RetJson classCastExceptionHandler(ClassCastException ex) {
        log.error("类型转换异常", ex);
        return RetFormat.format(RetStatusCode.CLASS_CAST_EXCEPTION, null);
    }

    /**
     * 处理IO异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(IOException.class)
    @ResponseBody
    public RetJson ioExceptionHandler(IOException ex) {
        log.error("IO异常", ex);
        return RetFormat.format(RetStatusCode.IO_EXCEPTION, null);
    }

    /**
     * 处理未知方法异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(NoSuchMethodException.class)
    @ResponseBody
    public RetJson noSuchMethodExceptionHandler(NoSuchMethodException ex) {
        log.error("未知方法异常", ex);
        return RetFormat.format(RetStatusCode.NO_SUCH_METHOD_EXCEPTION, null);
    }

    /**
     * 处理数组越界异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    @ResponseBody
    public RetJson arrayIndexOutOfBoundsExceptionHandler(ArrayIndexOutOfBoundsException ex) {
        log.error("数组越界异常", ex);
        return RetFormat.format(RetStatusCode.ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION, null);
    }
}
