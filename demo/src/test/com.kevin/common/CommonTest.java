package com.kevin.common;

import org.junit.Test;

/**
 * @类名：CommonTest
 * @包名：com.kevin.common
 * @作者：kevin
 * @时间：2017/8/8 23:02
 * @版本：1.0
 * @描述：
 */
public class CommonTest {

    @Test
    public void test() {
        System.out.println(hello());
    }

    private int hello() {
        try {
            int i = 1 / 0;
        } catch (ArithmeticException e) {
            System.out.println("捕获到异常");
            return 2;
        }
        return 3;
    }
}
