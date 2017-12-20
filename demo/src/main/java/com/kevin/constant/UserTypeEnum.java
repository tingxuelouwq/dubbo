package com.kevin.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @类名：UserTypeEnum
 * @包名：com.kevin.constant
 * @作者：kevin
 * @时间：2017/8/10 22:54
 * @版本：1.0
 * @描述：用户类型
 */
public enum  UserTypeEnum {

    /** 超级管理员 **/
    SUPER_ADMIN("超级管理员", "1"),

    /** 普通管理员 **/
    ADMIN("普通管理员", "2"),

    /** 用户主账号（商家主账号） **/
    MAIN_USER("用户主账号", "3"),

    /** 用户子账号（商家主账号分配的子账号） **/
    SUB_USER("用户子账号", "4");

    /** 描述 **/
    private String desc;

    /** 枚举值 **/
    private String value;

    UserTypeEnum(String desc, String value) {
        this.desc = desc;
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static UserTypeEnum getEnum(String value) {
        UserTypeEnum resultEnum = null;
        UserTypeEnum[] enumArr = UserTypeEnum.values();
        for (int i = 0; i < enumArr.length; i++) {
            if (enumArr[i].value.equals(value)) {
                resultEnum = enumArr[i];
                break;
            }
        }
        return resultEnum;
    }

    public static Map<String, Map<String, String>> toMap() {
        UserTypeEnum[] enumArr = UserTypeEnum.values();
        Map<String, Map<String, String>> enumMap = new HashMap<>();
        for (int i = 0; i < enumArr.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("value", enumArr[i].getValue());
            map.put("desc", enumArr[i].getDesc());
            String key = String.valueOf(getEnum(enumArr[i].getValue()));
            enumMap.put(key, map);
        }
        return enumMap;
    }

    public static List<Map<String, String>> toList() {
        UserTypeEnum[] enumArr = UserTypeEnum.values();
        List<Map<String, String>> list = new ArrayList();
        for (int i = 0; i < enumArr.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("value", enumArr[i].getValue());
            map.put("desc", enumArr[i].getDesc());
            list.add(map);
        }
        return list;
    }
}
