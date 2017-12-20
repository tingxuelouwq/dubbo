package com.kevin.constant;

/**
 * @类名：SessionConstant
 * @包名：com.kevin.constant
 * @作者：kevin
 * @时间：2017/8/10 23:46
 * @版本：1.0
 * @描述：会话键常量类
 */
public interface SessionConstant {

    /** 登录用户的session键名 **/
    String USER_SESSION_KEY = "pmsUser";

    /** 商户主账号ID的session键名 **/
    String MAIN_USER_ID_SESSION_KEY = "pmsMainUserId";

    /** 登录用户拥有的权限集合的session键名 **/
    String ACTIONS_SESSION_KEY = "actions";

    /** 用户密码连续输错次数限制（默认5） **/
    int WEB_PWD_INPUT_ERROR_LIMIT = 5;
}
