package com.kevin.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @类名：UserStatusEnum
 * @包名：com.kevin.constant
 * @作者：kevin
 * @时间：2017/8/10 22:44
 * @版本：1.0
 * @描述：用户状态
 */
public enum UserStatusEnum {

    ACTIVE("激活", 100),
    INACTIVE("冻结", 101);

    /** 描述 **/
    private String desc;

    /** 枚举值 **/
    private int value;

    UserStatusEnum(String desc, int value) {
        this.desc = desc;
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static UserStatusEnum getEnum(int value) {
        UserStatusEnum resultEnum = null;
        UserStatusEnum[] enumArr = UserStatusEnum.values();
        for (int i = 0; i < enumArr.length; i++) {
            if (enumArr[i].value == value) {
                resultEnum = enumArr[i];
                break;
            }
        }
        return resultEnum;
    }

    public static Map<String, Map<String, String>> toMap() {
        UserStatusEnum[] enumArr = UserStatusEnum.values();
        Map<String, Map<String, String>> enumMap = new HashMap<>();
        for (int i = 0; i < enumArr.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("value", String.valueOf(enumArr[i].getValue()));
            map.put("desc", enumArr[i].getDesc());
            String key = String.valueOf(getEnum(enumArr[i].getValue()));
            enumMap.put(key, map);
        }
        return enumMap;
    }

   public static List<Map<String, String>> toList() {
        UserStatusEnum[] enumArr = UserStatusEnum.values();
       List<Map<String, String>> list = new ArrayList();
       for (int i = 0; i < enumArr.length; i++) {
           Map<String, String> map = new HashMap<>();
           map.put("value", String.valueOf(enumArr[i].getValue()));
           map.put("desc", enumArr[i].getDesc());
           list.add(map);
       }
       return list;
   }
}
