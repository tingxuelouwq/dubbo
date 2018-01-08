package com.kevin.common.ret;

/**
 * @类名: RetStatusCode
 * @包名：com.kevin.article.constant
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/7/17 16:00
 * @版本：1.0
 * @描述：状态码
 */
public interface RetStatusCode {

    // 响应异常码
    Integer BAD_REQUEST = 400;
    Integer NOT_AUTHORIZATION = 401;
    Integer FORBIDDEN = 403;
    Integer METHOD_NOT_SUPPORTED = 405;
    Integer HTTP_MEDIA_TYPE_NOT_ACCEPTABLE = 406;
    Integer INTERNAL_SERVER_ERROR = 500;
    Integer BAD_GATEWAY = 502;
    Integer SERVICE_UNAVAILABLE = 503;

    // 程序异常码
    Integer RUNTIME_EXCEPTION = 100100;
    Integer NULL_POINT_EXCEPTION = 100101;
    Integer CLASS_CAST_EXCEPTION = 100102;
    Integer IO_EXCEPTION = 100103;
    Integer NO_SUCH_METHOD_EXCEPTION = 100104;
    Integer ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION = 100105;
    Integer HTTP_URL_CONNECTION_EXCEPTION = 100106;

    // 数据库操作异常码
    Integer DB_INSERT_RESULT_0 = 100200;
    Integer DB_DELETE_RESULT_0 = 100201;
    Integer DB_UPDATE_RESULT_0 = 100202;
    Integer DB_SELECT_RESULT_0 = 100203;
}
