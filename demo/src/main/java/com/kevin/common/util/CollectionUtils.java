package com.kevin.common.util;

import java.util.Collection;

/**
 * @类名: CollectionUtils
 * @包名：com.kevin.common.util
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/8/24 14:08
 * @版本：1.0
 * @描述：集合工具类
 */
public class CollectionUtils {

    /**
     * @方法名：isNotEmpty
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/8/24 14:12
     * @描述：判断集合是否不为空
     * @param t
     * @return boolean
     * @exception
     */
    public static <T> boolean isNotEmpty(Collection<T> t) {
        return (t != null && !t.isEmpty());
    }

    /**
     * @方法名：isEmpty
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/8/24 14:12
     * @描述：判断集合是否为空
     * @param t
     * @return boolean
     * @exception
     */
    public static <T> boolean isEmpty(Collection<T> t) {
        return (t == null || t.isEmpty());
    }
}
