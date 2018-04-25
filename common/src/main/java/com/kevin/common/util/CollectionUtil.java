package com.kevin.common.util;

import java.util.Collection;

/**
 * @类名: CollectionUtil
 * @包名：com.kevin.common.util
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/8/24 14:08
 * @版本：1.0
 * @描述：集合工具类
 */
public class CollectionUtil {

    private CollectionUtil() {
        // no constructor function
    }

    /**
     * 判断集合是否不为空
     * @param t
     * @param <T>
     * @return
     */
    public static <T> boolean isNotEmpty(Collection<T> t) {
        return (t != null && !t.isEmpty());
    }

    /**
     * 判断集合是否为空
     * @param t
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(Collection<T> t) {
        return (t == null || t.isEmpty());
    }
}
